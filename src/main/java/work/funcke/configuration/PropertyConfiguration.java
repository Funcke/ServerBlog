package work.funcke.configuration;

import java.util.ResourceBundle;

public abstract class PropertyConfiguration {
    private ResourceBundle resourceBundle;

    public PropertyConfiguration(String resource) {
        this.resourceBundle = ResourceBundle.getBundle(resource);
    }

    protected ResourceBundle getResourceBundle() {
        return this.resourceBundle;
    }

    public int getInteger(String key) {
        return Integer.parseInt(getResourceBundle().getString(key));
    }

    public String getString(String key) {
        return getResourceBundle().getString(key);
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getResourceBundle().getString(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(getResourceBundle().getString(key));
    }
}
