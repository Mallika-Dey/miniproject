package com.example.miniproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHistoryResponseDTO {
    private String bookName;
    private Date borrowedDate;
    private Date dueDate;
    private Date returnDate;
}
