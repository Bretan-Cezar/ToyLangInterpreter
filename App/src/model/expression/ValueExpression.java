package model.expression;

import model.dictionary.IDictionary;
import model.heap.IHeap;
import model.type.IType;
import model.value.IValue;
import model.exceptions.ToyLangException;

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
