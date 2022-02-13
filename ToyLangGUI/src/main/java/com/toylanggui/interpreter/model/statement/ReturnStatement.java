package com.toylanggui.interpreter.model.statement;

import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.type.IType;

public class ReturnStatement implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        state.getSymTableStack().pop();

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "return";
    }
}
