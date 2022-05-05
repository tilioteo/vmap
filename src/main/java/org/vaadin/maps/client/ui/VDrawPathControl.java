package org.vaadin.maps.client.ui;

import com.google.gwt.user.client.ui.Widget;
import org.vaadin.maps.shared.ui.Style;

/**
 * @author Kamil Morong
 */
public class VDrawPathControl extends VDrawPointControl {

    public static final String CLASSNAME = "v-drawpathcontrol";

    private Style startPointStyle = null;
    private Style startPointHoverStyle = null;
    private Style lineStyle = null;
    private Style vertexStyle = null;

    public VDrawPathControl() {
        super();
        setStyleName(CLASSNAME);
    }

    @Override
    public void setWidget(Widget widget) {
        if (widget instanceof VPathHandler) {
            super.setWidget(widget);

            getHandler().setStartPointStyle(startPointStyle);
            getHandler().setLineStyle(lineStyle);
            getHandler().setVertexStyle(vertexStyle);
        }
    }

    @Override
    public VPathHandler getHandler() {
        return (VPathHandler) handler;
    }

    public Style getStartPointStyle() {
        return startPointStyle;
    }

    public void setStartPointStyle(Style style) {
        startPointStyle = style;

        if (handler != null) {
            getHandler().setStartPointStyle(style);
        }
    }

    public Style getStartPointHoverStyle() {
        return startPointHoverStyle;
    }

    public void setStartPointHoverStyle(Style style) {
        startPointHoverStyle = style;

        if (handler != null) {
            getHandler().setStartPointHoverStyle(style);
        }
    }

    public Style getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(Style style) {
        lineStyle = style;

        if (handler != null) {
            getHandler().setLineStyle(style);
        }
    }

    public Style getVertexStyle() {
        return vertexStyle;
    }

    public void setVertexStyle(Style style) {
        vertexStyle = style;

        if (handler != null) {
            getHandler().setVertexStyle(style);
        }
    }

}
