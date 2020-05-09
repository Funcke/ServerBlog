package work.funcke.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * To register new injectable entities, add the canonical class-name
 * (e.g. work.funcke.data.Repository) and the identifier it should be
 * mapped to in the cdi.properties.
 *
 * This class serves as wrapper around the entity mapping provided
 * in cdi.properties.
 *
 * @author Jonas Funcke <jonas@funcke.work>
 */
public class CDIResourceConfiguration extends PropertyConfiguration implements CDIConfiguration{
    /**
     * Entity mapping.
     */
    private Map<String, String> entities;

    /**
     * default c'tor
     */
    public CDIResourceConfiguration() {
        super("cdi");
    }

    /**
     * Retrieves entity provided for the provided class.
     *
     * @param key String - Entity identifier
     * @return String - Class name
     */
    public String get(String key) {
        return getResourceBundle().getString(key);
    }

    /**
     * Returns entity mapping declared in cdi.properties.
     *
     * @return Map<String, String> - Entity mapping
     */
    public Map<String, String> entities() {
        if(this.entities == null) {
            this.entities = new HashMap<>();
            for(String entityName : getResourceBundle().keySet()) {
                entities.put(entityName, getResourceBundle().getString(entityName));
            }
        }

        return entities;
    }
}
