package model.statement;

import model.ProgramState;
import model.dictionary.Dictionary;
import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.stack.IStack;
import model.stack.Stack;
import model.value.IValue;

import java.util.HashMap;

public class ForkStatement implements IStatement {

    IStatement statement;

    public ForkStatement(IStatement s) {

        statement = s;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IDictionary<String, IValue> newSymTable = clone(state.getSymTable());

        return new ProgramState(new Stack<>(), newSymTable, state.getOut(), state.getFileTable(), state.getHeap(), statement);
    }


    private IDictionary<String, IValue> clone(IDictionary<String, IValue> symTable) {

        IDictionary<String, IValue> newDictionary = new Dictionary<>();

        symTable.getContent()
                .forEach((key, value) -> newDictionary.modify(String.valueOf(key), value.deepCopy()));

        return newDictionary;
    }

    @Override
    public IStatement deepCopy() {

        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString() {

        return "fork( " + statement.toString() + " )";
    }
}
