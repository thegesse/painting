package com.geese.paint.Inputs.Tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class PenTool implements Tools{
    private double lastX, lastY;
    private Color color;
    private double size;

    public PenTool(Color color, double size) {
        this.color = color;
        this.size = size;
    }

    @Override
    public void onPressed(MouseEvent e, GraphicsContext gc) {
        lastX = e.getX();
        lastY = e.getY();
        gc.setStroke(color);
        gc.setLineWidth(size);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.strokeLine(lastX, lastY, lastX, lastY);
    }

    @Override
    public void onDragged(MouseEvent e, GraphicsContext gc) {
        gc.strokeLine(lastX, lastY, e.getX(), e.getY());
        lastY = e.getY();
        lastX = e.getX();
    }
}
