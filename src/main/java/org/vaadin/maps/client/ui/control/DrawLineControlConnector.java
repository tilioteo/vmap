/**
 * 
 */
package org.vaadin.maps.client.ui.control;

import org.vaadin.maps.client.ui.VDrawLineControl;

import com.vaadin.shared.ui.Connect;

/**
 * @author Kamil Morong
 *
 */
@SuppressWarnings("serial")
@Connect(org.vaadin.maps.ui.control.DrawLineControl.class)
public class DrawLineControlConnector extends DrawPathControlConnector {

	@Override
	public VDrawLineControl getWidget() {
		return (VDrawLineControl) super.getWidget();
	}

}
