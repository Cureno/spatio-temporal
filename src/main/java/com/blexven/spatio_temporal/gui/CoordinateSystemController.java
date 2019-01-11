package com.blexven.spatio_temporal.gui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static javafx.scene.input.MouseEvent.*;

public class CoordinateSystemController implements Initializable {

    @FXML
    Pane coordinateSystem;

    @FXML
    ListView listView;

    private ContextMenu contextMenu = new ContextMenu(new MenuItem("Helló"));
    private double distance = 30;
    private double r = 16;
    private Circle origin;
    private Circle target;
    private Line line;
    private int gridStart = 1;
    private DecimalFormat df = new DecimalFormat("#");
    private SimpleDoubleProperty mouseX = new SimpleDoubleProperty();
    private SimpleDoubleProperty mouseY = new SimpleDoubleProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        coordinateSystem.addEventHandler(MOUSE_MOVED, e -> {
            mouseX.set(e.getX());
            mouseY.set(e.getY());
        });


        for (int i = gridStart; i < 20; i++) {

            distance = 30;

            Line horizontal = new Line(0, i * distance, coordinateSystem.getWidth(), i * distance);
            horizontal.endXProperty().bind(coordinateSystem.widthProperty());


            Line vertical = new Line(i * distance, 0, i * distance, coordinateSystem.getHeight());
            vertical.endYProperty().bind(coordinateSystem.heightProperty());


            coordinateSystem.getChildren().add(horizontal);
            coordinateSystem.getChildren().add(vertical);
        }


        for (int i = 1; i < 20; i++) {

            for (int j = 1; j < 20; j++) {

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

                    if (circle != origin) {
                        circle.setStroke(Color.BLACK);
                    }
                });

                circle.addEventHandler(MOUSE_EXITED, e -> {
                    if (circle != origin) {
                        if (circle.getStroke() == Color.BLACK) {
                            circle.setStroke(Color.TRANSPARENT);
                        }
                    }
                });

                coordinateSystem.getChildren().add(circle);
            }

        }

        coordinateSystem.getChildren().addListener((ListChangeListener<Node>) c -> {

            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(node -> {
                        if (node instanceof Line) {
                            Line line = (Line) node;
                            double startX = line.getStartX();
                            double startY = line.getStartY();

                            SimpleStringProperty startPosition = new SimpleStringProperty("startX " + startX + ", startY " + startY + ", ");

                            Text textRepresentation = new Text();

                            textRepresentation.textProperty().bind(
                                    startPosition.concat("endX ").concat(line.endXProperty()).concat(" endY ").concat(line.endYProperty()));


                            listView.getItems().add(textRepresentation);

                            textRepresentation.addEventHandler(MOUSE_ENTERED, event -> {
                                line.setStroke(Color.RED);
                            });

                            textRepresentation.addEventHandler(MOUSE_EXITED, event -> {
                                line.setStroke(Color.DARKBLUE);
                            });

                            textRepresentation.addEventHandler(MOUSE_CLICKED, event -> {
                                coordinateSystem.getChildren().remove(line);
                                listView.getItems().remove(textRepresentation);
                            });
                        }
                    });
                }
            }

        });

        drawRandomLines();
    }


    private void drawRandomLines() {

        List<Circle> circles = coordinateSystem.getChildren().filtered(n -> n instanceof Circle)
                                               .stream()
                                               .map(Circle.class::cast)
                                               .collect(Collectors.toList());

        Random random = new Random();

        for (int i = 0; i < 8; i++) {

            Circle origin = circles.get(
                    random.nextInt(circles.size())
            );

            Circle target = circles.get(
                    random.nextInt(circles.size())
            );


            drawLine(origin);
            drawLine(target);
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

        line.setMouseTransparent(true);
        line.endXProperty().bind(mouseX);
        line.endYProperty().bind(mouseY);

        coordinateSystem.getChildren().add(line);

        System.out.println("startline");
    }

    private void finishLine(Circle endPoint) {

        target = endPoint;
        target.setStrokeWidth(3);
        line.endXProperty().bind(endPoint.centerXProperty());
        line.endYProperty().bind(endPoint.centerYProperty());
        line.setStroke(Color.DARKBLUE);

        origin.setStroke(Color.TRANSPARENT);

        System.out.println("finishLine");

    }


}
