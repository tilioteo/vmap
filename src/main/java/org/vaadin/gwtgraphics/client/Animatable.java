package org.vaadin.gwtgraphics.client;

/**
 * Classes implementing this interface can be animated by using the Animate
 * class.
 *
 * @author Henri Kerola
 */
public interface Animatable {

    /**
     * Set the value of a numeric property.
     *
     * @param property the property to be set
     * @param value    the value
     */
    void setPropertyDouble(String property, double value);
}
