package com.toylanggui.interpreter.model;

import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.heap.IHeap;
import com.toylanggui.interpreter.model.list.IList;
import com.toylanggui.interpreter.model.proc_table.IProcTable;
import com.toylanggui.interpreter.model.stack.IStack;
import com.toylanggui.interpreter.model.statement.IStatement;
import com.toylanggui.interpreter.model.value.IValue;
import com.toylanggui.interpreter.model.value.StringValue;

import java.io.BufferedReader;


public class ProgramState {


    private IStack<IStatement> exeStack;
    private IStack<IDictionary<String, IValue>> symTableStack;
    private IList<IValue> out;
    private IStatement originalProgram;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IHeap heap;
    private IProcTable procTable;

    private int programID;
    private static int global_max_id = 0;

    public IStack<IStatement> getExeStack() {
        return exeStack;
    }

    public IStack<IDictionary<String, IValue>> getSymTableStack() {
        return symTableStack;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IHeap getHeap() {
        return heap;
    }

    public IProcTable getProcTable() {
        return procTable;
    }

    public int getProgramID() {
        return programID;
    }

    public static synchronized int getNewID() {

        global_max_id += 1;
        return global_max_id;
    }

    public ProgramState(IStack<IStatement> stack, IStack<IDictionary<String, IValue>> s_table_stack, IList<IValue> list, IDictionary<StringValue, BufferedReader> f_table, IHeap hp, IProcTable pt, IStatement program) {

        programID = getNewID();
        exeStack = stack;
        symTableStack = s_table_stack;
        out = list;
        fileTable = f_table;
        heap = hp;
        procTable = pt;

        originalProgram = program.deepCopy();
        exeStack.push(program);
    }



    @Override
    public String toString() {

        return "ProgramState {\nProgramID = " + programID + "\nStack: " + exeStack.toString() + "\nTable: " +
                symTableStack.getContent().getFirst().toString() + "\nOut: "+ out.toString() + "\nFileTable: " + fileTable.toString() +
                "\nHeap: " + heap.toString() + "\nProcTable: " + procTable.toString() + "\n}";
    }

    public String toLogString() {

        StringBuilder str = new StringBuilder();

        str.append("{\n    ProgramID = " + programID);

        str.append("\n    ExeStack:\n");

        str.append(exeStack.toLogString());

        str.append("\n    SymTable:\n");

        str.append(symTableStack.getContent().getFirst().toLogString());

        str.append("\n    Out:\n");

        str.append(out.toLogString());

        str.append("\n    FileTable:\n");

        str.append(fileTable.toLogString());

        str.append("\n    Heap:\n");

        str.append(heap.toLogString());

        str.append("\n    ProcTable:\n");

        str.append(procTable.toLogString());

        str.append("}\n");

        return str.toString();
    }

    public boolean isNotCompleted() {

        return !getExeStack().isEmpty();
    }


    public ProgramState oneStep() throws ToyLangException {

        if (exeStack.isEmpty())
            throw new ToyLangException("Program state stack is empty");

        IStatement currentStatement = exeStack.pop();

        return currentStatement.execute(this);
    }
}
