package com.rewardspoints.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tid;
    private Long amount;
    private Long rewardPoints;
    @ManyToOne(targetEntity = Customer.class,cascade = CascadeType.MERGE)
    @JoinColumn(name = "CUSTOMER_ID",referencedColumnName = "CID")
    private Customer customer;
    private LocalDate date;
	public Transaction(Long amount, Long rewardPoints, LocalDate string) {
		super();
		this.amount = amount;
		this.rewardPoints = rewardPoints;
		this.date = string;
	}
    
    
}
