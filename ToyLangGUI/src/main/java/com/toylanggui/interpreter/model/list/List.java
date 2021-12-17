package com.toylanggui.interpreter.model.list;

import java.util.ArrayList;

public class List<T> implements IList<T> {

    private ArrayList<T> list;

    public List(int length) {

        list = new ArrayList<>(length);
    }
    @Override
    public void append(T value) {

        list.add(value);
    }

    @Override
    public int length() {
        return list.size();
    }

    @Override
    public T get(int pos) {
        return list.get(pos);
    }

    public T first() {
        return list.get(0);
    }

    public T last() {
        return list.get(list.size()-1);
    }

    @Override
    public String toString() {

        return list.toString();
    }

    @Override
    public String toLogString() {

        StringBuilder str = new StringBuilder();

        for (T item : list) {

            str.append("    ");
            str.append(item.toString());
            str.append("\n");
        }

        return str.toString();
    }

    @Override
    public ArrayList<T> getContent() {
        return list;
    }

    @Override
    public void setContent(java.util.List<T> l) {

        list.clear();
        list.addAll(l);
    }


}
