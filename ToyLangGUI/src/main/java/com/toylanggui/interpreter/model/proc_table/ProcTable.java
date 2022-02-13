package com.toylanggui.interpreter.model.proc_table;

import com.toylanggui.interpreter.model.statement.IStatement;
import com.toylanggui.interpreter.model.value.IValue;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcTable implements IProcTable {

    private HashMap<String, Pair<List<String>, IStatement>> data;

    public ProcTable() {

        data = new HashMap<>();
    }

    public boolean isKeyDefined(String key) {

        return data.containsKey(key);
    }

    public Pair<List<String>, IStatement> get(String key) {

        return data.get(key);
    }

    public void modify(String key, List<String> header, IStatement body) {

        data.put(key, new Pair<>(header, body));
    }

    @Override
    public HashMap<String, Pair<List<String>, IStatement>> getContent() {

        return data;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();

        str.append("[");

        for (HashMap.Entry<String, Pair<List<String>, IStatement>> entry : data.entrySet()) {

            str.append(entry.getKey().toString());
            str.append(" -> ");
            str.append(entry.getValue().toString());
            str.append(" ; ");
        }

        if (!data.isEmpty())
            str.delete(str.length()-3, str.length());

        str.append("]");

        return str.toString();
    }

    @Override
    public String toLogString() {

        StringBuilder str = new StringBuilder();

        for (Map.Entry<String, Pair<List<String>, IStatement>> entry : data.entrySet()) {

            str.append("    ");
            str.append(entry.getKey().toString());
            str.append(" -> ");
            str.append(entry.getValue().toString());
            str.append("\n");
        }

        return str.toString();
    }
}
