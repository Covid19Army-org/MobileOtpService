package com.covid19army.MobileOtpService.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.covid19army.MobileOtpService.dtos.MobileVerificationResponseDto;
import com.covid19army.MobileOtpService.models.MobileVerificationQueue;
import com.covid19army.MobileOtpService.repositories.MobileVerificationQueueRepository;
import com.covid19army.core.dtos.MobileVerificationQueueDto;
import com.covid19army.core.dtos.OtpVerificationRequestDto;
import com.covid19army.core.dtos.PagedResponseDto;
import com.covid19army.core.mex.rabbitmq.RabbitMQSender;

@Service
public class MobileVerificationQueueService {

	@Autowired
	MobileVerificationQueueRepository _mobileVerificationQueueRepository;
	
	@Autowired
	ModelMapper _mapper;
	
	@Autowired
	@Qualifier("sendOtpExchangeSender")
	RabbitMQSender _sendOtpExchangeSender;
	
	public long createQueueItem(MobileVerificationQueueDto mobileVerificationQueueDto) {
		
		Optional<MobileVerificationQueue> existingModel =
				_mobileVerificationQueueRepository.findByEntityidAndEntitytypeAndMobilenumberAndIsprocessedFalse(
						mobileVerificationQueueDto.getEntityid(),mobileVerificationQueueDto.getEntitytype()
						,mobileVerificationQueueDto.getMobilenumber());
		MobileVerificationQueue newModel = null;
		Date currDate = new Date();
		if(existingModel.isEmpty() || 
				(existingModel.isPresent() && currDate.compareTo(existingModel.get().getDateExpiry()) > 0)) {
			if(existingModel.isPresent())
				_mobileVerificationQueueRepository.delete(existingModel.get());
			newModel = _mapper.map(mobileVerificationQueueDto, MobileVerificationQueue.class);
			newModel.setOtp(OTP());
			_mobileVerificationQueueRepository.save(newModel);
		}
		else {
			newModel = existingModel.get();
		}
		
		MobileVerificationResponseDto sendOtpDto = new MobileVerificationResponseDto();
		sendOtpDto.setMobilenumber(newModel.getMobilenumber());
		sendOtpDto.setOtp(newModel.getOtp());
		_sendOtpExchangeSender.<MobileVerificationResponseDto>send(sendOtpDto);
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
