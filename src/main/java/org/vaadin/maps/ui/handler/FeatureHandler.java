/**
 * 
 */
package org.vaadin.maps.ui.handler;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.vaadin.maps.event.ComponentEvent;
import org.vaadin.maps.shared.ui.Style;
import org.vaadin.maps.shared.ui.handler.FeatureHandlerState;
import org.vaadin.maps.ui.CanFreeze;
import org.vaadin.maps.ui.control.Control;
import org.vaadin.maps.ui.feature.VectorFeature;

import com.vaadin.util.ReflectTools;

/**
 * @author Kamil Morong
 *
 */
@SuppressWarnings("serial")
public abstract class FeatureHandler extends AbstractHandler implements RequiresVectorFeatureLayer, CanFreeze {

	protected Style featureStyle = null;

	protected FeatureHandler(Control control) {
		super(control);
	}

	@Override
	protected FeatureHandlerState getState() {
		return (FeatureHandlerState) super.getState();
	}

	public Style getFeatureStyle() {
		return featureStyle;
	}

	public void setFeatureStyle(Style featureStyle) {
		this.featureStyle = featureStyle;
	}

	/**
	 * This event is thrown, when the geometry is drawn.
	 * 
	 */
	public class DrawFeatureEvent extends ComponentEvent {

		public DrawFeatureEvent(long timestamp, PointHandler source, VectorFeature feature) {
			super(timestamp, source);
			this.feature = feature;
		}

		private VectorFeature feature;

		public VectorFeature getFeature() {
			return feature;
		}
	}

	/**
	 * Interface for listening for a {@link DrawFeatureEvent} fired by a
	 * {@link PointHandler}.
	 * 
	 */
	public interface DrawFeatureListener extends Serializable {

		public static final Method DRAW_FEATURE_METHOD = ReflectTools.findMethod(DrawFeatureListener.class,
				"drawFeature", DrawFeatureEvent.class);

		/**
		 * Called when a feature has been drawn.
		 * 
		 * @param event
		 *            An event containing information about the geometry.
		 */
		public void drawFeature(DrawFeatureEvent event);

	}

	/**
	 * Adds the draw feature listener.
	 * 
	 * @param listener
	 *            the Listener to be added.
	 */
	public void addDrawFeatureListener(DrawFeatureListener listener) {
		addListener(DrawFeatureEvent.class, listener, DrawFeatureListener.DRAW_FEATURE_METHOD);
	}

	/**
	 * Removes the draw feature listener.
	 * 
	 * @param listener
	 *            the Listener to be removed.
	 */
	public void removeDrawFeatureListener(DrawFeatureListener listener) {
		removeListener(DrawFeatureEvent.class, listener, DrawFeatureListener.DRAW_FEATURE_METHOD);
	}

	@Override
	public void freeze() {
		setFrozen(true);
	}

	@Override
	public void unfreeze() {
		setFrozen(false);
	}

	protected void setFrozen(boolean frozen) {
		getState().frozen = frozen;
	}

}
