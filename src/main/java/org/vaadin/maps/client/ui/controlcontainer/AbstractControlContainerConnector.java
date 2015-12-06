/**
 * 
 */
package org.vaadin.maps.client.ui.controlcontainer;

import org.vaadin.maps.shared.ui.controlcontainer.AbstractControlContainerState;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.AbstractComponentContainerConnector;

/**
 * @author Kamil Morong
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractControlContainerConnector extends AbstractComponentContainerConnector {

	@Override
	public AbstractControlContainerState getState() {
		return (AbstractControlContainerState) super.getState();
	}

	@Override
	public void updateCaption(ComponentConnector connector) {
		// nop
	}

}
