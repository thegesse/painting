package com.geese.paint.Buttons.Colors;

import com.geese.paint.Buttons.Button;
import javafx.scene.paint.Color;

public class ColorButton implements Button {
    private String color;

    public String getColor() {
        return color;
    }
    public static void setColor(String color) {}

    @Override
    public String onclick() {
        return getColor();
    }
}
