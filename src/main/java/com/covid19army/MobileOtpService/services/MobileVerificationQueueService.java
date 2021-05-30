package com.covid19army.MobileOtpService.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.covid19army.MobileOtpService.dtos.MobileVerificationResponseDto;
import com.covid19army.MobileOtpService.models.MobileVerificationQueue;
import com.covid19army.MobileOtpService.repositories.MobileVerificationQueueRepository;
import com.covid19army.core.dtos.MobileVerificationQueueDto;
import com.covid19army.core.dtos.OtpVerificationRequestDto;
import com.covid19army.core.dtos.PagedResponseDto;

@Service
public class MobileVerificationQueueService {

	@Autowired
	MobileVerificationQueueRepository _mobileVerificationQueueRepository;
	
	@Autowired
	ModelMapper _mapper;
	
	public long createQueueItem(MobileVerificationQueueDto mobileVerificationQueueDto) {
		
		MobileVerificationQueue newModel = _mapper.map(mobileVerificationQueueDto, MobileVerificationQueue.class);
		newModel.setOtp(OTP());
		_mobileVerificationQueueRepository.save(newModel);
		return newModel.getItemid();
	}
	
	public PagedResponseDto<MobileVerificationResponseDto> getNotProcessedItems( Pageable pageable){
		Page<MobileVerificationQueue> queueItemsPage = _mobileVerificationQueueRepository.findByIsprocessedFalse( pageable);
		List<MobileVerificationResponseDto> dtoList = queueItemsPage.getContent().stream()
				.map(q -> _mapper.map(q, MobileVerificationResponseDto.class))
				.collect(Collectors.toList());
		
		PagedResponseDto<MobileVerificationResponseDto> responseData = new PagedResponseDto<>();
		responseData.setCurrentPage(queueItemsPage.getNumber());
		responseData.setTotalItems(queueItemsPage.getTotalElements());
		responseData.setTotalPages(queueItemsPage.getTotalPages());
		responseData.setData(dtoList);
		
		return responseData;
		
	}
	
	public boolean validateOtp(OtpVerificationRequestDto dto) {
		Optional<MobileVerificationQueue> queueItem = _mobileVerificationQueueRepository.findByMobilenumberAndOtp(dto.getMobilenumber(), dto.getOtp());
		if(queueItem.isEmpty()) {
			return false;
		}
		_mobileVerificationQueueRepository.delete(queueItem.get());
		return true;
	}
	
	private int OTP()
    {
		//int randomPin declared to store the otp
        //since we using Math.random() hence we have to type cast it int
        //because Math.random() returns decimal value
        int randomPin   =(int) (Math.random()*90000)+10000;
        return randomPin;
    }
}
