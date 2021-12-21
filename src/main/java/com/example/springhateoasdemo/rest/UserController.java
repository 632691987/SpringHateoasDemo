package com.example.springhateoasdemo.rest;

import com.example.springhateoasdemo.dto.User;
import com.example.springhateoasdemo.rest.assemblers.UserAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
@ExposesResourceFor(User.class)
public class UserController {

    private final PagedResourcesAssembler<User> pagedResourcesAssembler;

    private final UserAssembler userAssembler;

    @GetMapping(path = "/search", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedModel<User>> searchUsers() {
        Pageable pageable = PageRequest.of(2, 3);
        int TOTAL_SIZE = 200;
        List<User> userList = List.of(new User("vincent1", 2020L), new User("vincent2", 2021L));
        Page<User> users = new PageImpl<>(userList, pageable, TOTAL_SIZE);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(users, userAssembler));
    }

}
