package org.vaadin.maps.client.ui;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Kamil Morong
 */
public class VDrawPointControl extends VDrawFeatureControl {

    public static final String CLASSNAME = "v-drawpointcontrol";

    public VDrawPointControl() {
        super();
        setStyleName(CLASSNAME);
    }

    @Override
    public void setWidget(Widget widget) {
        if (widget instanceof VPointHandler) {
            super.setWidget(widget);
        }
    }

    @Override
    public VPointHandler getHandler() {
        return (VPointHandler) handler;
    }

}
