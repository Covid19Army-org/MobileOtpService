package com.covid19army.MobileOtpService.modelListeners;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.PrePersist;

import com.covid19army.MobileOtpService.models.MobileVerificationQueue;

public class MobileVerificationQueueModelListener {

	@PrePersist
	public void onCreating(MobileVerificationQueue mobileVerificationQueue) {
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.MINUTE, 10);
		
		
		mobileVerificationQueue.setDateCreated(currentDate);
		mobileVerificationQueue.setDateExpiry(cal.getTime());
		mobileVerificationQueue.setIsprocessed(false);
	}
}
