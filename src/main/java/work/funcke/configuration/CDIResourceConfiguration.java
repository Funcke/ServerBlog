package work.funcke.configuration;

import java.util.HashMap;
import java.util.Map;

public class CDIResourceConfiguration extends PropertyConfiguration implements CDIConfiguration{
    private Map<String, String> entities;

    public CDIResourceConfiguration() {
        super("cdi");
    }

    public String get(String key) {
        return getResourceBundle().getString(key);
    }

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
