/**
 * 
 */
package org.vaadin.maps.client.ui.featurecontainer;

import org.vaadin.maps.shared.ui.featurecontainer.AbstractFeatureContainerState;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.AbstractComponentContainerConnector;

/**
 * @author Kamil Morong
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractFeatureContainerConnector extends AbstractComponentContainerConnector {

	@Override
	public AbstractFeatureContainerState getState() {
		return (AbstractFeatureContainerState) super.getState();
	}

	@Override
	public void updateCaption(ComponentConnector connector) {
		// nop
	}

}
