package work.funcke.messages;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class AdministrationInterfacePublisher {
    private static final String EXCHANGE_NAME = "administration";
    private Channel channel;
    private String routingKey;

    public AdministrationInterfacePublisher() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        //factory.setUri("amqp://ixwqfxak:CzfZYkg_H6Gov7kXv8R9FeWRpUz7Znh6@stingray.rmq.cloudamqp.com/ixwqfxak");
        //factory.setUsername("ixwqfxak");
        //factory.setPassword("CzfZYkg_H6Gov7kXv8R9FeWRpUz7Znh6");
        this.channel = factory.newConnection().createChannel();
        this.channel.exchangeDeclare(EXCHANGE_NAME, "topic");
    }

    public void setRoutingKey(String key) {
        this.routingKey = key;
    }

    public void publishMessage(String message) throws IOException {
        this.channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
    }

    public void publishMessage(String key, String message) throws IOException {
        this.channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes());
    }
}
