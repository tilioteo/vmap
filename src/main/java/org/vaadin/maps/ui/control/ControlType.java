package org.vaadin.maps.ui.control;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kamil Morong
 */
public enum ControlType {
    BUTTON(1), TOGGLE(2), TOOL(3);

    private static final Map<Integer, ControlType> lookup = new HashMap<>();

    static {
        for (ControlType controlType : EnumSet.allOf(ControlType.class))
            lookup.put(controlType.getCode(), controlType);
    }

    private final int code;

    ControlType(int code) {
        this.code = code;
    }

    public static ControlType get(int code) {
        return lookup.get(code);
    }

    public int getCode() {
        return code;
    }

}
