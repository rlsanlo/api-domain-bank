package com.rlsanlo.apidomainbank.service;

import com.rlsanlo.apidomainbank.domain.model.User;

public interface UserService {
    User findById(Long id);

    User create(User user);
}
