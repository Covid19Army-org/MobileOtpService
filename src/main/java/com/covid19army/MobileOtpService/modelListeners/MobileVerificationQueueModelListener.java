package com.covid19army.MobileOtpService.modelListeners;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Value;

import com.covid19army.MobileOtpService.models.MobileVerificationQueue;

public class MobileVerificationQueueModelListener {
	
	@Value("${covid19army.otp.expiration.duration:15}")
	int otpExpirationDuration;

	@PrePersist
	public void onCreating(MobileVerificationQueue mobileVerificationQueue) {
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.MINUTE, otpExpirationDuration);
		
		
		mobileVerificationQueue.setDateCreated(currentDate);
		mobileVerificationQueue.setDateExpiry(cal.getTime());
		mobileVerificationQueue.setIsprocessed(false);
	}
}
