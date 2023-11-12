package com.ui;

import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage)  {
        Model.getInstance().getView().showView();
    }

    public static void main(String[] args) {
        launch();
    }
}