package org.vaadin.maps.client.ui.controlcontainer;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.shared.ui.Connect;
import org.vaadin.maps.client.ui.VGeneralControlContainer;

import java.util.List;

/**
 * @author Kamil Morong
 */
@Connect(org.vaadin.maps.ui.controlcontainer.GeneralControlContainer.class)
public class GeneralControlContainerConnector extends AbstractControlContainerConnector {

    @Override
    public VGeneralControlContainer getWidget() {
        return (VGeneralControlContainer) super.getWidget();
    }

    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
        List<ComponentConnector> previousChildren = connectorHierarchyChangeEvent.getOldChildren();
        List<ComponentConnector> children = getChildComponents();
        VGeneralControlContainer container = getWidget();

        for (ComponentConnector previousChild : previousChildren) {
            if (!children.contains(previousChild)) {
                container.remove(previousChild.getWidget());
            }
        }

        for (ComponentConnector child : children) {
            if (!previousChildren.contains(child)) {
                container.add(child.getWidget());
            }
        }
    }

}
