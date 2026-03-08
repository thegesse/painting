package com.geese.paint.Inputs.macros;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

import java.util.Stack;

public class HistoryManager {
    private final Stack<WritableImage> undoStack = new Stack<>();
    private final Stack<WritableImage> redoStack = new Stack<>();
    private final Canvas canvas;

    public HistoryManager(Canvas canvas) {
        this.canvas = canvas;
    }

    public void saveState() {
        WritableImage writableImage = canvas.snapshot(null, null);
        undoStack.push(writableImage);
        redoStack.clear();
    }

    public void undo() {
        if(!undoStack.isEmpty()) {
            redoStack.push(canvas.snapshot(null, null));
            drawState(undoStack.pop());
        }
    }

    public void restorePreviewState() {
        if(!undoStack.isEmpty()) {
            drawState(undoStack.peek());
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(canvas.snapshot(null, null));
            drawState(redoStack.pop());
        }
    }

    public void drawState(WritableImage image) {
        canvas.getGraphicsContext2D().drawImage(image, 0, 0);
    }
}
