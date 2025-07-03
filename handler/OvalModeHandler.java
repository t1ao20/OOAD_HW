package handler;
import model.Shape.Oval;
import view.CanvasPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class OvalModeHandler implements MouseModeHandler {
    private final CanvasPanel canvas;

    public OvalModeHandler(CanvasPanel canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int depth = canvas.getDepthCounter();
        canvas.addShape(new Oval(e.getX(), e.getY(), 100, 60, depth++));
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
