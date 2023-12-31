package com.example.miniproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateDto {
    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Author name is required")
    private String author;

    @NotNull(message = "Description is required")
    private String description;

    private String bookCoverUrl;
}
