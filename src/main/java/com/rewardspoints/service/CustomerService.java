package com.rewardspoints.service;

import com.rewardspoints.dto.CustomerDTO;
import java.util.List;

public interface   CustomerService{
    CustomerDTO saveUpdateCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getCustomers();

    void removeCustomerDetailsById(Long id);

    CustomerDTO getCustomerDetailsById(Long id);


}
