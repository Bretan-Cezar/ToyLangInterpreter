package com.toylanggui.interpreter.controller;



import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.heap.IHeap;
import com.toylanggui.interpreter.model.list.IList;
import com.toylanggui.interpreter.model.value.IValue;
import com.toylanggui.interpreter.model.value.RefValue;
import com.toylanggui.interpreter.model.value.StringValue;
import com.toylanggui.interpreter.repository.IRepository;

import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Controller {

    private IRepository repo;
    private IList<IValue> sharedOut;
    private IDictionary<StringValue, BufferedReader> sharedFileTable;
    private IHeap sharedHeap;
    private ExecutorService executor;

    public Controller(IRepository r, IList<IValue> o, IDictionary<StringValue, BufferedReader> ft, IHeap heap) {

        repo = r;
        sharedOut = o;
        sharedFileTable = ft;
        sharedHeap = heap;
    }

    public IList<IValue> getSharedOut() {
        return sharedOut;
    }

    public IDictionary<StringValue, BufferedReader> getSharedFileTable() {
        return sharedFileTable;
    }

    public IHeap getSharedHeap() {
        return sharedHeap;
    }

    private List<ProgramState> removeCompletedProgram(List<ProgramState> programsList) {

        return programsList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromAllSymTables(Collection<Collection<IValue>> symTables) {

        ArrayList<Integer> masterList = new ArrayList<>();

        symTables.forEach(table -> masterList.addAll(table.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddr();
                }).toList()));


        return masterList.stream().toList();
    }

    private List<Integer> getAddrFromHeap(Collection<IValue> heapValues) {

        return heapValues.stream()
                .filter(val -> val instanceof RefValue)
                .map(val -> {RefValue val1 = (RefValue) val; return val1.getAddr();})
                .collect(Collectors.toList());
    }

    private Map<Integer, IValue> conservativeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, HashMap<Integer, IValue> hp) {

        return hp.entrySet().stream()
                .filter(e -> (symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public void oneStepForAllPrograms() throws ToyLangException {

        List<ProgramState> programsList = removeCompletedProgram(repo.getProgramList().getContent());

        sharedHeap.setContent(conservativeGarbageCollector(

                getAddrFromAllSymTables(
                        programsList.stream()
                                .map(prg -> prg.getSymTable().getContent().values())
                                .collect(Collectors.toList())),

                getAddrFromHeap(sharedHeap.getContent().values()),
                sharedHeap.getContent()));

        executor = Executors.newFixedThreadPool(8);

        programsList.forEach(prg -> {

            try {

                System.out.println(prg.toString());
                repo.logProgramStateExecution(prg);
            }
            catch (ToyLangException e) {

                System.out.println(e.getMessage());
            }
        });

        List<Callable<ProgramState>> callList = programsList.stream()
                .map((ProgramState prg) -> (Callable<ProgramState>)(prg::oneStep))
                .collect(Collectors.toList());

        List<ProgramState> newProgramList;

        try {

            // AtomicReference<Throwable> exception = new AtomicReference<>();

            newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();

                        } catch (InterruptedException | ExecutionException e) {

                            // exception.set(e.getCause());
                            System.out.println(e.getCause().getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // if (exception.get() instanceof ToyLangException tle)
            //     throw tle;

        }
        catch (InterruptedException e) {

            throw new ToyLangException(e.getMessage());
        }
        
        programsList.addAll(newProgramList);

        programsList.forEach(prg -> {
            try {

                System.out.println(prg.toString() + "\n");
                repo.logProgramStateExecution(prg);
            }
            catch (ToyLangException e) {
                System.out.println(e.getMessage());
            }
        });

        repo.setProgramList(removeCompletedProgram(programsList));
    }

    public void allStep() throws ToyLangException {

        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programsList = removeCompletedProgram(repo.getProgramList().getContent());

        while (programsList.size() > 0) {

            /*
            sharedHeap.setContent(conservativeGarbageCollector(

                    getAddrFromAllSymTables(
                            programsList.stream()
                                    .map(prg -> prg.getSymTable().getContent().values())
                                    .collect(Collectors.toList())),

                    getAddrFromHeap(sharedHeap.getContent().values()),
                    sharedHeap.getContent()));
            */

            oneStepForAllPrograms();
            programsList = removeCompletedProgram(repo.getProgramList().getContent());
        }

        executor.shutdownNow();
        // repo.setProgramList(programsList);
    }

    public IRepository getRepo() {
        return repo;
    }


}
