package com.example.paoim4.utils;

import com.example.paoim4.Application;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Loader {
    private static FXMLLoader fxmlLoader;
    public static <T> T getScene(String fxmlToLoad){
        fxmlLoader = new FXMLLoader(Application.class.getResource(fxmlToLoad));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
