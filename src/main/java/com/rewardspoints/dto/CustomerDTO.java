package com.rewardspoints.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDTO {


    private Long cid;

    private String name;

    private List<TransactionDTO> transactions;

    private Long totalRewardPoints = 0l;

}
