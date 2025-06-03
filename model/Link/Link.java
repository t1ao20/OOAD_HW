package model.Link;

import model.Port;
import model.Shape;

import java.awt.*;

public abstract class Link {
    private Port pfrom, pto;
    private Shape sfrom, sto;
//    private LinkType type;

    public Link(Shape sfrom, Shape sto, Port pfrom, Port pto) {
        this.sfrom = sfrom;
        this.sto = sto;
        this.pfrom = pfrom;
        this.pto = pto;
//        this.type = type;
    }
    public Shape getFromShape() {
        return sfrom;
    }

    public Shape getToShape() {
        return sto;
    }

    public Port getFromPort() {
        return pfrom;
    }

    public Port getToPort() {
        return pto;
    }

    public void setFrom(Shape sfrom, Port pfrom) {
        this.sfrom = sfrom;
        this.pfrom = pfrom;
    }

    public void setTo(Shape sto, Port pto) {
        this.sto = sto;
        this.pto = pto;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(pfrom.getX(), pfrom.getY(), pto.getX(), pto.getY());
    }

    public void updatePorts() {
        if (sfrom != null) {
            pfrom = sfrom.getClosestPort(pfrom.getX(), pfrom.getY());

        }

        if (sto != null) {
            pto = sto.getClosestPort(pto.getX(), pto.getY());
        }
    }
}
