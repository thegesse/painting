package com.geese.paint.Inputs;

import javafx.scene.input.MouseEvent;


public class MousePos {
    private double x;
    private double y;

    public void updatePos(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }

    public String getFormattedPos() {
        return String.format("x: %.2f, y: %.2f", x, y);
    }

    public double getY() {
        return y;
    }
    public double getX() {
        return x;
    }
}
