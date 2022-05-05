package org.vaadin.maps.ui;

import com.vaadin.ui.Component;

import java.util.Iterator;

/**
 * @author Kamil Morong
 */
public interface HasComponents<C extends Component> extends com.vaadin.ui.HasComponents {
    /**
     * Gets an iterator to the collection of contained components. Using this
     * iterator it is possible to step through all components contained in this
     * container.
     *
     * @return the component iterator.
     */
    Iterator<C> typedIterator();

}
