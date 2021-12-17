package model.statement;

import model.ProgramState;
import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.type.IType;

public class NoOpStatement implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {
        return typeEnv;
    }

    @Override
    public String toString() {

        return "nop";
    }

    @Override
    public IStatement deepCopy() {

        return new NoOpStatement();
    }
}
