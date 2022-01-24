package com.charamza.usradmin.services;

import com.charamza.usradmin.models.User;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    void saveUser(User user);

    void deleteUser(Long id);

    User findUserById(Long id);

    List<User> findByName(String name);

    List<User> findAllActive();

    List<User> findAllInactive();

    List<User> findAllByNameBetweenDates(String name, LocalDate fromDate, LocalDate toDate);

    Page<User> findPaginated(int pageNo, int pageSize);

    void changeUserStatus(long id);
}