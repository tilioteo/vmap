package org.vaadin.gwtgraphics.client;

import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author Kamil Morong
 */
public interface Drawing extends IsWidget, HasClickHandlers, HasAllMouseHandlers, HasDoubleClickHandlers {

    Class<? extends Drawing> getType();

}
