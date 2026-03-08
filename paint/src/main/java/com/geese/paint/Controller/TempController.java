package com.geese.paint.Controller;

import com.geese.paint.Inputs.MousePos;
import com.geese.paint.Inputs.Tools.BucketTool;
import com.geese.paint.Inputs.Tools.EraserTool;
import com.geese.paint.Inputs.Tools.PenTool;
import com.geese.paint.Inputs.Tools.Shapes.CircleTool;
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
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TempController {
    @FXML private Canvas canvas;
    private GraphicsContext gc;

    private Tools currentTool;
    private Color currentColor = Color.BLACK;
    private double currentBrushSize = 5.0;

    @FXML private Button greenButton, redButton, blueButton, purpleButton, yellowButton, blackButton, whiteButton;
    @FXML private Label statusLabel;
    @FXML private Label sizeLabel;
    @FXML private Slider sizeSlider;


    private final MousePos mousePos = new MousePos();
    private final MacroHandler macroHandler = new MacroHandler();
    private HistoryManager history;

    @FXML
    public void initialize() {

        gc = canvas.getGraphicsContext2D();
        this.history = new HistoryManager(canvas);

        currentTool = new PenTool(currentColor, currentBrushSize);

        setupCanvasListeners();
        setupSliderListener();
        setUpEventHandlers();
        setupMacros();
    }

    private void setupCanvasListeners() {
        canvas.widthProperty().bind(((Pane)canvas.getParent()).widthProperty());
        canvas.heightProperty().bind(((Pane)canvas.getParent()).heightProperty());

        canvas.setOnMouseMoved(e -> {
            mousePos.updatePos(e);
            statusLabel.setText(mousePos.getFormattedPos());
        });

        canvas.setOnMousePressed(e -> {
            canvas.requestFocus();
            history.saveState();
            currentTool.onPressed(e, gc);
        });

        canvas.setOnMouseDragged(e -> {
            if (currentTool instanceof RectangleTool || currentTool instanceof CircleTool) {
                history.restorePreviewState();
            }
            currentTool.onDragged(e, gc);
        });

        canvas.setOnMouseReleased(e -> {
            history.saveState();
        });
        canvas.widthProperty().addListener((obs, oldval, newval) -> {
            if(history != null) {
                history.restorePreviewState();
            }
        });

        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            if(history != null) {
                history.restorePreviewState();
            }
        });
    }

    private void setupSliderListener() {
        sizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentBrushSize = newVal.doubleValue();
            sizeLabel.setText(String.format("%.1f", currentBrushSize));
            refreshTool();
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

        yellowButton.setOnAction(e -> changeColor(Color.YELLOW));
        blackButton.setOnAction(e -> changeColor(Color.BLACK));
        whiteButton.setOnAction(e -> changeColor(Color.WHITE));
    }

    private void changeColor(Color color) {
        this.currentColor = color;
        System.out.println("Color changed to: " + color.toString());
        refreshTool();
    }

    private void refreshTool() {
        if (currentTool instanceof PenTool) {
            currentTool = new PenTool(currentColor, currentBrushSize);
        } else if (currentTool instanceof RectangleTool) {
            currentTool = new RectangleTool(currentColor, currentBrushSize);
        } else if (currentTool instanceof CircleTool) {
            currentTool = new CircleTool(currentColor, currentBrushSize);
        } else if (currentTool instanceof BucketTool) {
            currentTool = new BucketTool(currentColor);
        } else if (currentTool instanceof EraserTool) {
            currentTool = new EraserTool(currentBrushSize);
        }
    }


    @FXML
    public void selectRectangle() {
        currentTool = new RectangleTool(currentColor, currentBrushSize);
    }

    @FXML
    public void selectPen() {
        currentTool = new PenTool(currentColor, currentBrushSize);
    }

    @FXML
    public void selectCircle() {
        currentTool = new CircleTool(currentColor, currentBrushSize);
    }

    @FXML
    public void selectBucket() {
        currentTool = new BucketTool(currentColor);
    }

    @FXML
    public void selectEraser() {
        currentTool = new EraserTool(currentBrushSize);
    }

    @FXML
    public void clearCanvas() {
        history.saveState();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        System.out.println("Canvas cleared.");
    }

    @FXML
    public void saveImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(("save Drawing"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Image", "*.png")
        );

        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        if(file != null) {
            try {
                WritableImage snapshot = canvas.snapshot(null, null);
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
                System.out.println("Image save to" + file.getAbsolutePath());
            } catch (IOException ex) {
                System.err.println("failed to save image" + ex.getMessage());
            }

        }
    }
}