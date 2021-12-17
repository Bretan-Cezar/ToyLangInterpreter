package com.toylanggui.interpreter.model.expression;


import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.heap.IHeap;
import com.toylanggui.interpreter.model.type.IType;
import com.toylanggui.interpreter.model.value.IValue;

public class VariableExpression implements IExpression {

    private final String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ToyLangException {
        return table.get(id);
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException {

        return typeEnv.get(id);
    }

    @Override
    public IExpression deepCopy() {

        return new VariableExpression(String.valueOf(id));
    }
}
