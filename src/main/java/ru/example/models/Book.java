package ru.example.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int bookId;
    private Integer userId;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Author should be between 2 and 100 characters")
    private String author;
//    @NotEmpty(message = "Year should not be empty")
    private int year;


    public Book(int book_id, int user_id, String name, String author, Integer year) {
        this.bookId = book_id;
        this.userId = user_id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book(int book_id, String name, String author, int year) {
        this.bookId = book_id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book() {
    }


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
