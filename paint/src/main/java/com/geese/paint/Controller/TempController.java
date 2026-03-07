package com.geese.paint.Controller;

import com.geese.paint.Inputs.MousePos;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class TempController {
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

    //buttons here
    @FXML private Button greenButton;
    @FXML private Button redButton;
    @FXML private Button blueButton;
    @FXML private Label statusLabel;


    private final MousePos mousePos = new MousePos();

    private void setUpEventHandlers() {
        greenButton.setOnAction(e -> makePenGreen());
        redButton.setOnAction(e -> makePenRed());
        blueButton.setOnAction(e -> makePenBlue());
    }

    @FXML
    public void initialize() {
        gc =  canvas.getGraphicsContext2D();
        setUpEventHandlers();

        canvas.setOnMouseMoved(e -> {
            mousePos.updatePos(e);
            statusLabel.setText(mousePos.getFormattedPos());
        });

        //delete this once we have colors
        gc.setFill(Color.BLUE);
        gc.fillRect(50, 50, 100, 100);

    }

    private void makePenGreen() {
        System.out.println("green");
    }

    private void makePenRed() {
        System.out.println("red");
    }
    private void makePenBlue() {
        System.out.println("blue");
    }
}
