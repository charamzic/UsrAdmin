package com.charamza.usradmin.repositories;

import com.charamza.usradmin.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContainingIgnoreCase(String name);

    List<User> findBySurnameContainingIgnoreCase(String surname);

    List<User> findAllByCreationDateBetween(LocalDate fromDate, LocalDate toDate);
}