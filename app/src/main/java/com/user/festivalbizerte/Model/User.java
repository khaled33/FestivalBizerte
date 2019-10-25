package com.user.festivalbizerte.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String password;
    private int role;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
