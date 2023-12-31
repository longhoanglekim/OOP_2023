package com.controller;

import com.dictionary.Local;
import com.ui.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WindowController implements Initializable {
    @FXML
    public BorderPane rootWindow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootWindow.setLeft(Model.getInstance().getView().getDashboardPane());
        rootWindow.setCenter(Model.getInstance().getView().getDictionaryPane());

        Model.getInstance().getView().getSelectedMenuItem().addListener((observableValue, oldValue, newValue) -> {
            switch (newValue) {
                case "BarOpen":
                    AnchorPane dO = Model.getInstance().getView().getDashboardPane();
                    dO.setPrefWidth(120);
                    rootWindow.setLeft(dO);
                    break;
                case "BarClose":
                    AnchorPane dC = Model.getInstance().getView().getDashboardPane();
                    dC.setPrefWidth(60);
                    rootWindow.setLeft(dC);
                    break;
                case "Translate":
                    rootWindow.setCenter(Model.getInstance().getView().getTranslatePane());
                    break;
                case "Game":
                    rootWindow.setCenter(Model.getInstance().getView().getGamePane());
                    break;
                case "Flashcard":
                    rootWindow.setCenter(Model.getInstance().getView().getFlashcardPane());
                    break;
                case "toLocal":
                    Model.dictionary = new Local();
                    break;
                case "toSQL":
                    Model.getInstance().getView().showLoginSQLView();
                    break;
                default:
                    rootWindow.setCenter(Model.getInstance().getView().getDictionaryPane());
                    break;
            }
        });
    }

}
