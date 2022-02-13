package com.toylanggui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoaderMain = new FXMLLoader(Application.class.getResource("main-window.fxml"));
        FXMLLoader fxmlLoaderSelection = new FXMLLoader(Application.class.getResource("selection-window.fxml"));

        Scene mainScene = new Scene(fxmlLoaderMain.load(), 1300, 500);

        MainController mainController = fxmlLoaderMain.getController();

        Scene selectionScene = new Scene(fxmlLoaderSelection.load(), 900, 500);

        mainController.setSelectorController(fxmlLoaderSelection.getController());

        stage.setTitle("Main Window");
        stage.setScene(mainScene);

        Stage selectionWindow = new Stage();

        stage.onCloseRequestProperty().setValue(windowEvent -> selectionWindow.close());

        selectionWindow.setTitle("Program Selection Window");
        selectionWindow.setScene(selectionScene);

        stage.show();
        selectionWindow.show();
    }

    public static void main(String[] args) {

        launch();
    }
}