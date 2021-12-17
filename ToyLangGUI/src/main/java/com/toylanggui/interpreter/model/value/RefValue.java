package com.toylanggui.interpreter.model.value;


import com.toylanggui.interpreter.model.type.IType;
import com.toylanggui.interpreter.model.type.RefType;

public class RefValue implements IValue {

    int address;
    IType locationType;

    public RefValue(int addr, IType locT) {

        address = addr;
        locationType = locT;
    }


    public int getAddr() {
        return address;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public IValue deepCopy() {

        return new RefValue(Integer.valueOf(address), locationType.deepCopy());
    }

    @Override
    public String toString() {

        return "(" + address + ", " + locationType.toString() + ")";
    }
}
