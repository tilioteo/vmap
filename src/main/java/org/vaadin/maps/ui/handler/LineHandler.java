/**
 * 
 */
package org.vaadin.maps.ui.handler;

import org.vaadin.maps.shared.ui.handler.LineHandlerState;
import org.vaadin.maps.ui.CanUndoRedo;
import org.vaadin.maps.ui.control.Control;

/**
 * @author Kamil Morong
 *
 */
@SuppressWarnings("serial")
public class LineHandler extends PointHandler implements CanUndoRedo {

	public LineHandler(Control control) {
		super(control);
	}

	@Override
	protected LineHandlerState getState() {
		return (LineHandlerState) super.getState();
	}

	@Override
	public boolean undo() {
		return false;
	}

	@Override
	public boolean redo() {
		return false;
	}

}
