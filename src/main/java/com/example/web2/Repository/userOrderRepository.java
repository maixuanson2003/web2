package com.example.web2.Repository;

import com.example.web2.Entity.userorder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userOrderRepository extends JpaRepository<userorder,Integer> {
}
