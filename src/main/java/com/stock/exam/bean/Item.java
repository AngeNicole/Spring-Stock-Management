package com.stock.exam.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Item {

    @Id
    @GeneratedValue
    private int Id;
    private String name;
    private int itemCode;

    @Enumerated
    private String status;

    private int price;

    @OneToOne
    private Supplier supplier;

//    (fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "id")

}
