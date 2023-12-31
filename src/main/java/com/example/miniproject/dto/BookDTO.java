package com.example.miniproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Author name is required")
    private String author;

    @NotNull(message = "Body is required")
    private String description;

    @NotNull(message = "Image Url is required")
    private String bookCoverUrl;
}
