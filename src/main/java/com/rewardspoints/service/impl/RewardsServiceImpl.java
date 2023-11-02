package com.rewardspoints.service.impl;

import com.rewardspoints.dto.CustomerDTO;
import com.rewardspoints.dto.RewardsDto;
import com.rewardspoints.dto.TransactionDTO;
import com.rewardspoints.service.CustomerService;
import com.rewardspoints.service.RewardsService;
import com.rewardspoints.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class RewardsServiceImpl implements RewardsService {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CustomerService customerService;

    @Override
    public RewardsDto getRewardsMonthWise(Long id) {
        log.info("getRewardsMonthWise started customer id ::"+id);
        RewardsDto rewardsDto = null;
        HashMap<String,Long> map= new HashMap<>();
        CustomerDTO customerDTO = customerService.getCustomerDetailsById(id);
        rewardsDto = new RewardsDto();
        rewardsDto.setCustomerName(customerDTO.getName());
        rewardsDto.setTotalRewardsPoints(customerDTO.getTotalRewardPoints());
        for(Month month:Month.values()) {
            List<TransactionDTO> transactionDTOS = customerDTO.getTransactions();
            Long monthPoint = totalRewardsPointInMonth(transactionService.getTransactionsByCustomerAndMonths(customerDTO,month));
            if(monthPoint>0){
                map.put(month.name(),monthPoint);
            }
        }
        rewardsDto.setMonths(map);
        log.info("getRewardsMonthWise ended");
        return rewardsDto;
    }

    public Long totalRewardsPointInMonth(List<TransactionDTO> transactionDTOS){
        log.info("totalRewardsPointInMonth method started total transactions :: "+transactionDTOS.size());
        Long totalReward = 0l;
        for(TransactionDTO transactionDTO : transactionDTOS){
            totalReward = totalReward + transactionDTO.getRewardPoints();
        }
        log.info("totalRewardsPointInMonth method ended total Reward :: "+totalReward);
        return totalReward;
    }
}
