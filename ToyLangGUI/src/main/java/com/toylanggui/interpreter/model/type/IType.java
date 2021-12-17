package com.toylanggui.interpreter.model.type;

import com.toylanggui.interpreter.model.value.IValue;

public interface IType {

    IType deepCopy();

    IValue defaultValue();
}
