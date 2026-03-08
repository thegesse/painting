package com.geese.paint.Inputs.Tools.Shapes;

import com.geese.paint.Inputs.MouseAction;
import com.geese.paint.Inputs.Tools.Tools;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CircleTool implements Tools {
    private double startX, startY;
    private Color color;
    
    public CircleTool(Color color) {
        this.color = color;
    }
    
    @Override
    public void onPressed(MouseEvent e, GraphicsContext gc) {
        startX = e.getX();
        startY = e.getY();
        gc.setStroke(color);
    }
    
    @Override
    public void onDragged(MouseEvent e, GraphicsContext gc) {
        double x = Math.min(startX, e.getX());
        double y = Math.min(startY, e.getY());
        double w = Math.abs(startX - e.getX());
        double h = Math.abs(startY - e.getY());

        gc.strokeOval(x, y, w, h);
    }
}
