package model.expression;

import model.dictionary.IDictionary;
import model.heap.IHeap;
import model.type.IType;
import model.value.IValue;
import model.exceptions.ToyLangException;

public interface IExpression {

    IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ToyLangException;

    IType typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException;

    IExpression deepCopy();
}
