package com.example.tianzhu;

import java.lang.reflect.Constructor;

public class user {
    int id;
    String email,username,image;

    public user(int id,String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;

    }



    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
