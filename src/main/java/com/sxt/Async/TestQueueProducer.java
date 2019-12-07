package com.sxt.Async;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import com.sxt.domain.User;

/**
 * @author sunshuaiyong
 * @version 创建时间：2019年12月7日 上午10:01:30
 * @Description 类描述
 */
public class TestQueueProducer {

	private static String URL = "tcp://www.sunsy.top:61616";

	private static String queueName = "queue-hello";

	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(URL);

		// 启用异步模式
		factory.setUseAsyncSend(true);
		factory.setTrustAllPackages(true);
		Connection connection = factory.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue(queueName);
		ActiveMQMessageProducer messageProducer = (ActiveMQMessageProducer) session.createProducer(queue);
		for (int i = 1; i <= 10; i++) {
			ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setStringProperty("msgId", "id====" + i);
			objectMessage.setObject(new User(1, "xiaoming", "wuhan"));
			messageProducer.send(objectMessage, new AsyncCallback() {

				@Override
				public void onException(JMSException exception) {
					try {
						System.out.println("出在异常" + objectMessage.getStringProperty("msgId"));
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				@Override
				public void onSuccess() {
					try {
						System.out.println("消息发送成功:" + objectMessage.getStringProperty("msgId"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		}
		messageProducer.close();
		session.close();
		connection.close();

	}

}
