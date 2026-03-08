package com.geese.paint.Inputs.Tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class EraserTool implements Tools{
    private double lastX, lastY;
    private double size;

    public EraserTool(double size) {
        this.size = size;
    }

    @Override
    public void onPressed(MouseEvent e, GraphicsContext gc) {
        lastX  = e.getX();
        lastY = e.getY();

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(size);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.strokeLine(lastX, lastY, lastX, lastY);
    }

    @Override
    public void onDragged(MouseEvent e, GraphicsContext gc) {
        gc.strokeLine(lastX, lastY, e.getX(), e.getY());
        lastX = e.getX();
        lastY = e.getY();
    }
}
