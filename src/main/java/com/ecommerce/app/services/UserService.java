package com.ecommerce.app.services;

import com.ecommerce.app.exceptions.UserNotFoundException;
import com.ecommerce.app.models.User;
import com.ecommerce.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User Not FOund With id = "+userId)
        );
    }
}
