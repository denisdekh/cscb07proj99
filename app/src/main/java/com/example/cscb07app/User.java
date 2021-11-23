package com.example.cscb07app;

import androidx.annotation.NonNull;

//hello my name is youngjae heo
public class User {
    //CHANGED THE FIELD TO EMAIL FROM USERNAME FOR BETTER CLARITY MIGHT CHANGE BACK TO USERNAME
    //IF PEOPLE ALREADY USED THE FIELD "USERNAME" A LOT IN THEIR CODE
    String email;
    String password;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setUsername(String username) {
        this.email = email;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "The current user's username is:" + email;
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
        return other.email.equals(this.email);
    }
}
    
    



