package com.rewardspoints.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class TransactionDTO {

    private Long tid;
    private Long amount;
    private Long rewardPoints;
    private CustomerDTO customerDTO;
    private LocalDate date;
	public TransactionDTO(Long amount, Long rewardPoints, LocalDate date) {
		super();
		this.amount = amount;
		this.rewardPoints = rewardPoints;
		this.date = date;
	}
    
    
}

