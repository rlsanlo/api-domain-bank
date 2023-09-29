package com.rlsanlo.apidomainbank.service.impl;

import com.rlsanlo.apidomainbank.domain.model.User;
import com.rlsanlo.apidomainbank.domain.repository.UserRepository;
import com.rlsanlo.apidomainbank.service.UserService;
import com.rlsanlo.apidomainbank.service.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private static final Long UNCHANGEABLE_USER_ID = 1L;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id){
        return this.userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }


    @Transactional
    public User create(User user) {
        if (userRepository.existsByAccountNumber(user.getAccount().getNumber())){
            throw new IllegalArgumentException("This Account number already exists");
        } else if (userRepository.existsByCardNumber(user.getCard().getNumber())) {
            throw new IllegalArgumentException("This Card number already exists");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User user) {
        this.validateChangeableFields(id, "updated");
        User dbUser = this.findById(id);
        if(!dbUser.getId().equals(user.getId())) {
            throw new IllegalArgumentException("User id does not match.");
        }
        dbUser.setName(user.getName());
        dbUser.setAccount(user.getAccount());
        dbUser.setCard(user.getCard());
        dbUser.setFeattures(user.getFeattures());
        dbUser.setNews(user.getNews());
        return this.userRepository.save(dbUser);
    }

    @Transactional
    public void delete(Long id) {
        this.validateChangeableFields(id, "deleted");
        this.userRepository.deleteById(id);
    }

    private void validateChangeableFields(Long id, String operation) {
       if(UNCHANGEABLE_USER_ID.equals(id)) {
           throw new BusinessException("User with id %id can not be %s.".formatted(UNCHANGEABLE_USER_ID, operation));
       }
    }
}
