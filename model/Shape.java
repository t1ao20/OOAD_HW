package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Shape {
    private int x;
    private int y;
    private int width;
    private int height;
    private int depth;
    private boolean selected;
    private String shapeType = "Shape";
    private Color color_bg = Color.WHITE;

    protected final List<Port> ports = new ArrayList<>();

    private String label = "";
    private Font labelFont = new Font("Arial", Font.PLAIN, 14);
    private Color labelColor = Color.BLACK;

    protected Shape(){}
    public Shape(int x, int y, int width, int height, int depth) {
        this.x = x; this.y = y;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.selected = false;
        initPorts();
    }


    public void initPorts(){
        ports.clear();
        ports.add(new Port(x + width / 2, y)); // top
        ports.add(new Port(x + width / 2, y + height)); // bottom
        ports.add(new Port(x, y + height / 2)); // left
        ports.add(new Port(x + width, y + height / 2)); // right
    }
//  Draw
    public abstract void draw(Graphics g);

    protected void drawPorts(Graphics g) {
        if (selected) {
            g.setColor(Color.RED);
            for (Port port : ports) {
                g.fillRect(port.x - 5, port.y - 5, 10, 10);
            }
        }
    }

    protected void drawTagBG(Graphics g) {
        if(getShapeType() != "Shape"){
            g.setColor(color_bg);

            int padding = 15;
            int innerX = x + padding;
            int innerY = y + padding;
            int innerW = width - 2 * padding;
            int innerH = height - 2 * padding;

            if (getShapeType() == "Rect") {
                g.fillRect(innerX, innerY, innerW, innerH);
                g.setColor(Color.BLACK);
                g.drawRect(innerX, innerY, innerW, innerH);
            } else if(getShapeType() == "Oval"){
                g.fillOval(innerX, innerY, innerW, innerH);
                g.setColor(Color.BLACK);
                g.drawOval(innerX, innerY, innerW, innerH);
            }
        }
    }

    protected void drawLabel(Graphics g) {
        if (!label.isEmpty()) {
            g.setFont(labelFont);
            g.setColor(labelColor);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(label);
            int textHeight = fm.getAscent();
            int centerX = x + width / 2 - textWidth / 2;
            int centerY = y + height / 2 + textHeight / 2;
            g.drawString(label, centerX, centerY);
        }
    }
    public abstract boolean contains(int px, int py);
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getWidth() {
        return width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getHeight() {
        return height;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }
    public int getDepth() {
        return depth;
    }
    public void setColor_bg(Color color_bg) {
        this.color_bg = color_bg;
    }
    public Color getColor_bg() {
        return color_bg;
    }
    public void setLabelShape(String shapeType) {
        this.shapeType = shapeType;
    }
    public String getShapeType() { return shapeType; }
    public void setShapeType(String shape) { this.shapeType = shape; }
    public Color getBgColor() { return color_bg; }
    public void setBgColor(Color color) { this.color_bg = color; }
    public void setSelected(boolean s) { this.selected = s; }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    public boolean isSelected() { return selected; }
    public void move(int dx, int dy) {
        this.x = dx;
        this.y = dy;
        initPorts();
    }

    public Point getCenter() {
        return new Point(x + width / 2, y + height / 2);
    }


//    Label

    public void setLabel(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
    public void setLabelFont(Font font) {
        this.labelFont = font;
    }
    public Font getLabelFont() {
        return labelFont;
    }
    public void setLabelColor(Color color) {
        this.labelColor = color;
    }

    public List<Port> getPorts() {
        return ports;
    }


    public Port getClosestPort(int mouseX, int mouseY) {
        List<Port> ports = getPorts();
        Port closestPort = null;
        double minDistance = Double.MAX_VALUE;

        for (Port port : ports) {
            Point p = new Point(port.x, port.y);
            double distance = p.distance(mouseX, mouseY);
            if (distance < minDistance) {
                minDistance = distance;
                closestPort = port;
            }
        }
        return closestPort;
    }
}
