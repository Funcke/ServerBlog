package work.funcke.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class AdministrationInterfacePublisher implements JsonPublisher {
    private static final String EXCHANGE_NAME = "administration";
    private Channel channel;
    private String routingKey;
    private ObjectMapper objectMapper = new ObjectMapper();

    public AdministrationInterfacePublisher() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        this.channel = factory.newConnection().createChannel();
        this.channel.exchangeDeclare(EXCHANGE_NAME, "topic");
    }

    public AdministrationInterfacePublisher(String routingKey) throws Exception {
        this();
        this.routingKey = routingKey;
    }

    public void setRoutingKey(String key) {
        this.routingKey = key;
    }

    public void publishMessage(String message) throws IOException {
        publishMessage(routingKey, message);
    }

    public void publishMessage(String key, String message) throws IOException {
        publish(key, message.getBytes());
    }

    public void publish(String key, Object data) throws IOException {
        publish(key, objectMapper.writeValueAsBytes(data));
    }

    public void publish(Object data) throws IOException {
        publish(routingKey, data);
    }

    private void publish(String key, byte[] data) throws IOException {
        this.channel.basicPublish(EXCHANGE_NAME, key, null, data);
    }
}
