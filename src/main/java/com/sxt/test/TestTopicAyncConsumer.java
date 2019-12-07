package com.sxt.test;

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
* @version 创建时间：2019年12月5日 上午11:16:42
* @Description 类描述
*/
public class TestTopicAyncConsumer {
	
	private static String URL = "tcp://www.sunsy.top:61616";

	private static String QUEUE = "test-queue";

	public static void main(String[] args) throws JMSException, IOException {
		ConnectionFactory factory = new ActiveMQConnectionFactory(URL);
		Connection connection = factory.createConnection();
		connection.setClientID("zhangsan");
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic=session.createTopic(QUEUE);
		TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic,"remark");
		System.out.println("消费者2");
		topicSubscriber.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage=(TextMessage) message;
					String text = textMessage.getText();
					System.out.println("消息接收----内容为:"+text);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		//不终止程序，可以一直监听
		System.in.read();
		// 关闭资源
		topicSubscriber.close();
		session.close();
		connection.close();
		System.out.println("消息接收完成！");
	}

	
}

