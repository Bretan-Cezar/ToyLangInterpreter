package com.toylanggui;

import com.toylanggui.interpreter.model.statement.IStatement;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Pair;

import java.util.List;

public final class ProcTableEntry {

    private SimpleStringProperty name;
    private SimpleStringProperty value;

    public ProcTableEntry(String name, Pair<List<String>, IStatement> value) {

        this.name = new SimpleStringProperty(name);
        this.value = new SimpleStringProperty(value.toString());
    }

    public SimpleStringProperty nameProperty(){
        return this.name;
    }

    public SimpleStringProperty valueProperty(){
        return this.value;
    }

    public String getName(){
        return this.name.get();
    }

    public String getValue(){
        return this.value.get();
    }

    public void setName(Integer newName){
        this.name.set(Integer.toString(newName));
    }

    public void setValue(Integer newValue){
        this.value.setValue(Integer.toString(newValue));
    }
}
