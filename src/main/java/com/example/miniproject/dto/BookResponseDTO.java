package com.example.miniproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDTO {
    private int id;
    private String title;
    private String author;
    private String description;
    private String bookCoverUrl;
    private Double rating;
    private boolean available;
}
