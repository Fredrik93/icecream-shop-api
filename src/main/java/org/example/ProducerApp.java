package org.example;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerApp {

    public static final String QUEUE_NAME = "pizza-msg-queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // RabbitMQ host
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            // Declare the queue (idempotent, creates if it doesn't exist)
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            for (int i = 1; i <= 10; i++) {
                String message = "Pizza order #" + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
                System.out.println("Sent: " + message);
                Thread.sleep(1000); // optional delay between messages
            }
        }
    }
}
