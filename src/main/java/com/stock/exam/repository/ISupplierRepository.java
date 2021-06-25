package com.stock.exam.repository;

import com.stock.exam.bean.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISupplierRepository extends JpaRepository<Supplier, Integer> {
}