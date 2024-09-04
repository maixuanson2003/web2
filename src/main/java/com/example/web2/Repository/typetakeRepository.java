package com.example.web2.Repository;
import com.example.web2.Entity.typetake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface typetakeRepository extends JpaRepository<typetake,Integer> {
}
