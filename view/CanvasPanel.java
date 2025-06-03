package view;

import model.*;
import model.Link.Link;
import model.Link.LinkFactory;
import model.Shape;
import model.Composite;
import utils.Mode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CanvasPanel extends JPanel implements MouseListener, MouseMotionListener {
    private List<Shape> shapes = new ArrayList<>();
    private List<Link> links = new ArrayList<>();
    private Mode mode = Mode.SELECT;
    private int depthCounter = 0;

    private Port startPort = null;
    private Shape startShape = null;

    private Map<Shape, Point> dragOffsets = new HashMap<>();
    private List<Shape> draggingShapes = new ArrayList<>();

    private Point dragStartPoint = null;
    private Point dragEndPoint = null;
    private boolean isSelecting = false;



    public CanvasPanel() {
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setMode(Mode m) {
        this.mode = m;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        requestFocusInWindow(); // 為了能使用鍵盤事件
        if (mode == Mode.RECT) {
            shapes.add(new Rect(e.getX(), e.getY(), 100, 60, depthCounter++));
            repaint();
        } else if (mode == Mode.OVAL) {
            shapes.add(new Oval(e.getX(), e.getY(), 100, 60, depthCounter++));
            repaint();
        }

        if (mode == Mode.SELECT) {
            // select one object
            boolean clickedAny = false;
            for (int i = shapes.size() - 1; i >= 0; i--) {
                Shape s = shapes.get(i);
                if (s.contains(e.getX(), e.getY())) {
                    for (Shape shape : shapes) {
                        shape.setSelected(false);
                    }
                    s.setSelected(true);
                    clickedAny = true;
                    break;
                }

            }
            if (!clickedAny) {
                for (Shape s : shapes) s.setSelected(false);
            }

            // 拖曳準備: select many objects(drag)
            draggingShapes.clear();
            dragOffsets.clear();
            for (Shape s : getSelectedShapes()) {
                if (s.contains(e.getX(), e.getY())) {
                    draggingShapes.add(s);
                    dragOffsets.put(s, new Point(e.getX() - s.getX(), e.getY() - s.getY()));
                }
            }
            Shape clickedShape = getShapeAt(e.getX(), e.getY());
            if (clickedShape == null) {
                for (Shape s : shapes) s.setSelected(false); // Alternative c.2
                isSelecting = true;
                dragStartPoint = e.getPoint();
                dragEndPoint = e.getPoint(); // 初始化選取區
            }

            repaint();
        }
        else if (mode.isLinkMode()) {
            startPort = getNearestConnectionPort(e.getPoint());
            startShape = getShapeAt(e.getPoint());
        }
    }

    public void mouseReleased(MouseEvent e) {
        //link
        if (startPort != null && mode.isLinkMode()) {
            Port endPort = getNearestConnectionPort(e.getPoint());
            Shape endShape = getShapeAt(e.getPoint());
            if (endPort != null && endShape != startShape) {
                Link link = LinkFactory.createLink(mode.toLinkType(), startShape, endShape, startPort, endPort);
                links.add(link);
                repaint();
            }
        }
        startPort = null;
        startShape = null;
        //drag select
        if (mode == Mode.SELECT && isSelecting) {
            Rectangle selectionRect = createSelectionRect(dragStartPoint, dragEndPoint);
            boolean selectedAny = false;
            for (Shape s : shapes) {
                if (selectionRect.contains(s.getBounds())) {
                    s.setSelected(true);
                    selectedAny = true;
                } else {
                    s.setSelected(false);
                }
            }
            if (!selectedAny) {
                for (Shape s : shapes) s.setSelected(false); // Alternative c.3
            }
            isSelecting = false;
            dragStartPoint = dragEndPoint = null;
            repaint();
        }


    }
//move object
    public void mouseDragged(MouseEvent e) {
        if (mode == Mode.SELECT && !draggingShapes.isEmpty()) {
            // Track which links need to be updated based on dragged shapes
            Set<Link> updatedLinks = new HashSet<>();

            // Update positions of dragged shapes
            for (Shape shape : draggingShapes) {
                Point offset = dragOffsets.get(shape);
                shape.move(e.getX() - offset.x, e.getY() - offset.y);
            }

            // Update links that are affected by the dragging of shapes
            for (Link link : links) {
                if (draggingShapes.contains(link.getFromShape()) || draggingShapes.contains(link.getToShape())) {
                    updatedLinks.add(link);
                } else {
                    for (Shape dragged : draggingShapes) {
                        List<Shape> allChildren = getAllChildrenRecursively(dragged);
                        if (allChildren.contains(link.getFromShape()) || allChildren.contains(link.getToShape())) {
                            updatedLinks.add(link);
                            break;
                        }
                    }
                }
            }


            // Only update the ports of the affected links
            for (Link updatedLink : updatedLinks) {
                updatedLink.updatePorts();
            }

            repaint();
        }
        if (mode == Mode.SELECT && isSelecting) {
            dragEndPoint = e.getPoint();
            repaint(); // 畫出選取框
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape s : shapes) s.draw(g);
        for (Link l : links) l.draw(g);
        if (isSelecting && dragStartPoint != null && dragEndPoint != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
            Rectangle r = createSelectionRect(dragStartPoint, dragEndPoint);
            g2.drawRect(r.x, r.y, r.width, r.height);
        }

    }

    private Shape getShapeAt(int x, int y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            if (shapes.get(i).contains(x, y)) return shapes.get(i);
        }
        return null;
    }

    // Unused mouse events
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {
        boolean overSelected = false;
        for (Shape s : shapes) {
            if (s.contains(e.getX(), e.getY()) && s.isSelected()) {
                overSelected = true;
                break;
            }
        }
        if (overSelected) {
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    public Shape getSelectedShape() {
        for (Shape s : shapes) {
            if (s.isSelected()) return s;
        }
        return null;
    }
    public List<Shape> getSelectedShapes() {
        return shapes.stream().filter(Shape::isSelected).collect(Collectors.toList());
    }
    public void removeShapes(List<Shape> selected) {
        shapes.removeAll(selected);
    }
    public void addShapes(List<Shape> newShapes) {
        shapes.addAll(newShapes);
    }
    public void removeShape(Composite selected) {
        shapes.removeAll(selected.getChildren());
    }
    public void addShape(Composite newShapes) {
        shapes.addAll(newShapes.getChildren());
    }

//    Port
private Port getNearestConnectionPort(Point p) {
    for (Shape shape : shapes) {
        if (shape.contains(p.x, p.y)) {
            return getClosestPort(shape, p);
        }
    }
    return null;
}

    private Shape getShapeAt(Point p) {
        for (Shape shape : shapes) {
            if (shape.contains(p.x, p.y)) {
                return shape;
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

//    Group / UnGroup
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
        Shape selected = getSelectedShape();
        if (!(selected instanceof Composite)) return;

        Composite composite = (Composite) selected;

        // 移除 composite
        shapes.remove(composite);

        // 加回原本的 children
        for (Shape s : composite.getChildren()) {
            s.setSelected(true);
            shapes.add(s);
        }
        repaint();
    }
    public void replaceShape(Shape oldShape, Shape newShape) {
        int index = shapes.indexOf(oldShape);
        if (index != -1) {
            shapes.set(index, newShape);
            for (Link link : links) {
                if (link.getFromShape() == oldShape) {
                    Port oldPort = link.getFromPort();
                    Port closest = newShape.getClosestPort(oldPort.getX(), oldPort.getY());
                    link.setFrom(newShape, closest);
                }
                if (link.getToShape() == oldShape) {
                    Port oldPort = link.getToPort();
                    Port closest = newShape.getClosestPort(oldPort.getX(), oldPort.getY());
                    link.setTo(newShape, closest);
                }
            }
            repaint();
        }
    }
    private List<Shape> getAllChildrenRecursively(Shape shape) {
        List<Shape> result = new ArrayList<>();
        if (shape instanceof Composite) {
            for (Shape child : ((Composite) shape).getChildren()) {
                result.addAll(getAllChildrenRecursively(child));
            }
        } else {
            result.add(shape);
        }
        return result;
    }
    private Rectangle createSelectionRect(Point p1, Point p2) {
        int x = Math.min(p1.x, p2.x);
        int y = Math.min(p1.y, p2.y);
        int width = Math.abs(p1.x - p2.x);
        int height = Math.abs(p1.y - p2.y);
        return new Rectangle(x, y, width, height);
    }
}
