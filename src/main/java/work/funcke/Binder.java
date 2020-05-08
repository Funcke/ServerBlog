package work.funcke;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import work.funcke.data.Repository;
import work.funcke.messages.AdministrationInterfacePublisher;

public class Binder extends AbstractBinder {
    @Override
    protected void configure() {
        try {
            bind(AdministrationInterfacePublisher.class).to(AdministrationInterfacePublisher.class);
            bind(Class.forName("work.funcke.data.Repository")).to(Class.forName("work.funcke.data.Repository"));
        } catch(ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
