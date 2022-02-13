package com.toylanggui.interpreter.model.proc_table;

import com.toylanggui.interpreter.model.statement.IStatement;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public interface IProcTable {

    boolean isKeyDefined(String key);

    Pair<List<String>, IStatement> get(String key);

    void modify(String key, List<String> header, IStatement body);

    HashMap<String, Pair<List<String>, IStatement>> getContent();

    String toLogString();
}
