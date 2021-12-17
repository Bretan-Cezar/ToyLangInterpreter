package com.toylanggui.interpreter.view;

import com.toylanggui.interpreter.controller.Controller;
import com.toylanggui.interpreter.model.exceptions.ToyLangException;

public class RunExample extends Command {

    private Controller controller;

    public RunExample(String k, String desc, Controller ctrl) {

        super(k, desc);
        controller = ctrl;
    }

    @Override
    public void execute() {

        try {
            controller.allStep();
        }
        catch (ToyLangException tle) {

            System.out.println(tle.getMessage());
        }
    }

    public Controller getController() {
        return controller;
    }
}
