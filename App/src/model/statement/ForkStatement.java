package model.statement;

import model.ProgramState;
import model.dictionary.Dictionary;
import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.stack.Stack;
import model.type.IType;
import model.value.IValue;


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

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {
        return null;
    }


    private IDictionary<String, IValue> clone(IDictionary<String, IValue> symTable) {

        IDictionary<String, IValue> newDictionary = new Dictionary<>();

        symTable.getContent()
                .forEach((key, value) -> newDictionary.modify(String.valueOf(key), value.deepCopy()));

        return newDictionary;
    }

    private IDictionary<String, IType> clone_env(IDictionary<String, IType> typeEnv) {

        IDictionary<String, IType> newDictionary = new Dictionary<>();

        typeEnv.getContent()
                .forEach((id, type) -> newDictionary.modify(String.valueOf(id), type.deepCopy()));

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
