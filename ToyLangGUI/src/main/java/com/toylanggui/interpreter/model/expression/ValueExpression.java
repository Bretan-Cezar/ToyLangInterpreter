package com.toylanggui.interpreter.model.expression;


import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.heap.IHeap;
import com.toylanggui.interpreter.model.type.IType;
import com.toylanggui.interpreter.model.value.IValue;

public class ValueExpression implements IExpression {

    private final IValue exp;

    public ValueExpression(IValue e) {

        exp = e;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ToyLangException {
        return exp;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {
        return exp.getType();
    }

    @Override
    public IExpression deepCopy() {

        return new ValueExpression(exp.deepCopy());
    }

    @Override
    public String toString() {

        return exp.toString();
    }
}
