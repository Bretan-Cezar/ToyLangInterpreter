package com.toylanggui.interpreter.model.type;


import com.toylanggui.interpreter.model.value.BoolValue;
import com.toylanggui.interpreter.model.value.IValue;

public class BoolType implements IType {

    @Override
    public boolean equals(Object obj) {

        return (obj instanceof BoolType);
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }
}
