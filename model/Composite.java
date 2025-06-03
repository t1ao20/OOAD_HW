package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Composite extends Shape {
    private List<Shape> children = new ArrayList<>();

    public Composite(List<Shape> shapes) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (Shape s : shapes) {
            children.add(s);
            minX = Math.min(minX, s.x);
            minY = Math.min(minY, s.y);
            maxX = Math.max(maxX, s.x + s.width);
            maxY = Math.max(maxY, s.y + s.height);
        }

        this.x = minX;
        this.y = minY;
        this.width = maxX - minX;
        this.height = maxY - minY;
    }

    @Override
    public void initPorts() {
        for (Shape s : children) {
            s.initPorts();
        }
    }

    @Override
    public void draw(Graphics g) {
        for (Shape s : children) {
            s.selected = selected;
            s.draw(g);
        }
        if (selected) {
            g.setColor(Color.BLUE);
            g.drawRect(x, y, width, height);
        }
    }

    @Override
    public boolean contains(int mx, int my) {
        for (Shape s : children) {
            if (s.contains(mx, my)) return true;
        }
        return false;
    }

    @Override
    public List<Port> getPorts() {
        List<Port> connectionPorts = new ArrayList<>();
        for (Shape shape : children) {
            connectionPorts.addAll(shape.getPorts());
        }
        return connectionPorts;
    }

    @Override
    public void move(int newX, int newY) {
        int dx = newX - this.x;
        int dy = newY - this.y;

        this.x = newX;
        this.y = newY;

        for (Shape s : children) {
            s.move(s.x + dx, s.y + dy);
        }

        initPorts();
    }

    public List<Shape> getChildren() {
        return children;
    }
}
