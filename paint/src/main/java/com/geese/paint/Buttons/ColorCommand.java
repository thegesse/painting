package com.geese.paint.Buttons;


import com.geese.paint.Inputs.MouseAction;
import javafx.scene.paint.Color;

public class ColorCommand implements PaintCommand {
    private final Color color;

    public ColorCommand(String colorName) {
        this.color = Color.valueOf(colorName);
    }

    @Override
    public void execute(MouseAction tool) {
        tool.setCurrentColor(color);
        System.out.println("tool updated color to " + color);
    }
}
