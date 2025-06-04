package model.Shape;

import java.awt.*;

public class Rect extends Shape {



    public Rect(int x, int y, int width, int height, int depth) {
        super(x, y, width, height, depth);
    }

    @Override
    public void initPorts() {
        int x = this.getX();
        int y = this.getY();
        int width = this.getWidth();
        int height = this.getHeight();
        ports.clear();
        ports.add(new Port(this.getX(), this.getY())); // top-left
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
        g.setColor(this.isSelected() ? Color.RED : Color.BLACK);
        g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
//        drawTagBG(g);
//        drawLabel(g);
        this.getLabel().draw(g, this.getX(), this.getY(), this.getWidth(), this.getHeight());

        if (this.isSelected()){
            drawPorts(g);
        }
    }

    @Override
    public boolean contains(int px, int py) {
        return px >= this.getX()
                && px <= this.getX() + this.getWidth()
                && py >= this.getY()
                && py <= this.getY() + this.getHeight();
    }

}
