package com.demo.service;

import java.util.List;

import com.demo.dto.UserDto;
import com.demo.entity.User;


public interface UserService {
	
	void saveUser(UserDto userDto);
	
	void updateUser(UserDto userDto, String email);

    User findByEmail(String email);
    
    UserDto findByUsername(String email);
    
    void removeUserRoles(User user);

    List<UserDto> findAllUsers();

}
