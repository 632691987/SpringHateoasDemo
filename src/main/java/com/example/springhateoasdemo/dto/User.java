package com.example.springhateoasdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User extends RepresentationModel<User> {

    private String name;

    private Long salary;

}
