package com.geese.paint.Inputs.Tools.Shapes;

import com.geese.paint.Inputs.Tools.Tools;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;



public class RectangleTool implements Tools {
    private double startX, startY;
    private Color color;
    private double size;

    public RectangleTool(Color color, double size) {
        this.color = color;
        this.size = size;
    }

    @Override
    public void onPressed(MouseEvent e, GraphicsContext gc) {
        startX = e.getX();
        startY = e.getY();
        gc.setLineWidth(size);
        gc.setStroke(color);
    }

    @Override
    public void onDragged(MouseEvent e, GraphicsContext gc) {
        double x = Math.min(startX, e.getX());
        double y = Math.min(startY, e.getY());
        double w = Math.abs(startX - e.getX());
        double h = Math.abs(startY - e.getY());
        gc.setLineWidth(size);
        gc.strokeRect(x, y, w, h);
    }
}
