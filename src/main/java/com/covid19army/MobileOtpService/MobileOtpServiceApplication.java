package com.covid19army.MobileOtpService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.covid19army.core.exceptions.GlobalExceptionHandler;
import com.covid19army.core.mex.rabbitmq.RabbitMQConfig;
import com.covid19army.core.mex.rabbitmq.RabbitMQListenerConfig;
import com.covid19army.core.mex.rabbitmq.RabbitMQSender;

@SpringBootApplication
@EnableDiscoveryClient
@Import({RabbitMQConfig.class, RabbitMQListenerConfig.class, GlobalExceptionHandler.class})
public class MobileOtpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileOtpServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public RabbitMQSender sendOtpExchangeSender(
			@Value("${covid19army.rabbitmq.sendotpexchange}") final String otpexchange,
			@Value("${covid19army.rabbitmq.sendotpexchange.routingkey:}") final String routingkey) {
		return new RabbitMQSender(otpexchange, routingkey);
		
	}
	
}
