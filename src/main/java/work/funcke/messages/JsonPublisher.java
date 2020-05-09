package work.funcke.messages;

import java.io.IOException;

public interface JsonPublisher {
    public void publish(String key, Object data) throws IOException;
    public void publish(Object data) throws Exception;
}
