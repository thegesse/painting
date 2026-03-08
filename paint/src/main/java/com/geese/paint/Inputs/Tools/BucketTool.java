package com.geese.paint.Inputs.Tools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class BucketTool implements Tools{
    private Color fillContext;

    public BucketTool(Color color) {
        this.fillContext = color;
    }

    @Override
    public void onPressed(MouseEvent e, GraphicsContext gc) {
        Canvas canvas = gc.getCanvas();
        WritableImage snapshot = canvas.snapshot(null, null);
        PixelReader reader = snapshot.getPixelReader();
        PixelWriter writer = gc.getPixelWriter();

        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        int startX = (int) e.getX();
        int startY = (int) e.getY();

        if(startX < 0 || startX >= width || startY < 0 ||startY >=height) return;
        Color targetColor = reader.getColor(startX, startY);

        if(targetColor.equals(fillContext)) return;
        floodFill(reader, writer, startX, startY, width, height, targetColor, fillContext);
    }

    private void floodFill(PixelReader reader, PixelWriter writer, int x, int y,
                           int width, int height, Color target, Color replacement) {

        int[] stack = new int[width * height * 2];
        int top = 0;

        stack[top++] = x;
        stack[top++] = y;

        boolean[] visited = new boolean[width * height];

        while (top > 0) {
            int py = stack[--top];
            int px = stack[--top];

            if (px < 0 || px >= width || py < 0 || py >= height) continue;

            int idx = py * width + px;
            if (visited[idx]) continue;

            if (reader.getColor(px, py).equals(target)) {
                writer.setColor(px, py, replacement);
                visited[idx] = true;
                if (top + 8 < stack.length) {
                    stack[top++] = px + 1; stack[top++] = py;
                    stack[top++] = px - 1; stack[top++] = py;
                    stack[top++] = px;     stack[top++] = py + 1;
                    stack[top++] = px;     stack[top++] = py - 1;
                }
            }
        }
    }

    @Override
    public void onDragged(MouseEvent e, GraphicsContext gc) {

    }
}
