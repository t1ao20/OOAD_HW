package model.Shape;

import java.awt.*;

public class Port {
    private static final int SIZE = 8; // 圓點大小
    private int x;
    private int y; // 中心點座標

    public Port(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getCenter() {
        return new Point(x, y);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
    }

    public boolean contains(Point p) {
        return (p.distance(x, y) <= SIZE / 2.0);
    }
}
