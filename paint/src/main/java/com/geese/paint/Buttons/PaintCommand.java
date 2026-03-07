package com.geese.paint.Buttons;


import com.geese.paint.Inputs.MouseAction;

public interface PaintCommand {
    void execute(MouseAction tool);
}
