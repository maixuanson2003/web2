package com.example.web2.Repository;
import com.example.web2.Entity.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface bookRepository extends JpaRepository<book,Integer> {
}
