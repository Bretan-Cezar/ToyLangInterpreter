package com.toylanggui.interpreter.model.exceptions;

public class ToyLangException extends Exception {

    String message;

    public ToyLangException(String msg) {

        message = msg;
    }

    @Override
    public String getMessage() {

        return message;
    }
}
