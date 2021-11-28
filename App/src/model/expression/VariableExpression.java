package model.expression;

import model.dictionary.IDictionary;
import model.heap.IHeap;
import model.value.IValue;
import model.exceptions.ToyLangException;

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
    public IExpression deepCopy() {

        return new VariableExpression(String.valueOf(id));
    }
}
