package com.geese.paint.Controller;

import com.geese.paint.Inputs.MousePos;
import com.geese.paint.Inputs.Tools.PenTool;
import com.geese.paint.Inputs.Tools.Shapes.RectangleTool;
import com.geese.paint.Inputs.Tools.Tools;
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

    // Tools & State
    private Tools currentTool;
    private Color currentColor = Color.BLACK;
    private double currentBrushSize = 5.0;

    // UI Elements
    @FXML private Button greenButton, redButton, blueButton, purpleButton, yellowButton, blackButton, whiteButton;
    @FXML private Label statusLabel;

    // Logic Helpers
    private final MousePos mousePos = new MousePos();
    private final MacroHandler macroHandler = new MacroHandler();
    private HistoryManager history;

    @FXML
    public void initialize() {
        // 1. Initialize Canvas & Graphics
        gc = canvas.getGraphicsContext2D();

        // 2. Initialize History (After canvas is injected)
        this.history = new HistoryManager(canvas);

        // 3. Set default tool
        currentTool = new PenTool(currentColor, currentBrushSize);

        setUpEventHandlers();
        setupCanvasListeners();
        setupMacros();
    }

    private void setupCanvasListeners() {
        // Resizing
        canvas.widthProperty().bind(((Pane)canvas.getParent()).widthProperty());
        canvas.heightProperty().bind(((Pane)canvas.getParent()).heightProperty());

        // Mouse Movement (Status bar)
        canvas.setOnMouseMoved(e -> {
            mousePos.updatePos(e);
            statusLabel.setText(mousePos.getFormattedPos());
        });

        // DRAWING LOGIC: Call currentTool directly
        canvas.setOnMousePressed(e -> {
            canvas.requestFocus();
            history.saveState(); // Capture state before stroke
            currentTool.onPressed(e, gc);
        });

        canvas.setOnMouseDragged(e -> {
            // Rubber-banding logic for shapes
            if (currentTool instanceof RectangleTool) {
                history.restorePreviewState();
            }
            currentTool.onDragged(e, gc);
        });

        canvas.setOnMouseReleased(e -> {
            if(currentTool instanceof RectangleTool) {
                history.saveState();
            }
        });
    }

    private void setupMacros() {
        macroHandler.registerMacros(KeyCode.Z, new UndoMacro(history));
        macroHandler.registerMacros(KeyCode.Y, new RedoMacro(history));

        canvas.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    macroHandler.handleKey(event.getCode(), event.isControlDown());
                });
            }
        });
    }

    private void setUpEventHandlers() {
        greenButton.setOnAction(e -> changeColor(Color.GREEN));
        redButton.setOnAction(e -> changeColor(Color.RED));
        blueButton.setOnAction(e -> changeColor(Color.BLUE));
        purpleButton.setOnAction(e -> changeColor(Color.PURPLE));
        yellowButton.setOnAction(e -> changeColor(Color.YELLOW));
        blackButton.setOnAction(e -> changeColor(Color.BLACK));
        whiteButton.setOnAction(e -> changeColor(Color.WHITE));
    }

    private void changeColor(Color color) {
        this.currentColor = color;
        System.out.println("Color changed to: " + color.toString());

        // Update the current tool with the new color
        if (currentTool instanceof PenTool) {
            currentTool = new PenTool(currentColor, currentBrushSize);
        } else if (currentTool instanceof RectangleTool) {
            currentTool = new RectangleTool(currentColor);
        }
    }

    @FXML
    public void selectRectangle() {
        currentTool = new RectangleTool(currentColor);
    }

    @FXML
    public void selectPen() {
        currentTool = new PenTool(currentColor, currentBrushSize);
    }
}