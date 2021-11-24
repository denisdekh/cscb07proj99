package com.example.cscb07app;

import androidx.annotation.NonNull;

//hello my name is youngjae heo
public class User {
    //CHANGED THE FIELD TO EMAIL FROM USERNAME FOR BETTER CLARITY MIGHT CHANGE BACK TO USERNAME
    //IF PEOPLE ALREADY USED THE FIELD "USERNAME" A LOT IN THEIR CODE
    String username;
    String password;
    //MAKE SURE TO READ
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "The current user's username is:" + username;
    }
    //TWO USER OBJECTS ARE EQUAL IFF EMAILS ARE THE SAME
    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if(obj.getClass() != this.getClass()){
            return false;
        }
        User other = (User)obj;
        return other.username.equals(this.username);
    }
}
    
    



