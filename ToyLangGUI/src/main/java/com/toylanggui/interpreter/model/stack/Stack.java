package com.toylanggui.interpreter.model.stack;

import java.util.ArrayDeque;

public class Stack<T> implements IStack<T> {

    private ArrayDeque<T> stack;

    public Stack() {

        stack = new ArrayDeque<>();
    }

    @Override
    public T pop() {

        return stack.pop();
    }

    @Override
    public void push(T value) {

        stack.push(value);
    }

    @Override
    public boolean isEmpty() {
        return (stack.size() == 0);
    }

    @Override
    public String toString() {

        return stack.toString();
    }

    public String toLogString() {

        StringBuilder str = new StringBuilder();

        Object[] arr = stack.toArray();

        for (Object o : arr) {

            str.append("    ");
            str.append(o.toString());
            str.append("\n");
        }

        return str.toString();
    }

    @Override
    public ArrayDeque<T> getContent() {
        return stack;
    }
}
