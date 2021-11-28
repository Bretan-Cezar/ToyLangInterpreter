package model.value;

import model.type.BoolType;
import model.type.IType;

import java.util.Objects;

public class BoolValue implements IValue {

    private final boolean value;

    public BoolValue(boolean val) {
        value = val;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public IValue deepCopy() {

        return new BoolValue(Boolean.valueOf(value));
    }

    @Override
    public String toString() {

        return Boolean.toString(value);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        BoolValue boolValue = (BoolValue) o;
        return value == boolValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
