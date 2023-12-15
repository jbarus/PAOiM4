package com.example.paoim4;

import com.example.paoim4.back.DatabaseManager;
import com.example.paoim4.utils.Loader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(Loader.getScene("main-view.fxml"));
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        DatabaseManager.getInstance();
        launch();
    }
}