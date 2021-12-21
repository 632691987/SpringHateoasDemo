package com.example.springhateoasdemo.rest.assemblers;

import com.example.springhateoasdemo.dto.User;
import com.example.springhateoasdemo.rest.UserController;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler extends AbstractResourceAssembler<User, User>{

    public UserAssembler() {
        super(UserController.class, User.class);
    }

    @Override
    public User toModel(User user) {
        return user;
    }
}
