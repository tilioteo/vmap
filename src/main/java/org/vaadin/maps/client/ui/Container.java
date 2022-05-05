package org.vaadin.maps.client.ui;

import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;

/**
 * @author Kamil Morong
 */
public interface Container {

    boolean contains(Widget widget);

    Widget getWidget(int index);

    int getWidgetCount();

    int getWidgetIndex(Widget widget);

    Iterator<Widget> iterator();

    boolean remove(int index);

    boolean remove(Widget widget);

}
