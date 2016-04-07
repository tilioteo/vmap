/**
 * 
 */
package org.vaadin.maps.ui.handler;

import org.vaadin.maps.shared.ui.handler.PathHandlerState;
import org.vaadin.maps.ui.CanUndoRedo;
import org.vaadin.maps.ui.control.Control;

/**
 * @author Kamil Morong
 *
 */
@SuppressWarnings("serial")
public class PathHandler extends PointHandler implements CanUndoRedo {

	public enum FinishStrategy {
		AltClick, DoubleClick, NearStartClick
	}

	protected FinishStrategy strategy = FinishStrategy.AltClick;

	public PathHandler(Control control) {
		super(control);
	}

	@Override
	protected PathHandlerState getState() {
		return (PathHandlerState) super.getState();
	}

	@Override
	public boolean undo() {
		return false;
	}

	@Override
	public boolean redo() {
		return false;
	}

	public FinishStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(FinishStrategy strategy) {
		this.strategy = strategy;
		getState().strategy = strategy.ordinal();
	}

}