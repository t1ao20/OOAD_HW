package model;

import java.awt.*;

public class Oval extends Shape {
    public Oval(int x, int y, int width, int height, int depth) {
        super(x, y, width, height, depth);
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(selected ? Color.RED : Color.BLACK);
        g.drawOval(x, y, width, height);
        drawTagBG(g);
        drawPorts(g);
        drawLabel(g);
    }

    @Override
    public boolean contains(int px, int py) {
        double dx = px - (x + width / 2.0);
        double dy = py - (y + height / 2.0);
        return (dx*dx)/(width*width/4.0) + (dy*dy)/(height*height/4.0) <= 1.0;
    }
}
