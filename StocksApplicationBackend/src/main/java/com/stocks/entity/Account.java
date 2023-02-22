package com.stocks.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;

@Entity
@Table
@Data
@ToString
@Accessors(chain = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amountOfMoney;
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyJoinColumn(name = "stock_id")
    @Column(name = "amount")
    private Map<Stock, Integer> stocks;
}
