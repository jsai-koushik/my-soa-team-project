package com.klef.ms.sdp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klef.ms.sdp.client.ProductClient;
import com.klef.ms.sdp.dto.LoginRequest;
import com.klef.ms.sdp.dto.LoginResponse;
import com.klef.ms.sdp.dto.ProductResponse;
import com.klef.ms.sdp.dto.UserResponse;
import com.klef.ms.sdp.entity.User;
import com.klef.ms.sdp.repository.UserRepository;
import com.klef.ms.sdp.security.JwtUtil;

@Service
public class UserServiceimpl implements UserService
{
    @Autowired
    private UserRepository repo;

    @Autowired
    private ProductClient productclient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserResponse saveUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = repo.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers()
    {
        List<User> users = repo.findAll();

        List<UserResponse> responses =new ArrayList<>();

        for (User user : users)
        {
            responses.add(mapToResponse(user));
        }

        return responses;
    }

    @Override
    public UserResponse getUserById(Long id)
    {
        User user = repo.findById(id).orElse(null);

        if (user == null)
        {
            return null;
        }

        return mapToResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, User u)
    {
        Optional<User> optional = repo.findById(id);

        if (optional.isPresent())
        {
            User user = optional.get();

            user.setName(u.getName());
            user.setEmail(u.getEmail());
            user.setContact(u.getContact());

            if (u.getPassword() != null && !u.getPassword().isEmpty())
            {
                user.setPassword(passwordEncoder.encode(u.getPassword()));
            }

            if (u.getRole() != null)
            {
                user.setRole(u.getRole());
            }

            User updatedUser = repo.save(user);

            return mapToResponse(updatedUser);
        }

        return null;
    }

    @Override
    public void deleteUser(Long id)
    {
        boolean status = repo.existsById(id);

        if (status)
        {
            repo.deleteById(id);
            System.out.println("User Deleted Successfully");
        }
        else
        {
            System.out.println("User ID Not Found");
        }
    }

    @Override
    public List<ProductResponse> displayAllProducts()
    {
        return productclient.displayAllProducts();
    }

    @Override
    public LoginResponse userLogin(LoginRequest request)
    {
        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() ->
                    new RuntimeException(
                        "Invalid Email or Password"
                    )
                );

        boolean passwordMatches =
                passwordEncoder.matches(
                    request.getPassword(),
                    user.getPassword()
                );

        if (!passwordMatches)
        {
            throw new RuntimeException("Invalid Email or Password");
        }

        UserDetails userDetails =loadUserByUsername(user.getEmail());

        String token =jwtUtil.generateToken(userDetails);

        LoginResponse response =new LoginResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setContact(user.getContact());
        response.setRole(user.getRole().name());
        response.setToken(token);

        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        User user = repo.findByEmail(username)
                .orElseThrow(() ->
                    new UsernameNotFoundException(
                        "User not found with email: " + username
                    )
                );

        String authority =
                "ROLE_" + user.getRole().name();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(
                    new SimpleGrantedAuthority(authority)
                )
                .build();
    }

    private UserResponse mapToResponse(User user)
    {
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setContact(user.getContact());
        response.setRole(user.getRole().name());

        return response;
    }
}