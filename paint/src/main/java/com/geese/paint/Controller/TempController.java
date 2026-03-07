package com.geese.paint.Controller;

import com.geese.paint.Inputs.MouseAction;
import com.geese.paint.Inputs.MousePos;
import com.geese.paint.Inputs.macros.HistoryManager;
import com.geese.paint.Inputs.macros.MacroHandler;
import com.geese.paint.Inputs.macros.RedoMacro;
import com.geese.paint.Inputs.macros.UndoMacro;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TempController {
    @FXML private Canvas canvas;
    private GraphicsContext gc;

    //buttons here
    @FXML private Button greenButton;
    @FXML private Button redButton;
    @FXML private Button blueButton;
    @FXML private Button purpleButton;
    @FXML private Button yellowButton;
    @FXML private Button blackButton;
    @FXML private Button whiteButton;

    @FXML private Label statusLabel;


    private final MousePos mousePos = new MousePos();
    private final MouseAction mouseAction = new MouseAction();
    //macros
    private final MacroHandler macroHandler = new MacroHandler();
    private final HistoryManager history = new HistoryManager(canvas);

    private void setUpEventHandlers() {
        greenButton.setOnAction(e -> makePenGreen());
        redButton.setOnAction(e -> makePenRed());
        blueButton.setOnAction(e -> makePenBlue());
        purpleButton.setOnAction(e -> makePenPurple());
        yellowButton.setOnAction(e -> makePenYellow());
        blackButton.setOnAction(e -> makePenBlack());
        whiteButton.setOnAction(e -> makePenWhite());

    }

    @FXML
    public void initialize() {
        gc =  canvas.getGraphicsContext2D();
        setUpEventHandlers();

        canvas.widthProperty().bind(((Pane)canvas.getParent()).widthProperty());
        canvas.heightProperty().bind(((Pane)canvas.getParent()).heightProperty());

        canvas.setOnMouseMoved(e -> {
            mousePos.updatePos(e);
            statusLabel.setText(mousePos.getFormattedPos());
        });

        canvas.setOnMousePressed(e -> mouseAction.onMousePressed(e, gc));
        canvas.setOnMouseDragged(e -> mouseAction.onMouseDragged(e, gc));

        //macros move later and fix later
        /*macroHandler.registerMacros(KeyCode.Z, new UndoMacro(history));
        macroHandler.registerMacros(KeyCode.Y, new RedoMacro(history));

        canvas.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                newValue.setOnKeyPressed(event -> {
                    macroHandler.handleKey(event.getCode(), event.isControlDown());
                });
            }
        });*/

    }

    private void makePenGreen() {
        mouseAction.setCurrentColor(Color.GREEN);
        System.out.println("green");
    }

    private void makePenRed() {
        mouseAction.setCurrentColor(Color.RED);
        System.out.println("red");
    }
    private void makePenPurple() {
        mouseAction.setCurrentColor(Color.PURPLE);
        System.out.println("purple");
    }
    private void makePenYellow() {
        mouseAction.setCurrentColor(Color.YELLOW);
        System.out.println("blue");
    }
    private void makePenBlack() {
        mouseAction.setCurrentColor(Color.BLACK);
        System.out.println("blue");
    }
    private void makePenWhite() {
        mouseAction.setCurrentColor(Color.WHITE);
        System.out.println("blue");
    }
    private void makePenBlue() {
        mouseAction.setCurrentColor(Color.BLUE);
        System.out.println("blue");
    }
}
