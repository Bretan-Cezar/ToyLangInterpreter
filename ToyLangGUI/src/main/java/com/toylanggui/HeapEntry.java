package com.toylanggui;

import com.toylanggui.interpreter.model.value.IValue;
import javafx.beans.property.SimpleStringProperty;

public final class HeapEntry {

    private final SimpleStringProperty address;
    private final SimpleStringProperty value;

    public HeapEntry() {

        address = new SimpleStringProperty("");
        value = new SimpleStringProperty("");
    }
    public HeapEntry(Integer addr, IValue val) {

        address = new SimpleStringProperty(String.valueOf(addr));
        value = new SimpleStringProperty(val.toString());
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(Integer integer) {
        address.set(Integer.toString(integer));
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(IValue ivalue) {
        value.set(ivalue.toString());
    }
}
