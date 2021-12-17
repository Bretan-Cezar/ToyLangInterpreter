package com.toylanggui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ExceptionController {

    @FXML
    private Label exceptionMessageLabel;

    public ExceptionController() {

        exceptionMessageLabel = new Label();
    }

    public void setExceptionMessage(String msg) {

        exceptionMessageLabel.setText(msg);
    }

    public void initialize() {


    }
}
