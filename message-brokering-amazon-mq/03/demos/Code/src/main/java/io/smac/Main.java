package io.smac;

import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import javax.jms.*;

public class Main {
    private final static String OPENWIRE_ENDPOINT = "";
    private final static String ACTIVE_MQ_USERNAME = "";
    private final static String ACTIVE_MQ_PASSWORD = "";
    private final static String QUEUE_NAME = "";

    public static void main(String[] args) {
	// write your code here
        try {
            Connection connection = getConnection();
            //produce(connection);
            //consume(connection);
            connection.close();

            System.out.println("Exiting app");
        }
        catch (JMSException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static Connection getConnection() throws JMSException {
        // Create a connection factory.
        final PooledConnectionFactory connectionFactory = createConnectionFactory();
        Connection connection = connectionFactory.createConnection();

        connection.start();

        return connection;
    }

    private static PooledConnectionFactory createConnectionFactory() {
        // Create a connection factory.
        final ActiveMQSslConnectionFactory connectionFactory = new ActiveMQSslConnectionFactory(OPENWIRE_ENDPOINT);

        // Pass the username and password.
        connectionFactory.setUserName(ACTIVE_MQ_USERNAME);
        connectionFactory.setPassword(ACTIVE_MQ_PASSWORD);

        final PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(connectionFactory);
        pooledConnectionFactory.setMaxConnections(10);
        return pooledConnectionFactory;
    }

    private static void produce(Connection connection) throws JMSException
    {
        final Session producerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Destination producerDestination = producerSession.createQueue(QUEUE_NAME);
        final MessageProducer producer = producerSession.createProducer(producerDestination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        for (int i=0; i<1; i++) {
            final String text = "Hello from Amazon MQ!";
            final TextMessage producerMessage = producerSession.createTextMessage(text);

            producer.send(producerMessage);
            System.out.println("Message sent.");
        }

        producer.close();
        producerSession.close();
    }

    private static void consume(Connection connection) throws JMSException
    {
        final Session consumerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Destination consumerDestination = consumerSession.createQueue(QUEUE_NAME);
        final MessageConsumer consumer = consumerSession.createConsumer(consumerDestination);

        int i = 0;
        while (i++ < 100) {
            final Message consumedMessage = consumer.receive(1000);
            if (consumedMessage != null) {
                final TextMessage consumedTextMessage = (TextMessage) consumedMessage;
                System.out.println(consumedTextMessage.getText());
            }
            else
            {
                System.out.println("No message received");
            }
        }

        System.out.println("Closing consumer");
        consumer.close();
        consumerSession.close();
    }
}
