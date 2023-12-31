package com.example.miniproject.service;

import com.example.miniproject.dto.UserResponseDTO;
import com.example.miniproject.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    public UserResponseDTO getUserDetails(int userId);
    
    public List<UserResponseDTO> getUserDetails();

    public Set<String> getBookByUserId(int userId, User user);

    public Set<String> getBorrowedByBookByUserId(int userId, User user);

    public UserResponseDTO getUserDetailsByMail(String mail);

}
