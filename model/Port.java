package model;

import java.awt.*;

public class Port {
    private static final int SIZE = 8; // 圓點大小
    public int x;
    public int y; // 中心點座標

    public Port(int x, int y) {
        this.x = x;
        this.y = y;
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
