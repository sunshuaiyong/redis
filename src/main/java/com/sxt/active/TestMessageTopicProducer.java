package com.sxt.active;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
* @author sunshuaiyong
* @version 创建时间：2019年12月5日 下午3:35:40
* @Description 类描述
*/
public class TestMessageTopicProducer {
	
	private static final String QUEUE = "test-Topic";
	private static final String URL = "tcp://www.sunsy.top:61616";

	public static void main(String[] args) throws Exception {
		//创建ActiveMQConnectionFactory对象
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
		//创建连接对象
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//创建回话
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建Qyeue对象
		Topic topic=session.createTopic(QUEUE);
		//创建生产者对象
		MessageProducer producer = session.createProducer(topic);
		//默认为持久消息
		//producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		//创建Message对象
		
		//使用生产者发送消息
		for (int i = 1; i <= 12; i++) {
			TextMessage textMessage = session.createTextMessage("Hello World   "+i);
			producer.send(textMessage);
		}
		//关闭资源
		producer.close();
		session.close();
		connection.close();
		System.out.println("生产者向MQ发送消息成功！");

	}
	
}

