package java;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

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
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// create a messages
			String text = "Hello World!  From "
					+ Thread.currentThread().getName();
			TextMessage message = session.createTextMessage(text);

			// Tell the producer to send the message
			System.out.println("Sent message :" + message.hashCode() + " : "
					+ Thread.currentThread().getName());

			producer.send(message);

			// cleanup
			session.close();
			connection.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Caught : " + e);
			e.printStackTrace();
		}

	}

}
