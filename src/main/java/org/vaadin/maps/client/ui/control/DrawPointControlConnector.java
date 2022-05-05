package org.vaadin.maps.client.ui.control;

import com.vaadin.shared.ui.Connect;
import org.vaadin.maps.client.ui.VDrawPointControl;
import org.vaadin.maps.shared.ui.control.DrawPointControlState;

/**
 * @author Kamil Morong
 */
@Connect(org.vaadin.maps.ui.control.DrawPointControl.class)
public class DrawPointControlConnector extends DrawFeatureControlConnector {

    @Override
    public VDrawPointControl getWidget() {
        return (VDrawPointControl) super.getWidget();
    }

    @Override
    public DrawPointControlState getState() {
        return (DrawPointControlState) super.getState();
    }

}
