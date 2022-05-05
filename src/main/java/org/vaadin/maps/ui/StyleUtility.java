package org.vaadin.maps.ui;

import org.vaadin.maps.shared.ui.Style;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * @author Kamil Morong
 */
public class StyleUtility {

    public static HashMap<String, String> getStyleMap(Style style) {
        if (style != null) {
            HashMap<String, String> map = new HashMap<>();

            Field[] fields = Style.class.getDeclaredFields();
            for (Field field : fields) {
                if ((field.getModifiers() & Modifier.STATIC) != Modifier.STATIC) {
                    try {
                        map.put(field.getName(), field.get(style).toString());
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            return map;
        }
        return null;
    }

}
