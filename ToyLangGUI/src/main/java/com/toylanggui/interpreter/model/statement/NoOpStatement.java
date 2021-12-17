package com.toylanggui.interpreter.model.statement;


import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.type.IType;

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
