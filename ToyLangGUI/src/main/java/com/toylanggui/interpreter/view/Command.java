package com.toylanggui.interpreter.view;

public abstract class Command {

    private String key;
    private String description;

    public Command(String k, String desc) {

        key = k;
        description = desc;
    }

    public abstract void execute();

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
