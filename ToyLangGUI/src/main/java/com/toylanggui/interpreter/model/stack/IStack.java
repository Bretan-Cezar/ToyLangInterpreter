package com.toylanggui.interpreter.model.stack;

import java.util.ArrayDeque;

public interface IStack<T> {

    T pop();
    void push(T value);
    boolean isEmpty();

    String toLogString();

    ArrayDeque<T> getContent();
}
