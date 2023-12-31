package com.example.miniproject.utils;

public class AppConstant {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String SIGN_IN = "/user/login";
    public static final String SIGN_UP = "/user/register";
    public static final String USER_DETAILS = "users/get";
    public static final String ALL_USER_DETAILS = "users/get/all";
    public static final String USER_OWNED_BOOK = "users/{userId}/books";
    public static final String USER_BORROWED_BOOK = "users/{userId}/borrowed-books";
    public static final String CREATE_BOOK = "books/create";
    public static final String UPDATE_BOOK = "books/update/{id}";
    public static final String DELETE_BOOK = "books/delete/{id}";
    public static final String GET_BOOK = "books/all";
    public static final String BORROW_BOOK = "books/{bookId}/borrow";
    public static final String RETURN_BOOK = "books/{bookId}/return";
    public static final String RESERVE_BOOK = "books/{bookId}/reserve";
    public static final String CANCEL_RESERVATION = "books/{bookId}/cancel";
    public static final String GIVE_REVIEW = "books/{bookId}/reviews/create";
    public static final String UPDATE_REVIEW = "books/reviews/{reviewId}/update";
    public static final String DELETE_REVIEW = "books/reviews/{reviewId}/delete";
    public static final String GET_REVIEW = "books/{bookId}/reviews";
    public static final String GET_USER_HISTORY = "users/{userId}/history";
    public static final String GET_PERSONAL_HISTORY = "users/history";

}
