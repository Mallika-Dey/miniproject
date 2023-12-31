package com.example.miniproject.service;

public interface ReservedBookService {
    public void bookReserve(int bookId, int userId);

    public void cancelReservation(int bookId, int userId);
}
