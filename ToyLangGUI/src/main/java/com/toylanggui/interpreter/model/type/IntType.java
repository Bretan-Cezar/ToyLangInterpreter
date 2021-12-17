package com.toylanggui.interpreter.model.type;

import com.toylanggui.interpreter.model.value.IValue;
import com.toylanggui.interpreter.model.value.IntValue;

public class IntType implements IType {

    @Override
    public boolean equals(Object obj) {

        return (obj instanceof IntType);
    }

    @Override
    public String toString() {

        return "int";
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }
}
