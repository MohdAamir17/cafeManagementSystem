package com.inn.cafe.Dao;

import com.inn.cafe.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserDao extends JpaRepository<User,Integer> {

    Optional<User> findByEmailIgnoreCase(String email);
}
