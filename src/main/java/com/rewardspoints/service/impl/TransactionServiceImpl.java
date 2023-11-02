package com.rewardspoints.service.impl;

import com.rewardspoints.dto.CustomerDTO;
import com.rewardspoints.dto.TransactionDTO;
import com.rewardspoints.entity.Customer;
import com.rewardspoints.entity.Transaction;
import com.rewardspoints.exception.CustomerNotFoundException;
import com.rewardspoints.exception.TransactionNotFoundException;
import com.rewardspoints.repository.CustomerRepository;
import com.rewardspoints.repository.TransactionRepository;
import com.rewardspoints.service.RewardsService;
import com.rewardspoints.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TransactionDTO saveUpdateTransaction(TransactionDTO transactionDTO){
        log.info("saveUpdateTransaction method started Purchase value :: "+transactionDTO.getAmount());

        CustomerDTO customerDTO = transactionDTO.getCustomerDTO();
        if(customerDTO==null){
            throw new CustomerNotFoundException("Without Customer Transaction would not Happen");
        }
        Optional<Customer> existCustomerOptional = customerRepository.findById(customerDTO.getCid());
        if(existCustomerOptional.isEmpty()){
            throw new CustomerNotFoundException("Customer Not Exist");
        }
        transactionDTO.setRewardPoints(RewardsService.calculateRewardsPoint(transactionDTO.getAmount()));
        Customer existCustomer = existCustomerOptional.get();
        existCustomer.setTotalRewardPoints(existCustomer.getTotalRewardPoints()+transactionDTO.getRewardPoints());
        CustomerDTO existCustomerDto = modelMapper.map(existCustomer,CustomerDTO.class);
        transactionDTO.setCustomerDTO(existCustomerDto);
        Transaction transaction= modelMapper.map(transactionDTO,Transaction.class);
        Transaction savedTransaction = transactionRepository.save(transaction);
        TransactionDTO savedTransactionDto= modelMapper.map(savedTransaction,TransactionDTO.class);
        log.info("Transaction Saved Successfully transactionId :: "+savedTransaction.getTid());
        return savedTransactionDto;
    }

    @Override
    public List<TransactionDTO> getTransactions() {
        log.info("getTransactions method started.");
        List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
        List<TransactionDTO> transactionDTOS = transactions.stream().map(user -> modelMapper.map(user, TransactionDTO.class)).collect(Collectors.toList());
        log.info("getTransactions method completed total tranactions :: "+transactions.size());
        return transactionDTOS;
    }

    @Override
    public void removeTransaction(Long id) {
        log.info("removeTransaction method started transaction id ::"+id);
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if(transactionOptional.isEmpty()){
            throw new TransactionNotFoundException("Transaction not found with id ::"+id);
        }
        Transaction transaction = transactionOptional.get();
        Customer customer= transaction.getCustomer();

        customer.setTotalRewardPoints(customer.getTotalRewardPoints()-transaction.getRewardPoints());
        customer.getTransactions().remove(transaction);

        customerRepository.save(customer);
                transactionRepository.delete(transaction);
        log.info("removeTransaction method ended");
    }

    @Override
    public TransactionDTO getTransaction(Long id) {
        log.info("getTransaction method started transaction id ::"+id);
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if(transactionOptional.isEmpty()){
            log.error("getTransaction Transaction Not Found with id ::"+id);
            throw new TransactionNotFoundException("Invalid Transaction ID ::"+id);
        }
        TransactionDTO transactionDTO = modelMapper.map(transactionOptional.get(),TransactionDTO.class);
        log.info("getTransaction method ended");
        return transactionDTO;
    }

    @Override
    public List<TransactionDTO> getTransactionsByCustomerAndMonths(CustomerDTO customerDTO, Month month) {
        log.info("getTransactionsByMonths method started MONTH ::"+month);
        LocalDate startDate = LocalDate.of(2022, month,1);
        LocalDate endDate = LocalDate.of(2022,month, startDate.withDayOfMonth(
                startDate.getMonth().length(startDate.isLeapYear())).getDayOfMonth());
        Customer customer = modelMapper.map(customerDTO,Customer.class);
        List<Transaction> transactions = (List<Transaction>) transactionRepository.findAllByCustomerAndDateBetween(customer,startDate,endDate);
        List<TransactionDTO> transactionDTOS = transactions.stream().map(user -> modelMapper.map(user, TransactionDTO.class)).collect(Collectors.toList());
        log.info("getTransactionsByMonths method completed total transactions :: "+transactions.size());
        return transactionDTOS;

    }
}
