package com.maoge.csrf.beans;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private String id;
    private String userName;
    private String password;
    private Set<Role> roles;
    private String phone;
}
