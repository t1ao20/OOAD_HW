package utils;

import model.Link.LinkType;

public enum Mode {
    SELECT,
    RECT,
    OVAL,
    ASSOCIATION,
    GENERALIZATION,
    COMPOSITION;

    public boolean isLinkMode() {
        return this == ASSOCIATION || this == GENERALIZATION || this == COMPOSITION;
    }

    public LinkType toLinkType() {
        switch (this) {
            case ASSOCIATION: return LinkType.ASSOCIATION;
            case GENERALIZATION: return LinkType.GENERALIZATION;
            case COMPOSITION: return LinkType.COMPOSITION;
            default: return null;
        }
    }
}
