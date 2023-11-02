package com.rewardspoints;

import com.rewardspoints.dto.CustomerDTO;
import com.rewardspoints.dto.RewardsDto;
import com.rewardspoints.dto.TransactionDTO;
import com.rewardspoints.service.RewardsService;
import com.rewardspoints.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rewardspoints.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Slf4j
public class RewardsServiceImplTest {

	 @Autowired private CustomerService customerService;

	 @Autowired private TransactionService transactionService;

	 @Autowired private RewardsService rewardsService;
	 
	 @Test
	 public void getRewardPointsMonthWise() {
		 TransactionDTO transactionDto = new TransactionDTO();
		 CustomerDTO  customerDto = new CustomerDTO();
		 customerDto.setName("test1");
		 CustomerDTO customerDTO= customerService.saveUpdateCustomer(customerDto);
		 transactionDto.setAmount(170l); transactionDto.setDate(LocalDate.of(2022, 01, 025));
		 transactionDto.setCustomerDTO(customerDTO);
		 TransactionDTO savedTransactionDTO = transactionService.saveUpdateTransaction(transactionDto);
		 RewardsDto rewardsDto = rewardsService.getRewardsMonthWise(customerDTO.getCid());
		 Assertions.assertNotNull(rewardsDto);
		 Assertions.assertTrue(rewardsDto.getMonths().size()>0);
		 Assertions.assertNotNull(rewardsDto.getMonths());
	 }

	 @Test
	public void calculateRewardsPointTest(){
		Assertions.assertEquals(90,RewardsService.calculateRewardsPoint(120l));
		 Assertions.assertEquals(20,RewardsService.calculateRewardsPoint(70l));
		 Assertions.assertEquals(0,RewardsService.calculateRewardsPoint(45l));
	}

	@Test
	public void totalRewardsPointInMonthTest(){
		TransactionDTO transactionDto1 = new TransactionDTO();
		TransactionDTO transactionDto2 = new TransactionDTO();
		CustomerDTO  customerDto = new CustomerDTO();
		customerDto.setName("test1");
		CustomerDTO customerDTO= customerService.saveUpdateCustomer(customerDto);
		transactionDto1.setAmount(120l); transactionDto1.setDate(LocalDate.of(2022, 01, 025));
		transactionDto1.setCustomerDTO(customerDTO);
		TransactionDTO savedTransactionDTO1 = transactionService.saveUpdateTransaction(transactionDto1);
		transactionDto2.setAmount(70l); transactionDto1.setDate(LocalDate.of(2022, 02, 025));
		transactionDto2.setCustomerDTO(customerDTO);
		TransactionDTO savedTransactionDTO2 = transactionService.saveUpdateTransaction(transactionDto2);

		Assertions.assertEquals(110,rewardsService.totalRewardsPointInMonth(List.of(savedTransactionDTO1,savedTransactionDTO2)));
	}
}
