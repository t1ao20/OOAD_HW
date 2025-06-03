package model.Link;

import model.Port;
import model.Shape;

import java.awt.*;

public class CompositionLink extends Link {

    public CompositionLink(Shape sfrom, Shape sto, Port pfrom, Port pto) {
        super(sfrom, sto, pfrom, pto);
    }

    @Override
    public void draw(Graphics g) {
        int diamondSize = 10;

        Port pfrom = this.getFromPort();
        Port pto = this.getToPort();

        int x1 = pfrom.getX();
        int y1 = pfrom.getY();

        int x2 = pto.getX();
        int y2 = pto.getY();

        double angle = Math.atan2(y2 - y1, x2 - x1);

        int xTip = x2;
        int yTip = y2;

        int xLeft = (int) (xTip - diamondSize * Math.cos(angle - Math.PI / 6));
        int yLeft = (int) (yTip - diamondSize * Math.sin(angle - Math.PI / 6));
        int xRight = (int) (xTip - diamondSize * Math.cos(angle + Math.PI / 6));
        int yRight = (int) (yTip - diamondSize * Math.sin(angle + Math.PI / 6));
        int xBack = (int) (xTip - 2 * diamondSize * Math.cos(angle));
        int yBack = (int) (yTip - 2 * diamondSize * Math.sin(angle));

        // drawLine
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);

        //drawArrow

        Polygon diamond = new Polygon();
        diamond.addPoint(xTip, yTip);
        diamond.addPoint(xLeft, yLeft);
        diamond.addPoint(xBack, yBack);
        diamond.addPoint(xRight, yRight);

        g.setColor(Color.WHITE);
        g.fillPolygon(diamond);
        g.setColor(Color.BLACK);
        g.drawPolygon(diamond);
        g.drawLine(x1, y1, xBack, yBack);
    }
}
