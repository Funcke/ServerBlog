package work.funcke.configuration;

import java.util.Map;

/**
 * Interface for Context Dependency Injection binding configurations.
 * As the basic CDI binding has to be done manually while starting the
 * application container, all injectable classes have to be registered
 * beforehand.
 *
 * To register new injectable entities, add the canonical class-name
 * (e.g. work.funcke.data.Repository) and the identifier it should be
 * mapped to in the cdi.properties.
 *
 * @author Jonas Funcke <jonas@funcke.work>
 */
public interface CDIConfiguration {
    /**
     * Returns all registered entity mappings.
     *
     * @return Map<String, String> - CDI entity mapping
     */
    public Map<String, String> entities();
    /**
     * Returns entity for mapping identifier provided.
     *
     * @param key String - Entity identifier
     * @return String - Name of class that is being provided for this identifier
     */
    public String get(String key);
}
