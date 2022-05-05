package org.vaadin.maps.shared.ui.handler;

import org.vaadin.maps.shared.ui.ClickRpc;

/**
 * @author Kamil Morong
 */
public interface PointHandlerServerRpc extends ClickRpc {

    void geometry(long timestamp, String wkb);

}
