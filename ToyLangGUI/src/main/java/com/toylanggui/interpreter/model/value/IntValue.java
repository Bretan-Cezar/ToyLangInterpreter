package com.toylanggui.interpreter.model.value;

import com.toylanggui.interpreter.model.type.IType;
import com.toylanggui.interpreter.model.type.IntType;

import java.util.Objects;

public class IntValue implements IValue {

    private final int value;

    public IntValue(int val) {
        value = val;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue deepCopy() {

        return new IntValue(Integer.valueOf(value));
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {

        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        IntValue intValue = (IntValue) o;
        return value == intValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
