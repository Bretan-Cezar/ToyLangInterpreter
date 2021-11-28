package model.expression;

import model.dictionary.IDictionary;
import model.heap.IHeap;
import model.value.IValue;
import model.exceptions.ToyLangException;

public interface IExpression {

    IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ToyLangException;

    IExpression deepCopy();
}
