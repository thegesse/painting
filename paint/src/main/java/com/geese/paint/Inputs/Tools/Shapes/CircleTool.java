package com.geese.paint.Inputs.Tools.Shapes;

import com.geese.paint.Inputs.MouseAction;
import com.geese.paint.Inputs.Tools.Tools;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CircleTool implements Tools {
    private double startX, startY;
    private Color color;
    private double size;
    
    public CircleTool(Color color, double size) {
        this.color = color;
        this.size = size;
    }
    
    @Override
    public void onPressed(MouseEvent e, GraphicsContext gc) {
        startX = e.getX();
        startY = e.getY();
        gc.setStroke(color);
        gc.setLineWidth(size);
    }
    
    @Override
    public void onDragged(MouseEvent e, GraphicsContext gc) {
        double x = Math.min(startX, e.getX());
        double y = Math.min(startY, e.getY());
        double w = Math.abs(startX - e.getX());
        double h = Math.abs(startY - e.getY());

        gc.setLineWidth(size);
        gc.strokeOval(x, y, w, h);
    }
}
