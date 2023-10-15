package me.wanpin.springboot.service;

import me.wanpin.springboot.dto.RegistrationDto;
import me.wanpin.springboot.entity.User;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    User findByEmail(String email);
}
