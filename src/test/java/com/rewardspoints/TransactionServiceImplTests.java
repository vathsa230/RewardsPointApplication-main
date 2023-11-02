package com.rewardspoints;

import com.rewardspoints.dto.CustomerDTO;
import com.rewardspoints.dto.TransactionDTO;
import com.rewardspoints.exception.TransactionNotFoundException;
import com.rewardspoints.service.CustomerService;
import com.rewardspoints.service.TransactionService;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;


@SpringBootTest
@Slf4j
public class TransactionServiceImplTests {

    
    @Autowired  private TransactionService transactionService;

    @Autowired private CustomerService customerService;

    @Test
    public void getAllTransactionTest() {
        TransactionDTO transactionDto = new TransactionDTO();
        CustomerDTO  customerDto = new CustomerDTO();
        CustomerDTO savedCustomerDTO = customerService.saveUpdateCustomer(customerDto);
        transactionDto.setAmount(170l); transactionDto.setDate(LocalDate.of(2022, 01, 025));
        transactionDto.setCustomerDTO(savedCustomerDTO);
        TransactionDTO savedTransactionDTO = transactionService.saveUpdateTransaction(transactionDto);
        List<TransactionDTO> transactions = transactionService.getTransactions();
        Assertions.assertNotNull(transactions);
        Assertions.assertTrue(!transactions.isEmpty());
    }

    @Test
    public void getTransactionByIdTest() {
        TransactionDTO transactionDto = new TransactionDTO();
        CustomerDTO  customerDto = new CustomerDTO();
        CustomerDTO savedCustomerDTO = customerService.saveUpdateCustomer(customerDto);
        transactionDto.setAmount(170l); transactionDto.setDate(LocalDate.of(2022, 01, 025));
        transactionDto.setCustomerDTO(savedCustomerDTO);
        TransactionDTO savedTransactionDTO = transactionService.saveUpdateTransaction(transactionDto);
        TransactionDTO transaction = transactionService.getTransaction(savedTransactionDTO.getTid());
        Assertions.assertNotNull(transaction);
        Assertions.assertEquals(savedTransactionDTO.getTid(), transaction.getTid());
    }

    @Test
    public void saveTransactionsTest(){
    	TransactionDTO transactionDto = new TransactionDTO();
    	CustomerDTO  customerDto = new CustomerDTO();
        customerDto.setCid(7l);
        transactionDto.setAmount(170l); transactionDto.setDate(LocalDate.of(2022, 01, 025));
        transactionDto.setCustomerDTO(customerDto);
        TransactionDTO savedTransactionDTO = transactionService.saveUpdateTransaction(transactionDto);
        Assertions.assertNotNull(savedTransactionDTO);
        Assertions.assertEquals(LocalDate.of(2022, 01, 025), savedTransactionDTO.getDate());
        Assertions.assertNotNull(savedTransactionDTO.getTid());
    }
    
    @Test
    public void updateTransactionTest(){
    	TransactionDTO transactionDto = new TransactionDTO();
    	CustomerDTO  customerDto = new CustomerDTO();
        CustomerDTO savedCustomerDTO = customerService.saveUpdateCustomer(customerDto);
        transactionDto.setAmount(170l); transactionDto.setDate(LocalDate.of(2022, 01, 025));
        transactionDto.setCustomerDTO(savedCustomerDTO);
        TransactionDTO savedTransactionDTO = transactionService.saveUpdateTransaction(transactionDto);
        Assertions.assertNotNull(savedTransactionDTO);
        Assertions.assertEquals(LocalDate.of(2022, 01, 025), savedTransactionDTO.getDate());
        Assertions.assertNotNull(savedTransactionDTO.getTid());
    }
    
    @Test
    public void deleteTransactionTest() {
    	TransactionDTO transactionDto = new TransactionDTO();
    	CustomerDTO  customerDto = new CustomerDTO();
        CustomerDTO savedCustomerDTO = customerService.saveUpdateCustomer(customerDto);
        transactionDto.setAmount(170l); transactionDto.setDate(LocalDate.of(2022, 01, 025));
        transactionDto.setCustomerDTO(savedCustomerDTO);
        TransactionDTO savedTransactionDTO = transactionService.saveUpdateTransaction(transactionDto);
        transactionService.removeTransaction(savedTransactionDTO.getTid());
        Assertions.assertThrows(TransactionNotFoundException.class,()->transactionService.getTransaction(savedTransactionDTO.getTid()));
    }


}
