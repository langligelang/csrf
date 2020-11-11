package com.maoge.csrf.service;

import com.maoge.csrf.beans.User;

public interface LoginService{

    User getUserByName(String userName);
}
