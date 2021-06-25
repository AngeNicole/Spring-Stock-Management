package com.stock.exam.repository;


import com.stock.exam.bean.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemRepository extends JpaRepository<Item, Integer> {

}