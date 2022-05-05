package org.vaadin.maps.client.ui;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Kamil Morong
 */
public class VDrawLineControl extends VDrawPathControl {

    public static final String CLASSNAME = "v-drawlinecontrol";

    public VDrawLineControl() {
        super();
        setStyleName(CLASSNAME);
    }

    @Override
    public void setWidget(Widget widget) {
        if (widget instanceof VLineHandler) {
            super.setWidget(widget);
        }
    }

    @Override
    public VLineHandler getHandler() {
        return (VLineHandler) handler;
    }

}
