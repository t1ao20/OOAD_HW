package model.Link;

import model.Port;
import model.Shape;

import java.awt.*;

public class GeneralizationLink extends Link {

    public GeneralizationLink(Shape sfrom, Shape sto, Port pfrom, Port pto) {
        super(sfrom, sto, pfrom, pto);
    }

    @Override
    public void draw(Graphics g) {

        Port pfrom = this.getFromPort();
        Port pto = this.getToPort();

        int x1 = pfrom.getX();
        int y1 = pfrom.getY();

        int x2 = pto.getX();
        int y2 = pto.getY();

        g.drawLine(x1, y1, x2, y2);

        int arrowSize = 15;
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int xTip = x2;
        int yTip = y2;

        int xLeft = (int) (xTip - arrowSize * Math.cos(angle - Math.PI / 6));
        int yLeft = (int) (yTip - arrowSize * Math.sin(angle - Math.PI / 6));
        int xRight = (int) (xTip - arrowSize * Math.cos(angle + Math.PI / 6));
        int yRight = (int) (yTip - arrowSize * Math.sin(angle + Math.PI / 6));

        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);

        Polygon triangle = new Polygon();
        triangle.addPoint(xTip, yTip);
        triangle.addPoint(xLeft, yLeft);
        triangle.addPoint(xRight, yRight);

        g.setColor(Color.WHITE);
        g.fillPolygon(triangle);
        g.setColor(Color.BLACK);
        g.drawPolygon(triangle);
    }
}
