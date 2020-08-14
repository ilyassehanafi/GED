package com.Ged.demoSpringFramework.service;

import com.Ged.demoSpringFramework.Web.dto.UserRegistrationDto;
import com.Ged.demoSpringFramework.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);

}
