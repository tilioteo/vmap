package org.vaadin.maps.server;

/**
 * @author Kamil Morong
 */
public interface WMSConstants {

    String PARAM_SERVICE_WMS = "SERVICE=WMS";
    String PARAM_VERSION = "VERSION";
    String PARAM_REQUEST = "REQUEST";
    String PARAM_LAYERS = "LAYERS";
    String PARAM_STYLES = "STYLES";
    String PARAM_SRS = "SRS";
    String PARAM_CRS = "CRS";
    String PARAM_BBOX = "BBOX";
    String PARAM_WIDTH = "WIDTH";
    String PARAM_HEIGHT = "HEIGHT";
    String PARAM_FORMAT = "FORMAT";
    String PARAM_TRANSPARENT = "TRANSPARENT";
    String PARAM_BGCOLOR = "BGCOLOR";

    String DEFAULT_VERSION = "1.1.1";
    String DEFAULT_FORMAT = "image/jpeg";
    int DEFAULT_WIDTH = 255;
    int DEFAULT_HEIGHT = 255;

    String GET_CAPABILITIES = "GetCapabilities";
    String GET_MAP = "GetMap";
    String GET_FEATURE_INFO = "GetFeatureInfo";

}
