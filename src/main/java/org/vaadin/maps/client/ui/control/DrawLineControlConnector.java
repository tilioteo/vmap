package org.vaadin.maps.client.ui.control;

import com.vaadin.shared.ui.Connect;
import org.vaadin.maps.client.ui.VDrawLineControl;

/**
 * @author Kamil Morong
 */
@Connect(org.vaadin.maps.ui.control.DrawLineControl.class)
public class DrawLineControlConnector extends DrawPathControlConnector {

    @Override
    public VDrawLineControl getWidget() {
        return (VDrawLineControl) super.getWidget();
    }

}
