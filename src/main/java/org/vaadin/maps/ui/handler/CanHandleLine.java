/**
 * 
 */
package org.vaadin.maps.ui.handler;

/**
 * @author Kamil Morong
 *
 */
public interface CanHandleLine {

	public void insertXY(Number x, Number y);

	public void insertDeltaXY(Number x, Number y);

	public void insertDirectionLength(Number direction, Number length);

	public void insertDeflectionLength(Number deflection, Number length);

}
