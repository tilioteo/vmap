package org.vaadin.maps.client.ui.control;

import com.vaadin.shared.ui.Connect;
import org.vaadin.maps.client.ui.VPanControl;
import org.vaadin.maps.shared.ui.control.PanControlState;

/**
 * @author Kamil Morong
 */
@Connect(org.vaadin.maps.ui.control.PanControl.class)
public class PanControlConnector extends NavigateControlConnector {

    @Override
    public VPanControl getWidget() {
        return (VPanControl) super.getWidget();
    }

    @Override
    public PanControlState getState() {
        return (PanControlState) super.getState();
    }

}
