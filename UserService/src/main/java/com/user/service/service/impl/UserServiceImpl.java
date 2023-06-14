package com.user.service.service.impl;
import com.user.service.entities.User;
import com.user.service.exceptions.ResourceNotFoundException;
import com.user.service.repository.UserRepository;
import com.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public User saveUser(User user) {
        User savedUser =userRepo.save(user);
        return savedUser;
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(String userId) {
        return userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }
}
