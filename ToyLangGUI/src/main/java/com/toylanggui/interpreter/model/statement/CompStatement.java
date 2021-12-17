package com.toylanggui.interpreter.model.statement;

import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.stack.IStack;
import com.toylanggui.interpreter.model.type.IType;

public class CompStatement implements IStatement {

    private final IStatement first;
    private final IStatement second;

    public CompStatement(IStatement s1, IStatement s2) {

        first = s1;
        second = s2;
    }

    @Override
    public String toString() {

        return "( " + first.toString() + " ; " + second.toString() + " )";
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IStack<IStatement> stack = state.getExeStack();

        stack.push(second);
        stack.push(first);

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public IStatement deepCopy() {
        return new CompStatement(first.deepCopy(), second.deepCopy());
    }
}
