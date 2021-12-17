package com.toylanggui.interpreter.model.list;

import java.util.ArrayList;

public interface IList<T> {

    void append(T value);

    int length();

    T get(int pos);

    String toLogString();

    ArrayList<T> getContent();

    void setContent(java.util.List<T> l);
}
