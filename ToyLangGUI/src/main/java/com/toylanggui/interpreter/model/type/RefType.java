package com.toylanggui.interpreter.model.type;

import com.toylanggui.interpreter.model.value.IValue;
import com.toylanggui.interpreter.model.value.RefValue;

import java.util.Objects;

public class RefType implements IType {

    IType inner_type;

    public RefType(IType inner) {
        inner_type = inner;
    }

    public IType getInner() {
        return inner_type;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o instanceof RefType)
            return inner_type.equals(((RefType) o).getInner());

        else return false;
    }

    @Override
    public String toString() {
        return "Ref(" + inner_type.toString() + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(inner_type);
    }

    @Override
    public IType deepCopy() {

        return new RefType(inner_type.deepCopy());
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner_type);
    }
}
