package work.funcke.configuration;

import java.util.*;

/**
 * Configuration class.
 * This class serves as wrapper around .properties files.
 * It aims to make access to the key-value pairs provided in it
 * and to make typecasting around them easier.
 *
 * @author Jonas Funcke <jonas@funcke.work>
 */
public abstract class PropertyConfiguration {
    /**
     * Target resource bundle
     */
    private ResourceBundle resourceBundle;

    /**
     * c'tor
     * @param resource - Name of the .properties file to load
     */
    public PropertyConfiguration(String resource) {
        this.resourceBundle = ResourceBundle.getBundle(resource);
    }

    /**
     * Access resource bundle instance.
     *
     * @return Wrapped resource bundle
     */
    protected ResourceBundle getResourceBundle() {
        return this.resourceBundle;
    }

    /**
     * Retrieves value from wrapped resource and tries to parse it to
     * integer type.
     *
     * @param key identifier
     * @return retrieved value
     */
    public int getInteger(String key) {
        return Integer.parseInt(getResourceBundle().getString(key));
    }

    /**
     * Retrieves value from wrapped resource.
     *
     * @param key identifier
     * @return retrieved value
     */
    public String getString(String key) {
        return getResourceBundle().getString(key);
    }

    /**
     * Retrieves value from wrapped resource and tries to parse it to
     * boolean type.
     *
     * @param key identifier
     * @return retrieved value
     */
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getResourceBundle().getString(key));
    }

    /**
     * Retrieves value from wrapped resource and tries to parse it to
     * double type.
     *
     * @param key identifier
     * @return retrieved value
     */
    public double getDouble(String key) {
        return Double.parseDouble(getResourceBundle().getString(key));
    }

    /**
     * Retrieves array property from wrapped resource.
     *
     * @param key identifier
     * @return retrieved value
     */
    public Set<String> getStringSet(String key) {
        return new HashSet<>(Arrays.asList(getResourceBundle().getStringArray(key)));
    }

    /**
     * Retrieves all keys from wrapped resource
     *
     * @return keys
     */
    public Set<String> keys() {
        return getResourceBundle().keySet();
    }
}
