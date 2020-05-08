package work.funcke;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import work.funcke.configuration.CDIConfiguration;

import java.util.Map;

public class ApplicationResourceBinder extends AbstractBinder {
    private CDIConfiguration cdiConfig;

    public ApplicationResourceBinder(CDIConfiguration cdiConfig) {
        this.cdiConfig = cdiConfig;
    }

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
