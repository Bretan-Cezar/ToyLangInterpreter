package com.toylanggui.interpreter.model.statement;

import com.toylanggui.interpreter.model.ProgramState;
import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.expression.IExpression;
import com.toylanggui.interpreter.model.type.IType;
import com.toylanggui.interpreter.model.value.IValue;

public class PrintStatement implements IStatement {

    private final IExpression exp;

    public PrintStatement(IExpression e) {
        exp = e;
    }

    @Override
    public String toString() {

        return "print(" + exp.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLangException {

        IValue value = exp.evaluate(state.getSymTableStack().getContent().getFirst(), state.getHeap());

        System.out.println(value);
        state.getOut().append(value);

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(exp.deepCopy());
    }
}
