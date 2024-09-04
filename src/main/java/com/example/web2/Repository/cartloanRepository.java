package com.example.web2.Repository;
import com.example.web2.Entity.cartloan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cartloanRepository extends JpaRepository<cartloan,Integer> {
}
