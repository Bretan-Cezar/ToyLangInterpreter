package model.statement;

import model.ProgramState;
import model.exceptions.ToyLangException;

public interface IStatement {

    ProgramState execute(ProgramState state) throws ToyLangException;

    IStatement deepCopy();
}
