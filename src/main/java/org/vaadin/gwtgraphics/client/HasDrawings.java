/*
 * Copyright 2011 Henri Kerola
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vaadin.gwtgraphics.client;

import java.util.Iterator;

/**
 * Classes implementing this class are able to contain VectorObjects.
 * <p>
 * renamed from VectorObjectContainer
 *
 * @author Henri Kerola
 * @author Kamil Morong
 */
public interface HasDrawings {

    /**
     * Adds a child drawing.
     *
     * @param drawing the drawing to be added
     * @return added drawing
     * @throws UnsupportedOperationException if this method is not supported (most often this means that a
     *                                       specific overload must be called)
     */
    public Drawing addDrawing(Drawing drawing);

    /**
     * Gets an iterator for the contained drawings. This iterator is required to
     * implement {@link Iterator#remove()}.
     */
    public Iterator<Drawing> drawingIterator();

    /**
     * Removes a child drawing.
     *
     * @param drawing the drawing to be removed
     * @return removed drawing or null if the container doesn't contain the
     * given drawing
     */
    public Drawing removeDrawing(Drawing drawing);

    /**
     * Removes all child drawings.
     */
    public void clear();

    /**
     * Returns the number of drawings in this container.
     *
     * @return the number of drawings in this container.
     */
    public int getCount();

}