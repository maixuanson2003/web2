package com.example.web2.Repository;
import  com.example.web2.Entity.preview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface previewRepository extends JpaRepository<preview,Integer> {
}
