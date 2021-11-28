package model.statement;

import model.ProgramState;
import model.exceptions.ToyLangException;

public class NoOpStatement implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {
        return state;
    }

    @Override
    public IStatement deepCopy() {

        return new NoOpStatement();
    }
}
