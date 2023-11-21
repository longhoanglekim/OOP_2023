package com.ui;

import com.dictionary.Bookmark;
import com.dictionary.Database;
import com.dictionary.Dictionary;
import com.dictionary.Local;
import javafx.application.Platform;

public class Model {
    private final View view;
    private static Model instance;

    public static Dictionary dictionary;
    public static Bookmark bookmark;

    private Model() {
//        dictionary = new Database();
//        if (!dictionary.initialize()) {
        dictionary = new Local();
        if (!dictionary.initialize()) {
            System.out.println("Cannot initialize dictionary");
            Platform.exit();
        }
        bookmark = new Bookmark();
//        }
        view = new View();
    }

    public View getView() {
        return view;
    }

    /**
     * Get instance of model
     * @return instance of model
     */
    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
}
