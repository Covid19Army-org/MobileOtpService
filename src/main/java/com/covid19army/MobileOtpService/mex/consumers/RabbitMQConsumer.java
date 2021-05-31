package com.covid19army.MobileOtpService.mex.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.covid19army.MobileOtpService.services.MobileVerificationQueueService;
import com.covid19army.core.dtos.MobileVerificationQueueDto;

@Component
public class RabbitMQConsumer {
	
	@Autowired
	MobileVerificationQueueService _verificationQueueService;

	@RabbitListener(queues = "${covid19army.rabbitmq.mobileverificationrequestqueue}")
	public void recievedMessage(MobileVerificationQueueDto message) {		
		System.out.println("Recieved Message From RabbitMQ: " + message.getMobilenumber());
		long otpId = _verificationQueueService.createQueueItem(message);		
		System.out.println("Recieved Message From RabbitMQ: " + otpId);
	}
}
