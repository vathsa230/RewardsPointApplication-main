package com.rewardspoints.service;

import com.rewardspoints.dto.CustomerDTO;
import com.rewardspoints.dto.TransactionDTO;

import java.time.Month;
import java.util.List;


public interface TransactionService {

    TransactionDTO saveUpdateTransaction(TransactionDTO transactionDTO);

    List<TransactionDTO> getTransactions();

    void removeTransaction(Long id);

    TransactionDTO getTransaction(Long id);

    List<TransactionDTO> getTransactionsByCustomerAndMonths(CustomerDTO customerDTO, Month month);
}
