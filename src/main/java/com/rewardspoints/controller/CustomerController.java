package com.rewardspoints.controller;

import com.rewardspoints.dto.CustomerDTO;
import com.rewardspoints.reponse.ResponseHandler;
import com.rewardspoints.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("customers")
    public ResponseEntity<Object> getCustomers(){
        log.info("getCustomers started");
        try {
            List<CustomerDTO> customers = customerService.getCustomers();
            log.info("getCustomers customers list size ::"+customers.size());
            return ResponseHandler.generateResponse("success", HttpStatus.OK, customers);
        }catch (Exception e) {
            log.error("Exception occured in getCustomer ::"+ e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR,null) ;
        }
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<Object> getCustomer(@PathVariable Long id){
        log.info("getCustomer started id ::"+id);
        try {
            CustomerDTO customerDTO = customerService.getCustomerDetailsById(id);
            log.info("getCustomer retrive successfully with id ::"+id);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, customerDTO);
        }catch (Exception e) {
            log.error("Exception occured in getCustomer ::"+ e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR,null) ;
        }
    }

    @PostMapping("customers")
    public ResponseEntity<Object> addCustomer(@RequestBody CustomerDTO customerDTO){
        log.info("addCustomer started");
        try {
            CustomerDTO savedCustomerDTO = customerService.saveUpdateCustomer(customerDTO);
            log.info("addCustomer customer added successfully with id ::"+savedCustomerDTO.getCid());
            return ResponseHandler.generateResponse("Customer Signup Done", HttpStatus.OK, savedCustomerDTO);
        }catch (Exception e) {
            log.error("Exception occured in addCustomer ::"+ e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY,null) ;
        }
    }

    @PutMapping("customers")
    public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDTO customerDTO){
        log.info("updateCustomer started customer id ::"+customerDTO.getCid());
        try {
            CustomerDTO updateCustomer = customerService.saveUpdateCustomer(customerDTO);
            log.info("updateCustomer customer updated successfully with id ::"+updateCustomer.getCid());
            return ResponseHandler.generateResponse("", HttpStatus.OK, updateCustomer);
        }catch (Exception e) {
            log.error("Exception occured in updateCustomer ::"+ e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY,null) ;
        }
    }

    @DeleteMapping("customers/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id){
        log.info("deleteCustomer started customer id ::"+id);
        try {
            customerService.removeCustomerDetailsById(id);
            log.info("deleteCustomer customer deleted successfully with id ::"+id);
            return ResponseHandler.generateResponse("Customer Deleted Successfully", HttpStatus.OK, null);
        }catch (Exception e) {
            log.error("Exception occured in deleteCustomer ::"+ e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR,null) ;
        }
    }
}
