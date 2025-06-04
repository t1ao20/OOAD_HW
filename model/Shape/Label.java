package model.Shape;

import java.awt.*;

public class Label {
    private String text = "";

    private Font font = new Font("Arial", Font.PLAIN, 14);
    private Color textColor = Color.BLACK;

    private ShapeType shapeType = ShapeType.DEFAULT;
    private Color backgroundColor = Color.WHITE;

    private static final int padding = 15;


    public String getText() {
        return text;
    }
    public Font getFont() {
        return font;
    }
    public Color getTextColor() {
        return textColor;
    }
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public ShapeType getShapeType() { return shapeType; }
    public void setText(String text) {
        this.text = text;
    }
    public void setFont(Font font) {
        this.font = font;
    }
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public void setShapeType(ShapeType type) { this.shapeType = type; }

    public void draw(Graphics g, int x, int y, int width, int height) {
        if (text == null || text.isEmpty()) return;

        // DRAW BACKGROUND

        g.setColor(backgroundColor);

        int innerX = x + padding;
        int innerY = y + padding;
        int innerW = width - 2 * padding;
        int innerH = height - 2 * padding;

        if (shapeType != ShapeType.DEFAULT) {
            switch (shapeType) {
                case RECT:
                    g.fillRect(innerX, innerY, innerW, innerH);
                    g.setColor(Color.BLACK);
                    g.drawRect(innerX, innerY, innerW, innerH);
                    break;
                case OVAL:
                    g.fillOval(innerX, innerY, innerW, innerH);
                    g.setColor(Color.BLACK);
                    g.drawOval(innerX, innerY, innerW, innerH);
                    break;
            }
        }

        // DRAW TEXT

        g.setFont(font);
        g.setColor(textColor);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int centerX = x + width / 2 - textWidth / 2;
        int centerY = y + height / 2 + textHeight / 2;
        g.drawString(text, centerX, centerY);
    }

}

