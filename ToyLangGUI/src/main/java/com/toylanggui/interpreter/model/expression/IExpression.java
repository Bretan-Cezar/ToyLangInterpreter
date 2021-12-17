package com.toylanggui.interpreter.model.expression;


import com.toylanggui.interpreter.model.dictionary.IDictionary;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;
import com.toylanggui.interpreter.model.heap.IHeap;
import com.toylanggui.interpreter.model.type.IType;
import com.toylanggui.interpreter.model.value.IValue;

public interface IExpression {

    IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ToyLangException;

    IType typecheck(IDictionary<String, IType> typeEnv) throws ToyLangException;

    IExpression deepCopy();
}
