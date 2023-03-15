package com.mmt.ProductService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
//As we are defining Lombok above there is no need for getter and setter
@AllArgsConstructor
//For constructor
@NoArgsConstructor
//For default constructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //To Increment the product count by 1.
    private long productId;

    @Column(name= "PRODUCT_NAME")
    //To Define the column name in DB
    private String productName;
    @Column(name= "PRICE")
    private long price;
    @Column(name= "QUANTITY")
    private long quantity;
}
