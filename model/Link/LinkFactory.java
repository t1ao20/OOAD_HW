package model.Link;

import model.Shape.Port;
import model.Shape.Shape;

public class LinkFactory {
    public static Link createLink(LinkType type, Shape sfrom, Shape sto, Port pfrom, Port pto) {
        switch (type) {
            case ASSOCIATION:
                return new AssociationLink(sfrom, sto, pfrom, pto);
            case GENERALIZATION:
                return new GeneralizationLink(sfrom, sto, pfrom, pto);
            case COMPOSITION:
                return new CompositionLink(sfrom, sto, pfrom, pto);
            default:
                throw new IllegalArgumentException("Unknown LinkType");
        }
    }
}
