package handler;

import model.Shape.Rect;
import view.CanvasPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class RectModeHandler implements MouseModeHandler {
    private final CanvasPanel canvas;

    public RectModeHandler(CanvasPanel canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int depth = canvas.getDepthCounter();
        canvas.addShape(new Rect(e.getX(), e.getY(), 100, 60, depth++));
        canvas.setDepthCounter(depth);
        canvas.repaintCanvas();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void drawSelectionBox(Graphics g) {

    }
}