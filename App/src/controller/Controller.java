package controller;

import model.ProgramState;
import model.heap.IHeap;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;
import model.exceptions.ToyLangException;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    private IRepository repo;
    private IHeap sharedHeap;
    private ExecutorService executor;

    public Controller(IRepository r, IHeap heap) {

        repo = r;
        sharedHeap = heap;
    }

    private List<ProgramState> removeCompletedProgram(List<ProgramState> programsList) throws ToyLangException {

        return programsList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    /*
    public void allStep() throws ToyLangException {

        ProgramState program = repo.getCurrentProgram(); // repo is the controller field of type IRepository

        repo.logProgramStateExecution();
        System.out.println("\n" + program);

        while (!program.getExeStack().isEmpty()) {

            oneStep(program);
            repo.logProgramStateExecution();

            program.getHeap().setContent(program.safeGarbageCollector(
                    program.getAddrFromSymTable(program.getSymTable().getContent().values()),
                    program.getAddrFromHeap(program.getHeap().getContent().values()),
                    program.getHeap().getContent()));

            System.out.println("\n" + program);
        }
    }

    */

    private List<Integer> getAddrFromAllSymTables(Collection<Collection<IValue>> symTables) {

        ArrayList<Integer> masterList = new ArrayList<>();

        symTables.forEach(table -> masterList.addAll(table.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddr();
                })
                .collect(Collectors.toList())));


        return masterList.stream().toList();

        /*
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddr();})
                .collect(Collectors.toList());

         */
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


    public void oneStepForAllPrograms(List<ProgramState> programsList) throws ToyLangException {

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

            newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();

                        } catch (InterruptedException | ExecutionException e) {

                            System.out.println(e.getCause().getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        }
        catch (InterruptedException e) {

            throw new ToyLangException(e.getMessage());
        }

        programsList.clear();
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

        repo.setProgramList(programsList);
    }

    public void allStep() throws ToyLangException {

        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programsList = removeCompletedProgram(repo.getProgramList().getContent());

        while (programsList.size() > 0) {

            sharedHeap.setContent(conservativeGarbageCollector(

                    getAddrFromAllSymTables(
                            programsList.stream()
                                    .map(prg -> prg.getSymTable().getContent().values())
                                    .collect(Collectors.toList())),

                    getAddrFromHeap(sharedHeap.getContent().values()),
                    sharedHeap.getContent()));

            oneStepForAllPrograms(programsList);
            programsList = removeCompletedProgram(repo.getProgramList().getContent());
        }

        executor.shutdownNow();
        repo.setProgramList(programsList);
    }

    public IRepository getRepo() {
        return repo;
    }


}
