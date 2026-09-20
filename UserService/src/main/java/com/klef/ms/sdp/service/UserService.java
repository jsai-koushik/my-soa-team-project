package com.klef.ms.sdp.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.klef.ms.sdp.dto.LoginRequest;
import com.klef.ms.sdp.dto.LoginResponse;
import com.klef.ms.sdp.dto.ProductResponse;
import com.klef.ms.sdp.dto.UserResponse;
import com.klef.ms.sdp.entity.User;

public interface UserService 
{
    UserResponse saveUser(User user);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, User user);

    void deleteUser(Long id);

    List<ProductResponse> displayAllProducts();

    LoginResponse userLogin(LoginRequest request);

    UserDetails loadUserByUsername(String username);
}