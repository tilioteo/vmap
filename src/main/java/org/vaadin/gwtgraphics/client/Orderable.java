package org.vaadin.gwtgraphics.client;

/**
 * @author morong
 */
public interface Orderable {

    /**
     * Insert the given VectorObject before the specified index.
     * <p>
     * If the VectorObjectContainer contains already the VectorObject, it will
     * be removed from the VectorObjectContainer before insertion.
     *
     * @param drawing     drawing to be inserted
     * @param beforeIndex the index before which the VectorObject will be inserted.
     * @return inserted drawing
     * @throws IndexOutOfBoundsException if <code>beforeIndex</code> is out of range
     */
    Drawing insert(Drawing drawing, int beforeIndex);

    /**
     * Returns the drawing element at the specified position.
     *
     * @param index index of element to return.
     * @return the drawing element at the specified position.
     */
    Drawing getDrawing(int index);

    /**
     *
     * Brings the given drawing to front in this VectorObjectContainer.
     *
     * @param drawing
     *            drawing to be brought to front
     * @return the popped drawing
     */
    // public Drawing bringToFront(Drawing drawing);

}
