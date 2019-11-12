package com.edu.account.repository;

import com.edu.account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value="select * from users where email = ?1 and password=?2",nativeQuery = true)
    User findUserByEmailAndPassword(String email,String password);
    User findUserById(long id);
}
