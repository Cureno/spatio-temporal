package com.blexven.spatio_temporal.gui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

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
    ListView<Text> listView;

    private Text infoBox = new Text("Info");

    private double distance = 30;
    private double r = 16;
    private Circle origin;
    private Circle target;
    private Line line;
    private int gridStart = 1;
    private DecimalFormat df = new DecimalFormat("#");
    private SimpleDoubleProperty mouseX = new SimpleDoubleProperty();
    private SimpleDoubleProperty mouseY = new SimpleDoubleProperty();
    private int numberOfLines = 25;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        infoBox.setRotationAxis(Rotate.X_AXIS);
        infoBox.setRotate(180);
        coordinateSystem.getChildren().add(infoBox);
        coordinateSystem.setCursor(Cursor.HAND);
        coordinateSystem.setRotationAxis(Rotate.X_AXIS);
        coordinateSystem.setRotate(180);


        coordinateSystem.addEventHandler(MOUSE_MOVED, e -> {
            mouseX.set(e.getX());
            mouseY.set(e.getY());
        });


        for (int i = gridStart; i < numberOfLines; i++) {

            Line horizontal = new Line(0, i * distance, coordinateSystem.getWidth(), i * distance);
            horizontal.endXProperty().bind(coordinateSystem.widthProperty());


            Line vertical = new Line(i * distance, 0, i * distance, coordinateSystem.getHeight());
            vertical.endYProperty().bind(coordinateSystem.heightProperty());

            horizontal.setStroke(Color.LIGHTGREY);
            vertical.setStroke(Color.LIGHTGREY);

            if (i == 1) {
                horizontal.setStrokeWidth(3);
                vertical.setStrokeWidth(3);
            }

            coordinateSystem.getChildren().add(horizontal);
            coordinateSystem.getChildren().add(vertical);
        }


        for (int i = 1; i < numberOfLines; i++) {

            for (int j = 1; j < numberOfLines; j++) {

                Circle circle = new Circle(i * distance, j * distance, r);

                circle.setFill(Color.TRANSPARENT);
                circle.setStroke(Color.TRANSPARENT);

                circle.addEventHandler(MOUSE_CLICKED, e -> {

                    if (e.getButton() == MouseButton.PRIMARY) {
                        drawLine(circle);
                    }

                });

                circle.addEventHandler(MOUSE_ENTERED, e -> {

                    if (circle != origin) {
                        circle.setStroke(Color.BLACK);
                    }

                    String xPosition = df.format(circle.getCenterX() / distance - gridStart);
                    String yPosition = df.format(circle.getCenterY() / distance - gridStart);
                    String position = "x : " + xPosition + "  y : " + yPosition;


                    infoBox.setText(position);
                    infoBox.setLayoutX(circle.getCenterX() + circle.getRadius() + 10);
                    infoBox.setLayoutY(circle.getCenterY() + circle.getRadius());

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
                                listView.getSelectionModel().select(textRepresentation);
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
