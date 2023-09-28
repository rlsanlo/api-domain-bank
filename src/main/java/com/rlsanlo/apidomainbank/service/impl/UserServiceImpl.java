package com.rlsanlo.apidomainbank.service.impl;

import com.rlsanlo.apidomainbank.domain.model.User;
import com.rlsanlo.apidomainbank.domain.repository.UserRepository;
import com.rlsanlo.apidomainbank.service.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User create(User user) {
        if (user.getId() != null && userRepository.existsById(user.getId())){
            throw new IllegalArgumentException("User already exists");
        }
        return userRepository.save(user);
    }
}