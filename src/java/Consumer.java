package java;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

	public static void main(String[] args) {

		try {
			// create ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					"tcp://GS-2170:61616");

			// create connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// create session
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// create the destination topic or queue
			Destination destination = session.createQueue("TestQueue123");

			// create a message producer from the session to the topic or queue
			MessageConsumer consumer = session.createConsumer(destination);
			// consumer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// wait for a message
			Message message = consumer.receive(1000);

			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
				System.out.println("Received: " + text);
			} else {
				System.out.println("Received:" + message);
			}

			// cleanup
			consumer.close();
			session.close();
			connection.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Caught : " + e);
			e.printStackTrace();
		}

	}

}
