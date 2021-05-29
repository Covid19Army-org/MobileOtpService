package com.covid19army.MobileOtpService.dtos;

public class MobileVerificationResponseDto {

	private String mobilenumber;

	private int otp;

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}
	
	
}
