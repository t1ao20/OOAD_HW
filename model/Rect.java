package model;

import java.awt.*;

public class Rect extends Shape {

    public Rect(int x, int y, int width, int height, int depth) {
        super(x, y, width, height, depth);
    }

    @Override
    public void initPorts() {
        ports.clear();
        ports.add(new Port(x, y)); // top-left
        ports.add(new Port(x + width, y)); // top-right
        ports.add(new Port(x, y + height)); // bottom-left
        ports.add(new Port(x + width, y + height)); // bottom-right
        ports.add(new Port(x + width / 2, y)); // top
        ports.add(new Port(x + width / 2, y + height)); // bottom
        ports.add(new Port(x, y + height / 2)); // left
        ports.add(new Port(x + width, y + height / 2)); // right
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(selected ? Color.RED : Color.BLACK);
        g.drawRect(x, y, width, height);
        drawTagBG(g);
        drawPorts(g);
        drawLabel(g);
    }

    @Override
    public boolean contains(int px, int py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

}
