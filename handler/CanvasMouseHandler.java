package handler;

import utils.Mode;
import view.CanvasPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CanvasMouseHandler implements MouseListener, MouseMotionListener {
    private final CanvasPanel canvas;
    private final Map<Mode, MouseModeHandler> handlers = new HashMap<>();
    private Mode mode;

    public CanvasMouseHandler(CanvasPanel canvas) {
        this.canvas = canvas;
        this.mode = Mode.SELECT;

        LinkModeHandler linkModeHandler = new LinkModeHandler(canvas);


        handlers.put(Mode.SELECT, new SelectModeHandler(canvas));
        handlers.put(Mode.RECT, new RectModeHandler(canvas));
        handlers.put(Mode.OVAL, new OvalModeHandler(canvas));
        handlers.put(Mode.ASSOCIATION, linkModeHandler);
        handlers.put(Mode.GENERALIZATION, linkModeHandler);
        handlers.put(Mode.COMPOSITION, linkModeHandler);

        // 更多模式可繼續加
    }

    public void setMode(Mode m) {
        this.mode = m;
    }

    public Mode getMode() {
        return mode;
    }

    private MouseModeHandler getCurrentHandler() {
        return handlers.getOrDefault(mode, handlers.get(Mode.SELECT));
    }

    @Override public void mousePressed(MouseEvent e) { getCurrentHandler().mousePressed(e); }
    @Override public void mouseReleased(MouseEvent e) { getCurrentHandler().mouseReleased(e); }
    @Override public void mouseDragged(MouseEvent e) { getCurrentHandler().mouseDragged(e); }
    @Override public void mouseMoved(MouseEvent e) { getCurrentHandler().mouseMoved(e); }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public void drawSelectionBox(Graphics g) {
        getCurrentHandler().drawSelectionBox(g);
    }
}
