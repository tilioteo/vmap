package org.vaadin.maps.ui.layer;

import com.vaadin.ui.Component;

/**
 * @author Kamil Morong
 */
public interface Layer extends Component {

    boolean isBase();

    boolean isFixed();
}
