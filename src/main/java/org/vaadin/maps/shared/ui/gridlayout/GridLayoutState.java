package org.vaadin.maps.shared.ui.gridlayout;

import com.vaadin.shared.Connector;
import org.vaadin.maps.shared.ui.AbstractLayoutState;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kamil Morong
 */
public class GridLayoutState extends AbstractLayoutState {
    public final Map<Connector, ChildComponentData> childData = new HashMap<>();
    public int rows = 0;
    public int columns = 0;

    {
        primaryStyleName = "v-gridlayout";
    }

    public static class ChildComponentData implements Serializable {
        public int column1;
        public int row1;
        public int column2;
        public int row2;

        public int left = 0;
        public int top = 0;
    }
}