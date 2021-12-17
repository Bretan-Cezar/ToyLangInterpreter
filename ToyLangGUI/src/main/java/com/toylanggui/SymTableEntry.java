package com.toylanggui;

import com.toylanggui.interpreter.model.value.IValue;
import javafx.beans.property.SimpleStringProperty;

public final class SymTableEntry {

    private final SimpleStringProperty varName;
    private final SimpleStringProperty value;

    public SymTableEntry() {

        varName = new SimpleStringProperty();
        value = new SimpleStringProperty();
    }

    public SymTableEntry(String name, IValue val) {

        varName = new SimpleStringProperty(name);
        value = new SimpleStringProperty(val.toString());
    }

    public SimpleStringProperty varNameProperty() {
        return varName;
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public String getVarName() {
        return varName.get();
    }

    public String getValue() {
        return value.get();
    }

    public void setVarName(String name) {
        varName.set(name);
    }

    public void setValue(IValue val) {
        value.set(val.toString());
    }

}
