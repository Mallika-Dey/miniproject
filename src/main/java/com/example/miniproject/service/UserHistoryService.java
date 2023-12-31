package com.example.miniproject.service;

import com.example.miniproject.dto.UserHistoryResponseDTO;
import com.example.miniproject.entity.User;

import java.util.List;

public interface UserHistoryService {
    public List<UserHistoryResponseDTO> viewHistory(int userId, User user);
}
