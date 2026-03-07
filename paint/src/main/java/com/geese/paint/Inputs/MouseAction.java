package com.geese.paint.Inputs;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MouseAction {
    private Color currentColor = Color.BLACK;
    private double brushSize = 5.0;
    private double lastX, lastY;

    public void onMousePressed(MouseEvent event, GraphicsContext gc) {
        gc.setFill(currentColor);
        draw(event, gc);
        lastX = event.getX();
        lastY = event.getY();

        gc.fillOval(event.getX() - 2.5, event.getY() - 2.5, 5, 5);
    }

    public void onMouseDragged(MouseEvent event, GraphicsContext gc) {
        gc.setStroke(currentColor);
        gc.setLineWidth(brushSize);
        gc.strokeLine(lastX, lastY, event.getX(), event.getY());

        lastX = event.getX();
        lastY = event.getY();
    }

    private void draw(MouseEvent mouseEvent, GraphicsContext gc) {
        double y = mouseEvent.getY() - brushSize / 2;
        double x = mouseEvent.getX() - brushSize / 2;
        gc.fillOval(x, y, brushSize, brushSize);
    }

    public void setCurrentColor(Color currentColor) { this.currentColor = currentColor; }
}
