package org.vaadin.maps.server;

import com.vaadin.server.ExternalResource;
import org.vaadin.maps.ui.MapConstants;

/**
 * @author Kamil Morong
 */
public class WMSResource extends ExternalResource implements TileResource {

    private final String baseUrl;
    private int width = WMSConstants.DEFAULT_WIDTH;
    private int height = WMSConstants.DEFAULT_HEIGHT;
    private String version = WMSConstants.DEFAULT_VERSION;
    private String format = WMSConstants.DEFAULT_FORMAT;
    private String layers = "";
    private String styles = "";
    private String srs = MapConstants.DEFAULT_CRS;
    private String bbox = "";
    private boolean transparent = false;

    public WMSResource(String baseURL) {
        super(baseURL);
        this.baseUrl = baseURL;
    }

    /**
     * Gets the URL of the external resource.
     *
     * @return the URL of the external resource.
     */
    public String getURL() {
        return buildRequest();
    }

    @Override
    public String getMIMEType() {
        return format;
    }

    private String buildRequest() {
        StringBuilder builder = new StringBuilder(baseUrl);
        if (!baseUrl.endsWith("?")) {
            builder.append("?");
        }
        builder.append(WMSConstants.PARAM_SERVICE_WMS);
        builder.append("&" + WMSConstants.PARAM_VERSION + "=").append(version);
        builder.append("&" + WMSConstants.PARAM_LAYERS + "=").append(layers);
        builder.append("&" + WMSConstants.PARAM_REQUEST + "=" + WMSConstants.GET_MAP);

        if (version.startsWith("1.1")) {
            builder.append("&" + WMSConstants.PARAM_SRS);
        } else {
            builder.append("&" + WMSConstants.PARAM_CRS);
        }
        builder.append("=").append(srs);
        builder.append("&" + WMSConstants.PARAM_BBOX + "=").append(bbox);
        builder.append("&" + WMSConstants.PARAM_WIDTH + "=").append(width);
        builder.append("&" + WMSConstants.PARAM_HEIGHT + "=").append(height);
        builder.append("&" + WMSConstants.PARAM_FORMAT + "=").append(format);
        builder.append("&" + WMSConstants.PARAM_STYLES + "=").append(styles);

        if (transparent) {
            builder.append("&" + WMSConstants.PARAM_TRANSPARENT + "=" + "TRUE");
        }

        return builder.toString();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (this.width != width) {
            if (width > 0) {
                this.width = width;
            } else {
                this.width = WMSConstants.DEFAULT_WIDTH;
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (this.height != height) {
            if (height > 0) {
                this.height = height;
            } else {
                this.height = WMSConstants.DEFAULT_HEIGHT;
            }
        }
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        if (!this.version.equals(version)) {
            if (version != null && !version.isEmpty()) {
                this.version = version;
            } else {
                this.version = WMSConstants.DEFAULT_VERSION;
            }
        }
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        if (!this.format.equals(format)) {
            if (format != null && !format.isEmpty()) {
                this.format = format;
            } else {
                this.format = WMSConstants.DEFAULT_FORMAT;
            }
        }
    }

    public String getLayers() {
        return layers;
    }

    public void setLayers(String layers) {
        if (!this.layers.equals(layers)) {
            if (layers != null && !layers.isEmpty()) {
                this.layers = layers;
            } else {
                this.layers = "";
            }
        }
    }

    public String getStyles() {
        return styles;
    }

    public void setStyles(String styles) {
        if (!this.styles.equals(styles)) {
            if (styles != null && !styles.isEmpty()) {
                this.styles = styles;
            } else {
                this.styles = "";
            }
        }
    }

    public String getSRS() {
        return srs;
    }

    public void setSRS(String srs) {
        if (!this.srs.equals(srs)) {
            if (srs != null && !srs.isEmpty()) {
                this.srs = srs;
            } else {
                this.srs = MapConstants.DEFAULT_CRS;
            }
        }
    }

    public String getBBox() {
        return bbox;
    }

    public void setBBox(String bbox) {
        if (!this.bbox.equals(bbox)) {
            if (bbox != null && !bbox.isEmpty()) {
                this.bbox = bbox;
            } else {
                this.bbox = "";
            }
        }
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

}
