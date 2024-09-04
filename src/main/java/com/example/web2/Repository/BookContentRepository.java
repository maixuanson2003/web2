package com.example.web2.Repository;
import com.example.web2.Entity.BookContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookContentRepository extends JpaRepository<BookContent,Integer> {
}
