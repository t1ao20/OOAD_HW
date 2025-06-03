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
            minX = Math.min(minX, s.getX());
            minY = Math.min(minY, s.getY());
            maxX = Math.max(maxX, s.getX() + s.getWidth());
            maxY = Math.max(maxY, s.getY() + s.getHeight());
        }

        this.setX(minX);
        this.setY(minY);
        this.setWidth(maxX - minX);
        this.setHeight(maxY - minY);
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
            //子圖案select狀態設定為composite物件的狀態
            s.setSelected(this.isSelected());
            s.draw(g);
        }
        if (this.isSelected()) {
            g.setColor(Color.BLUE);
            g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
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
        int dx = newX - this.getX();
        int dy = newY - this.getY();

        this.setX(newX);
        this.setY(newY);

        for (Shape s : children) {
            s.move(s.getX() + dx, s.getY() + dy);
        }

        initPorts();
    }

    public List<Shape> getChildren() {
        return children;
    }
}
