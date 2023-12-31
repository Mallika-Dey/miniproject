package com.example.miniproject.service.impl;

import com.example.miniproject.dto.UserHistoryResponseDTO;
import com.example.miniproject.entity.Role;
import com.example.miniproject.entity.User;
import com.example.miniproject.entity.UserHistory;
import com.example.miniproject.exception.CustomException;
import com.example.miniproject.repository.UserHistoryRepository;
import com.example.miniproject.service.UserHistoryService;
import com.example.miniproject.utils.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {
    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Override
    public List<UserHistoryResponseDTO> viewHistory(int userId, User user) {
        if (!user.getRole().equals(Role.ADMIN) && userId != user.getId()) {
            throw new CustomException("You are not allowed to view this history", HttpStatus.FORBIDDEN);
        }
        List<UserHistory> userHistories = userHistoryRepository.findAllByUserId(userId);

        if (userHistories.isEmpty()) {
            throw new CustomException("User History doesn't exist", HttpStatus.NOT_FOUND);
        }

        return userHistories
                .stream().map(userHistory ->
                        new UserHistoryResponseDTO(userHistory.getBook().getTitle(),
                                userHistory.getBorrowedDate(),
                                userHistory.getDueDate(),
                                userHistory.getReturnDate())
                ).toList();
    }
}
