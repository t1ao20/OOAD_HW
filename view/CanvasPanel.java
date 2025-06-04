package view;

import handler.CanvasMouseHandler;
import model.Shape.*;
import model.Link.Link;
import model.Shape.Composite;
import model.Shape.Shape;
import utils.Mode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CanvasPanel extends JPanel {
    private final List<Shape> shapes = new ArrayList<>();
    private final List<Link> links = new ArrayList<>();
    private int depthCounter = 0;

    private final CanvasMouseHandler mouseHandler;

    public CanvasPanel() {
        setBackground(Color.WHITE);
        mouseHandler = new CanvasMouseHandler(this);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public List<Link> getLinks() {
        return links;
    }

    public int getDepthCounter() {
        return depthCounter;
    }

    public void setDepthCounter(int depthCounter) {
        this.depthCounter = depthCounter;
    }

    public void addShape(Shape s) {
        shapes.add(s);
        repaint();
    }

    public void addLink(Link l) {
        links.add(l);
        repaint();
    }

    public void repaintCanvas() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape s : shapes) s.draw(g);
        for (Link l : links) l.draw(g);
        mouseHandler.drawSelectionBox(g); // 如果正在框選
    }


    public void setMode(Mode m) {
        mouseHandler.setMode(m);
    }

    public Mode getMode() {
        return mouseHandler.getMode();
    }

    public void groupSelectedShapes() {
        List<Shape> selectedShapes = getSelectedShapes();
        if (selectedShapes.size() <= 1) return;

        // 移除選到的
        shapes.removeAll(selectedShapes);

        // 建立 composite，加入 shape list
        Composite group = new Composite(selectedShapes);
        group.setSelected(true);
        shapes.add(group);

        repaint();
    }
    public void ungroupSelectedShape() {
        Shape selected = getSelectedShapes().get(0);
        if (!(selected instanceof Composite)) return;

        Composite selectedComposite = (Composite) selected;

        // 移除 composite
        shapes.remove(selectedComposite);

        // 加回原本的 children
        for (Shape s : selectedComposite.getChildren()) {
            s.setSelected(true);
            shapes.add(s);
        }
        repaint();
    }

    public List<Shape> getSelectedShapes() {
        return shapes.stream().filter(Shape::isSelected).collect(Collectors.toList());
    }
    public Shape getShapeAt(Point p) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape s = shapes.get(i);
            if (s.contains(p.x, p.y)) return s;
        }
        return null;
    }

    public Port getNearestPort(Point p) {
        for (Shape shape : shapes) {
            if (shape.contains(p.x, p.y)) {
                return getClosestPort(shape, p);
            }
        }
        return null;
    }
    private Port getClosestPort(Shape shape, Point p) {
        if (shape instanceof Rect) {
            Rect rect = (Rect) shape;
            List<Port> ports = rect.getPorts();
            return getClosestPoint(ports, p);
        } else if (shape instanceof Oval) {
            Oval oval = (Oval) shape;
            List<Port> ports = oval.getPorts();
            return getClosestPoint(ports, p);
        }
        return null;
    }
    private Port getClosestPoint(List<Port> points, Point target) {
        Port closest = points.get(0);
        Point closestPoint = new Point(closest.getX(), closest.getY());
        double minDist = target.distance(closestPoint);
        for (Port p : points) {
            double dist = target.distance(new Point(p.getX(), p.getY()));
            if (dist < minDist) {
                minDist = dist;
                closest = p;
            }
        }
        return closest;
    }


}
