package org.vaadin.maps.client.ui.featurecontainer;

import com.google.gwt.event.dom.client.*;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;
import org.vaadin.maps.client.DateUtility;
import org.vaadin.maps.client.ui.VVectorFeatureContainer;
import org.vaadin.maps.shared.ui.featurecontainer.VectorFeatureContainerServerRpc;

import java.util.List;

/**
 * @author Kamil Morong
 */
@Connect(org.vaadin.maps.ui.featurecontainer.VectorFeatureContainer.class)
public class VectorFeatureContainerConnector extends AbstractFeatureContainerConnector
        implements ClickHandler, MouseDownHandler, MouseMoveHandler {

    private boolean mouseDown = false;
    private boolean mouseMoved = false;

    @Override
    protected void init() {
        super.init();
        getWidget().addClickHandler(this);
        getWidget().addMouseDownHandler(this);
        getWidget().addMouseMoveHandler(this);
    }

    @Override
    public VVectorFeatureContainer getWidget() {
        return (VVectorFeatureContainer) super.getWidget();
    }

    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
        List<ComponentConnector> previousChildren = connectorHierarchyChangeEvent.getOldChildren();
        List<ComponentConnector> children = getChildComponents();
        VVectorFeatureContainer container = getWidget();

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

    @Override
    public void onClick(ClickEvent event) {
        if (!mouseMoved) {
            MouseEventDetails mouseDetails = MouseEventDetailsBuilder.buildMouseEventDetails(event.getNativeEvent(),
                    getWidget().getElement());
            getRpcProxy(VectorFeatureContainerServerRpc.class).click(DateUtility.getTimestamp(), mouseDetails);
        } else {
            mouseMoved = false;
        }
        mouseDown = false;
    }

    @Override
    public void onMouseDown(MouseDownEvent event) {
        mouseDown = true;
    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {
        if (mouseDown) {
            mouseMoved = true;
        }
    }

}
