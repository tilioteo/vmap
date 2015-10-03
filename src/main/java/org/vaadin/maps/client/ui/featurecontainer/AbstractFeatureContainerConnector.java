/**
 * 
 */
package org.vaadin.maps.client.ui.featurecontainer;

import org.vaadin.maps.shared.ui.featurecontainer.AbstractFeatureContainerState;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentContainerConnector;

/**
 * @author kamil
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractFeatureContainerConnector extends
		AbstractComponentContainerConnector {

	protected boolean captureClick = true;
	
	@Override
	public AbstractFeatureContainerState getState() {
		return (AbstractFeatureContainerState) super.getState();
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);
		
		if (stateChangeEvent.hasPropertyChanged("captureClick")) {
			captureClick = getState().captureClick;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.vaadin.client.HasComponentsConnector#updateCaption(com.vaadin.client.ComponentConnector)
	 */
	@Override
	public void updateCaption(ComponentConnector connector) {
		// nop
	}

}
