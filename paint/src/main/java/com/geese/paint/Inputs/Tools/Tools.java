package com.geese.paint.Inputs.Tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public interface Tools {
    void onPressed(MouseEvent e, GraphicsContext gc);
    void onDragged(MouseEvent e, GraphicsContext gc);
}
