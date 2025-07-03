package handler;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface MouseModeHandler {
    void mousePressed(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mouseDragged(MouseEvent e);
    void mouseMoved(MouseEvent e);
    void drawSelectionBox(Graphics g);
}
