package model.statement;

import model.ProgramState;
import model.dictionary.IDictionary;
import model.exceptions.ToyLangException;
import model.type.IType;

public interface IStatement {

    ProgramState execute(ProgramState state) throws ToyLangException;

    IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException;

    IStatement deepCopy();
}
