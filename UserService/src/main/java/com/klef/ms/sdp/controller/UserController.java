package com.klef.ms.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.ms.sdp.dto.LoginRequest;
import com.klef.ms.sdp.dto.LoginResponse;
import com.klef.ms.sdp.dto.ProductResponse;
import com.klef.ms.sdp.dto.UserResponse;
import com.klef.ms.sdp.entity.User;
import com.klef.ms.sdp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController 
{
    @Autowired
    private UserService service;

    @GetMapping("/")
    public String home() 
    {
        return "User Service Project";
    }

    @PostMapping("/add")
    public ResponseEntity<UserResponse> addUser(@RequestBody User user) 
    {
        UserResponse response = service.saveUser(user);

        return ResponseEntity.status(201)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/displayall")
    public ResponseEntity<List<UserResponse>> displayAllUsers() 
    {
        List<UserResponse> users = service.getAllUsers();

        return ResponseEntity.status(200).body(users);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    @GetMapping("/display")
    public ResponseEntity<?> displayUserById(@RequestParam Long id) 
    {
        UserResponse user =service.getUserById(id);

        if (user != null) 
        {
            return ResponseEntity.status(200).body(user);
        } 
        else 
        {
            return ResponseEntity.status(404).body("User ID Not Found");
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,@RequestBody User user) 
    {
        UserResponse response =service.updateUser(id, user);

        if (response != null) 
        {
            return ResponseEntity.ok(response);
        } 
        else 
        {
            return ResponseEntity.status(404).body("User ID Not Found");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) 
    {
        service.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully.");
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
    @GetMapping("/allproducts")
    public ResponseEntity<List<ProductResponse>> displayAllProducts()
    {
        List<ProductResponse> products = service.displayAllProducts();

        return ResponseEntity.ok(products);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest request)
    {
        LoginResponse response = service.userLogin(request);

        return ResponseEntity.ok(response);
    }
}