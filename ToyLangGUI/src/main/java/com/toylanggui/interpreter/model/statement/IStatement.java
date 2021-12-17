package com.toylanggui.interpreter.model.statement;


import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.type.IType;

public interface IStatement {

    ProgramState execute(ProgramState state) throws ToyLangException;

    IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException;

    IStatement deepCopy();
}
