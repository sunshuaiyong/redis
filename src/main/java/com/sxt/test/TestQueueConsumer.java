package com.sxt.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author sunshuaiyong
 * @version 创建时间：2019年12月5日 上午10:09:32
 * @Description 类描述
 */
public class TestQueueConsumer {

	private static String URL = "tcp://www.sunsy.top:61616";

	private static String QUEUE = "test-queue";

	public static void main(String[] args) throws JMSException {
		ConnectionFactory factory = new ActiveMQConnectionFactory(URL);
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue(QUEUE);
		MessageConsumer messageConsumer = session.createConsumer(queue);
		// 监听消息
		Message message = messageConsumer.receive();
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			System.out.println("接收到消息 ----内容为:" + textMessage.getText());
		}
		// 关闭资源
		messageConsumer.close();
		session.close();
		connection.close();
		System.out.println("消息接收完成！");
	}

}
