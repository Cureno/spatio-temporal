package com.blexven.spatio_temporal.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Main.fxml"));

        Pane root = fxmlLoader.load();


        int width = 1300;
        int height = 900;

        Scene scene = new Scene(root, width, height);

        stage.setScene(scene);

        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Coordinate System");
        stage.show();
    }
}
