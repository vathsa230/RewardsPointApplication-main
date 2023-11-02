package com.rewardspoints.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;


@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;

    private String name;

    @OneToMany(targetEntity = Transaction.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID",referencedColumnName = "CID")
    private Set<Transaction> transactions;

    private Long totalRewardPoints = 0l;

}
