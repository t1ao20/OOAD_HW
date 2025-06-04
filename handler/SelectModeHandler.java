package handler;

import model.Link.Link;
import model.Shape.Shape;
import view.CanvasPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class SelectModeHandler implements MouseModeHandler {
    private final CanvasPanel canvas;

    private Map<Shape, Point> dragOffsets = new HashMap<>();
    private List<Shape> draggingShapes = new ArrayList<>();
    private Point dragStartPoint = null;
    private Point dragEndPoint = null;
    private boolean isSelecting = false;

    public SelectModeHandler(CanvasPanel canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        List<Shape> shapes = canvas.getShapes();
        boolean clickedAny = false;
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape s = shapes.get(i);
            if (s.contains(e.getX(), e.getY())) {
                for (Shape shape : shapes) shape.setSelected(false);
                s.setSelected(true);
                clickedAny = true;
                break;
            }
        }
        if (!clickedAny) {
            for (Shape s : shapes) s.setSelected(false);
        }

        draggingShapes.clear();
        dragOffsets.clear();
        for (Shape s : canvas.getSelectedShapes()) {
            if (s.contains(e.getX(), e.getY())) {
                draggingShapes.add(s);
                dragOffsets.put(s, new Point(e.getX() - s.getX(), e.getY() - s.getY()));
            }
        }

        Shape clickedShape = canvas.getShapeAt(e.getPoint());
        if (clickedShape == null) {
            isSelecting = true;
            dragStartPoint = e.getPoint();
            dragEndPoint = e.getPoint();
        }

        canvas.repaintCanvas();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isSelecting) {
            Rectangle selectionRect = createSelectionRect(dragStartPoint, dragEndPoint);
            for (Shape s : canvas.getShapes()) {
                s.setSelected(selectionRect.contains(s.getBounds()));
            }
            isSelecting = false;
            dragStartPoint = dragEndPoint = null;
            canvas.repaintCanvas();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!draggingShapes.isEmpty()) {
            for (Shape shape : draggingShapes) {
                Point offset = dragOffsets.get(shape);
                shape.move(e.getX() - offset.x, e.getY() - offset.y);
            }
            for (Link link : canvas.getLinks()) {
                link.updatePorts();
            }
            canvas.repaintCanvas();
        }

        if (isSelecting) {
            dragEndPoint = e.getPoint();
            canvas.repaintCanvas();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        boolean overSelected = canvas.getShapes().stream()
                .anyMatch(s -> s.contains(e.getX(), e.getY()) && s.isSelected());

        canvas.setCursor(overSelected ?
                Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR) :
                Cursor.getDefaultCursor());
    }

    @Override
    public void drawSelectionBox(Graphics g) {
        if (isSelecting && dragStartPoint != null && dragEndPoint != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
            Rectangle r = createSelectionRect(dragStartPoint, dragEndPoint);
            g2.drawRect(r.x, r.y, r.width, r.height);
        }
    }

    private Rectangle createSelectionRect(Point p1, Point p2) {
        int x = Math.min(p1.x, p2.x);
        int y = Math.min(p1.y, p2.y);
        int width = Math.abs(p1.x - p2.x);
        int height = Math.abs(p1.y - p2.y);
        return new Rectangle(x, y, width, height);
    }
}
