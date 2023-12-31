package com.example.miniproject.service.impl;

import com.example.miniproject.dto.UserResponseDTO;
import com.example.miniproject.entity.Role;
import com.example.miniproject.entity.User;
import com.example.miniproject.exception.CustomException;
import com.example.miniproject.repository.UserRepository;
import com.example.miniproject.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserResponseDTO getUserDetails(int userId) {
        User user = getUserById(userId);
        return modelMapper.map(user, UserResponseDTO.class);
    }


    public UserResponseDTO getUserDetailsByMail(String mail) {
        User user = getUserByMail(mail);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public Set<String> getBookByUserId(int userId, User user) {
        if (!user.getRole().equals(Role.ADMIN) && userId != user.getId()) {
            throw new CustomException("You are not allowed to view this history", HttpStatus.FORBIDDEN);
        }
        return userRepository.findById(userId).get()
                .getUserHistories().stream().map(userHistory ->
                        userHistory.getBook().getTitle()
                ).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getBorrowedByBookByUserId(int userId, User user) {
        if (!user.getRole().equals(Role.ADMIN) && userId != user.getId()) {
            throw new CustomException("You are not allowed to view the borrowed history"
                    , HttpStatus.FORBIDDEN);
        }
        return userRepository.findById(userId).get()
                .getBorrowedBook().stream()
                .map(borrowedBook -> borrowedBook.getBook().getTitle())
                .collect(Collectors.toSet());
    }

    private User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User doesn't found with id: " + userId
                        , HttpStatus.NOT_FOUND));
    }

    private User getUserByMail(String mail) {
        return userRepository.findByEmail(mail)
                .orElseThrow(() -> new CustomException("User doesn't found with id: " + mail
                        , HttpStatus.NOT_FOUND));
    }


	@Override
	public List<UserResponseDTO> getUserDetails() {
		return userRepository.findAll()
				.stream()
				.map(user -> modelMapper.map(user, UserResponseDTO.class))
				.toList();
	}
}
