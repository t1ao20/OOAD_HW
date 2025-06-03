package model.Link;

import model.Port;
import model.Shape;

import java.awt.*;

public class AssociationLink extends Link {

    public AssociationLink(Shape sfrom, Shape sto, Port pfrom, Port pto) {
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

        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);

        int arrowSize = 10;


        double angle = Math.atan2(y2 - y1, x2 - x1);
        int x3 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int y3 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));
        int x4 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int y4 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
        g.drawLine(x2, y2, x3, y3);
        g.drawLine(x2, y2, x4, y4);
    }
}
