package work.funcke;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import work.funcke.configuration.CDIConfiguration;

import java.util.Map;

/**
 * Binder implementation.
 * This class carries the context dependency injection (CDI) registry
 * for javax.inject.
 * All resources that should be made available for CDI shall be provided
 * in a key=value manner in an implementation of CDIConfiguration.
 *
 * @author Jonas Funcke <jonas@funcke.work>
 */
public class ApplicationResourceBinder extends AbstractBinder {
    /**
     * Implementation of CDIConfiguration carrying a map of all
     * entities that are being provided to CDI system.
     */
    private CDIConfiguration cdiConfig;

    /**
     * c'tor
     * @param cdiConfig CDIConfiguration - CDI entity mapping configuration
     */
    public ApplicationResourceBinder(CDIConfiguration cdiConfig) {
        this.cdiConfig = cdiConfig;
    }

    /**
     * Registers CDI mappings to the application CDI system.
     */
    @Override
    protected void configure() {
        try {
            for(Map.Entry<String, String> entity : this.cdiConfig.entities().entrySet()) {
                bind(Class.forName(entity.getKey())).to(Class.forName(entity.getValue()));
            }
        } catch(ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
