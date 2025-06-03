package model.Link;

import model.Port;
import model.Shape;

import java.awt.*;

public abstract class Link {
    private Port pfrom, pto;
    private Shape sfrom, sto;
    private LinkType type;

    public Link(Shape sfrom, Shape sto, Port pfrom, Port pto, LinkType type) {
        this.sfrom = sfrom;
        this.sto = sto;
        this.pfrom = pfrom;
        this.pto = pto;
        this.type = type;
    }


    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(pfrom.x, pfrom.y, pto.x, pto.y);

        // 繪製箭頭
        switch (type) {
            case ASSOCIATION:
                drawArrow(g, pto.x, pto.y, pfrom.x, pfrom.y);
                break;
            case GENERALIZATION:
                drawGeneralizationArrow(g, pfrom.x, pfrom.y, pto.x, pto.y);
                break;
            case COMPOSITION:
                drawCompositionArrow(g, pfrom.x, pfrom.y, pto.x, pto.y);
                break;
        }

    }

    public void updatePorts() {
        if (sfrom != null) {
            if (sfrom instanceof model.Composite) {
                pfrom = sfrom.getClosestPort(pfrom.x, pfrom.y);
            } else {
                pfrom = sfrom.getClosestPort(pfrom.x, pfrom.y);
            }
        }

        if (sto != null) {
            if (sto instanceof model.Composite) {
                pto = sto.getClosestPort(pto.x, pto.y);
            } else {
                pto = sto.getClosestPort(pto.x, pto.y);
            }
        }
    }


    public model.Shape getFromShape() {
        return sfrom;
    }

    public model.Shape getToShape() {
        return sto;
    }

    public Port getFromPort() {
        return pfrom;
    }

    public Port getToPort() {
        return pto;
    }

    public void setFrom(model.Shape sfrom, Port pfrom) {
        this.sfrom = sfrom;
        this.pfrom = pfrom;
    }

    public void setTo(Shape sto, Port pto) {
        this.sto = sto;
        this.pto = pto;
    }

    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int arrowSize = 10;
        double angle = Math.atan2(y1 - y2, x1 - x2);
        int x3 = (int) (x1 - arrowSize * Math.cos(angle + Math.PI / 6));
        int y3 = (int) (y1 - arrowSize * Math.sin(angle + Math.PI / 6));
        int x4 = (int) (x1 - arrowSize * Math.cos(angle - Math.PI / 6));
        int y4 = (int) (y1 - arrowSize * Math.sin(angle - Math.PI / 6));
        g.drawLine(x1, y1, x3, y3);
        g.drawLine(x1, y1, x4, y4);
    }

    private void drawGeneralizationArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int arrowSize = 15;
        double angle = Math.atan2(y2 - y1, x2 - x1);

        int xTip = x2;
        int yTip = y2;

        int xLeft = (int) (xTip - arrowSize * Math.cos(angle - Math.PI / 6));
        int yLeft = (int) (yTip - arrowSize * Math.sin(angle - Math.PI / 6));

        int xRight = (int) (xTip - arrowSize * Math.cos(angle + Math.PI / 6));
        int yRight = (int) (yTip - arrowSize * Math.sin(angle + Math.PI / 6));

        Polygon triangle = new Polygon();
        triangle.addPoint(xTip, yTip);
        triangle.addPoint(xLeft, yLeft);
        triangle.addPoint(xRight, yRight);
        g.drawLine(x1, y1, xTip, yTip); // 主幹線
        g.setColor(Color.WHITE);
        g.fillPolygon(triangle);
        g.setColor(Color.BLACK);
        g.drawPolygon(triangle); // 空心三角形

    }


    private void drawCompositionArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int diamondSize = 10;
        double angle = Math.atan2(y2 - y1, x2 - x1);

        int xTip = x2;
        int yTip = y2;

        int xLeft = (int) (xTip - diamondSize * Math.cos(angle - Math.PI / 6));
        int yLeft = (int) (yTip - diamondSize * Math.sin(angle - Math.PI / 6));
        int xRight = (int) (xTip - diamondSize * Math.cos(angle + Math.PI / 6));
        int yRight = (int) (yTip - diamondSize * Math.sin(angle + Math.PI / 6));
        int xBack = (int) (xTip - 2 * diamondSize * Math.cos(angle));
        int yBack = (int) (yTip - 2 * diamondSize * Math.sin(angle));

        Polygon diamond = new Polygon();
        diamond.addPoint(xTip, yTip);
        diamond.addPoint(xLeft, yLeft);
        diamond.addPoint(xBack, yBack);
        diamond.addPoint(xRight, yRight);
        g.setColor(Color.WHITE);
        g.fillPolygon(diamond); // 實心菱形
        g.setColor(Color.BLACK);
        g.drawPolygon(diamond);
        g.drawLine(x1, y1, xBack, yBack); // 主線從 x1,y1 到菱形尾巴
    }

}
