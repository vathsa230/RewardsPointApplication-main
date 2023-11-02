package com.rewardspoints.repository;

import com.rewardspoints.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer,Long> {


}
