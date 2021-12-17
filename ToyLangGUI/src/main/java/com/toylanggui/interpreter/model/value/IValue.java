package com.toylanggui.interpreter.model.value;

import com.toylanggui.interpreter.model.type.IType;

public interface IValue {

    IType getType();

    IValue deepCopy();
}
