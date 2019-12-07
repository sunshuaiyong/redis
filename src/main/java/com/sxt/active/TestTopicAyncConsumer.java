package com.sxt.active;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author sunshuaiyong
 * @version 创建时间：2019年12月5日 上午10:31:03
 * @Description 类描述
 */
public class TestTopicAyncConsumer {

	private static String URL = "tcp://www.sunsy.top:61616";

	private static String QUEUE = "test-Topic";

	public static void main(String[] args) throws JMSException, IOException {
		ConnectionFactory factory = new ActiveMQConnectionFactory(URL);
		Connection connection = factory.createConnection();
		connection.setClientID("zhangsan");
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);// 自动签收
		// 创建主题
		Topic topic = session.createTopic(QUEUE);
		// 创建一个永久的主体订阅者   消费者对象
		TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "zhangsan");
		connection.start();
		topicSubscriber.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					System.out.println("消息接收----内容为:" + text);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		// 不终止程序，可以一直监听
		System.in.read();
		// 关闭资源
		topicSubscriber.close();
		session.close();
		connection.close();
		System.out.println("消息接收完成！");
	}

}
