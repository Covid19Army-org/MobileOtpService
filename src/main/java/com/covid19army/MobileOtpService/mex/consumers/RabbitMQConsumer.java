package com.covid19army.MobileOtpService.mex.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer<T> {

	@RabbitListener(queues = "${covid19army.rabbitmq.mobileverificationrequestqueue}")
	public void recievedMessage(T message) {
		System.out.println("Recieved Message From RabbitMQ: " + message);
	}
}
