package com.stock.exam.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String email;

    private int mobile;

    @Override
    public String toString() {
        return String.format("Supplier [id=%s, name=%s, email=%s, mobile=%s]", id, name, email, mobile);
    }

}
