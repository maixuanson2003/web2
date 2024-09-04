package com.example.web2.Repository;
import com.example.web2.Entity.librarycard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface librarycardRepository extends JpaRepository<librarycard,Integer> {
//    public  librarycard findByActorId(int actorId);
}
