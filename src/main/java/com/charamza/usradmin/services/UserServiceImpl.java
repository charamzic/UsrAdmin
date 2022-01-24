package com.charamza.usradmin.services;

import com.charamza.usradmin.models.User;
import com.charamza.usradmin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User realUser = user.get();
            userRepository.delete(realUser);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.orElse(null);
    }

    @Override
    public List<User> findByName(String name) {
        // calls helper method which combines both name and surname
        return nameAndSurnameSearch(name);
    }

    @Override
    public List<User> findAllActive() {
        // find all users and then remove those who are not active
        List<User> users = userRepository.findAll();
        users.removeIf(user -> !user.isActive());
        return users;
    }

    @Override
    public List<User> findAllInactive() {
        // find all users and then remove those who are inactive
        List<User> users = userRepository.findAll();
        users.removeIf(User::isActive);
        return users;
    }

    @Override
    public List<User> findAllByNameBetweenDates(String name, LocalDate fromDate, LocalDate toDate) {
        // find all by name and surname...
        List<User> userByName = nameAndSurnameSearch(name);
        // find all within the date range...
        List<User> usersBetweenDateRange = dateRangeSearch(fromDate, toDate);
        List<User> match = new ArrayList<>();

        // and check if there are any results matches both conditions
        for (User user : userByName) {
            if (usersBetweenDateRange.contains(user)) {
                match.add(user);
            }
        }

        return match;
    }

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return userRepository.findAll(pageable);
    }

    @Override
    public void changeUserStatus(long id) {
        Optional<User> dbUser = userRepository.findById(id);

        if (dbUser.isPresent()) {
            User realUser = dbUser.get();
            // if user is active, set him inactive and vise versa
            realUser.setActive(!realUser.isActive());
            userRepository.save(realUser);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<User> nameAndSurnameSearch(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        List<User> usersBySurname = userRepository.findBySurnameContainingIgnoreCase(name);
        List<User> joined = Stream.concat(users.stream(), usersBySurname.stream())
                .collect(Collectors.toList());

        if (joined.isEmpty()) {
            return Collections.emptyList();
        } else {
            return joined;
        }
    }

    public List<User> dateRangeSearch(LocalDate fromDate, LocalDate toDate) {
        List<User> usersFound = userRepository.findAllByCreationDateBetween(fromDate, toDate);
        if (!usersFound.isEmpty()) {
            return Collections.emptyList();
        }
        return usersFound;
    }
}