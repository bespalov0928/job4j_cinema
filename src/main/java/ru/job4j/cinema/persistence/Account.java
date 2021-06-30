package ru.job4j.cinema.persistence;

public class Account {
    private int id;
    private String username;
    private String email;
    private String phone;

    public Account(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
