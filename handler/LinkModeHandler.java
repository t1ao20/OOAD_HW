package handler;

import model.Link.Link;
import model.Link.LinkFactory;
import model.Shape.Port;
import model.Shape.Shape;
import view.CanvasPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class LinkModeHandler implements MouseModeHandler {
    private final CanvasPanel canvas;
    private Shape fromShape = null;
    private Port fromPort = null;
    private Point tempEndPoint = null;

    public LinkModeHandler(CanvasPanel canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        fromShape = canvas.getShapeAt(e.getPoint());
        if (fromShape != null) {
            fromPort = canvas.getNearestPort(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (fromShape != null && fromPort != null) {
            Shape toShape = canvas.getShapeAt(e.getPoint());
            if (toShape != null && toShape != fromShape) {
                Port toPort = canvas.getNearestPort(e.getPoint());
                if (toPort != null){
//                    System.out.println(toPort.getX());
//                    System.out.println(toPort.getY());
                    Link link = LinkFactory.createLink(Objects.requireNonNull(canvas.getMode().toLinkType()), fromShape, toShape, fromPort, toPort);
                    canvas.addLink(link);
                }
            }
        }
        fromShape = null;
        fromPort = null;
        tempEndPoint = null;
        canvas.repaintCanvas();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void drawSelectionBox(Graphics g) {

    }

    public Point getTempEndPoint() {
        return tempEndPoint;
    }

    public Shape getFromShape() {
        return fromShape;
    }

    public Port getFromPort() {
        return fromPort;
    }
}
