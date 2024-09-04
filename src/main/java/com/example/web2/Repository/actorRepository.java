package com.example.web2.Repository;

import com.example.web2.Entity.actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface actorRepository extends JpaRepository<actor,Integer> {
    public actor findByUsername(String username);
}
