package com.blexven.spatio_temporal.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import static javafx.scene.input.MouseEvent.*;

public class CoordinateSystemController implements Initializable {

    @FXML
    Pane innerPane;

    private ContextMenu contextMenu = new ContextMenu(new MenuItem("Helló"));
    private double distance = 30;
    private double r = 16;
    private Circle origin;
    private Circle target;
    private Line line;
    private int gridStart = 3;
    private DecimalFormat df = new DecimalFormat("#");

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        for (int i = gridStart; i < 20; i++) {

            int size = 30;

            Line horizontal = new Line(0, i * size, innerPane.getWidth(), i * size);
            horizontal.endXProperty().bind(innerPane.widthProperty());


            Line vertical = new Line(i * size, 0, i * size, innerPane.getHeight());
            vertical.endYProperty().bind(innerPane.heightProperty());


            innerPane.getChildren().add(horizontal);
            innerPane.getChildren().add(vertical);
        }


        for (int i = 3; i < 15; i++) {

            for (int j = 3; j < 15; j++) {

                Circle circle = new Circle(i * distance, j * distance, r);

                circle.setFill(Color.TRANSPARENT);
                circle.setStroke(Color.TRANSPARENT);

                circle.addEventHandler(MOUSE_CLICKED, e -> {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        contextMenu.getItems().clear();

                        String xPosition = df.format(circle.getCenterX() / distance - gridStart);
                        String yPosition = df.format(circle.getCenterY() / distance - gridStart);
                        String position = "x : " + xPosition + "\ty : " + yPosition;
                        contextMenu.getItems().add(new MenuItem(position));
                        contextMenu.show(circle, Side.RIGHT, 0, 0);
                    }
                });


                circle.addEventHandler(MOUSE_CLICKED, e -> {

                    if (e.getButton() == MouseButton.PRIMARY) {
                        drawLine(circle);
                    }

                });

                circle.addEventHandler(MOUSE_ENTERED, e -> {


                    if (circle != origin /*&& lineBeingCreated*/)
                        circle.setStroke(Color.BLACK);
                });

                circle.addEventHandler(MOUSE_EXITED, e -> {
                    if (circle != origin) {
                        if (circle.getStroke() == Color.BLACK) {
                            circle.setStroke(Color.TRANSPARENT);
                        }
                    }
                });

                innerPane.getChildren().add(circle);
            }

        }
    }

    private void drawLine(Circle pointClicked) {
        if (origin == null) {
            startLine(pointClicked);

            origin = pointClicked;
        }
        else if (pointClicked != origin) {
            finishLine(pointClicked);

            origin = null;
        }
    }

    private void startLine(Circle startPoint) {
        origin = startPoint;
        origin.setStroke(Color.BLACK);
        origin.setStrokeType(StrokeType.OUTSIDE);
        origin.setStrokeWidth(3);

        line = new Line();
        line.setStroke(Color.RED);
        line.setStrokeWidth(3);
        line.startXProperty().bind(startPoint.centerXProperty());
        line.startYProperty().bind(startPoint.centerYProperty());

        System.out.println("startline");
    }

    private void finishLine(Circle endPoint) {

        target = endPoint;

        line.endXProperty().bind(endPoint.centerXProperty());
        line.endYProperty().bind(endPoint.centerYProperty());

        origin.setStroke(Color.TRANSPARENT);

        System.out.println("finishLine");

        innerPane.getChildren().add(line);
    }
}
