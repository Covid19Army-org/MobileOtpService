package com.covid19army.MobileOtpService.controllers;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid19army.MobileOtpService.dtos.MobileVerificationResponseDto;
import com.covid19army.MobileOtpService.services.MobileVerificationQueueService;
import com.covid19army.core.dtos.MobileVerificationQueueDto;
import com.covid19army.core.dtos.OtpVerificationRequestDto;
import com.covid19army.core.dtos.PagedResponseDto;

@RestController
@RequestMapping("motp")
public class MobileOtpVerificationController {

	@Autowired
	MobileVerificationQueueService _mobileVerificationQueueService;
	
	@PostMapping("/create")
	public long createOtp(@RequestBody MobileVerificationQueueDto mobileVerificationQueueDto) {
		return _mobileVerificationQueueService.createQueueItem(mobileVerificationQueueDto);
	}
	
	@PostMapping("/verify")
	public boolean validateOtp(@RequestBody OtpVerificationRequestDto otpVerificationRequestDto) {
		return _mobileVerificationQueueService.validateOtp(otpVerificationRequestDto);
	}
	
	@GetMapping("/getnotprocesseditems")
	public PagedResponseDto<MobileVerificationResponseDto> getNotProcessedQueueItems(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size){
		Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.ASC, "dateCreated"));
		return _mobileVerificationQueueService.getNotProcessedItems(pageable);
	}
	
}
