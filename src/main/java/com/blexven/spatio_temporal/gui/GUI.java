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


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CoordinateSystem.fxml"));

        Pane root = fxmlLoader.load();

        Scene scene = new Scene(root, 640, 500);

        stage.setScene(scene);

        stage.setWidth(640);
        stage.setHeight(500);
        stage.setTitle("Coordinate System");
        stage.show();
    }
}
