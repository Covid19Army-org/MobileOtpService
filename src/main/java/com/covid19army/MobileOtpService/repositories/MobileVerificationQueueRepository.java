package com.covid19army.MobileOtpService.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.covid19army.MobileOtpService.models.MobileVerificationQueue;

public interface MobileVerificationQueueRepository extends PagingAndSortingRepository<MobileVerificationQueue, Long>{
	
	Page<MobileVerificationQueue> findByDateCreatedGreaterThanEqual(Date date, Pageable pageable);
	Optional<MobileVerificationQueue> findByMobilenumberAndOtp(String mobilenumber, int otp);
}
