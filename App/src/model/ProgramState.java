package model;

import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.heap.IHeap;
import model.list.IList;
import model.stack.IStack;
import model.statement.IStatement;
import model.value.IValue;
import model.value.StringValue;
import java.io.BufferedReader;


public class ProgramState {

    private IStack<IStatement> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IStatement originalProgram;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IHeap heap;
    private static int id;

    public IStack<IStatement> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
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

    public ProgramState(IStack<IStatement> stack, IDictionary<String, IValue> s_table, IList<IValue> list, IDictionary<StringValue, BufferedReader> f_table, IHeap hp, IStatement program) {

        id = 1;
        exeStack = stack;
        symTable = s_table;
        out = list;
        fileTable = f_table;
        heap = hp;
        originalProgram = program.deepCopy();
        exeStack.push(program);
    }

    public static synchronized void manageID() {


    }

    @Override
    public String toString() {

        return "ProgramState {\nProgramID = " + id + "\nStack: " + exeStack.toString() + "\nTable: " +
                symTable.toString() + "\nOut: "+ out.toString() + "\nFileTable: " + fileTable.toString() + "\nHeap: " + heap.toString() + "\n}";
    }

    public String toLogString() {

        StringBuilder str = new StringBuilder();

        str.append("{\n    ProgramID = " + id);

        str.append("\n    ExeStack:\n");

        str.append(exeStack.toLogString());

        str.append("\n    SymTable:\n");

        str.append(symTable.toLogString());

        str.append("\n    Out:\n");

        str.append(out.toLogString());

        str.append("\n    FileTable:\n");

        str.append(fileTable.toLogString());

        str.append("\n    Heap:\n");

        str.append(heap.toLogString());

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