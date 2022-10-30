package com.example.accessingdatamysql;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.accessingdatamysql.model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String addUser(@RequestParam String name, @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        n.setBalance(5);
        userRepository.save(n);
        return "Saved";
    }
}
