package com.stocks.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"symbol", "name"})})
@Data
@Accessors(chain = true)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private String name;
    private double price;
}
