package model.stack;

public interface IStack<T> {

    T pop();
    void push(T value);
    boolean isEmpty();

    String toLogString();
}
