package ru.example.models;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import javax.lang.model.element.Name;

public class Person {
    private int userId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "Year should not be empty")
    @Min(value = 1900, message = "Year should be greater than 1900")
    private int yearOfBirth;

    public Person(int id, String name, int yearOfBirth) {
        this.userId = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public Person() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
