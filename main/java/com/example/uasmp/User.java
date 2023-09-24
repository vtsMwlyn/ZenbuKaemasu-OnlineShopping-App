package com.example.uasmp;

public class User {
    protected String id, username, password, name, email, phone, address, bdate;

    User(String id, String username, String password, String name, String email, String phone, String address, String bdate){
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bdate = bdate;
    }

    public String getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }

    public String getAddress(){
        return address;
    }

    public String getBdate(){
        return bdate;
    }

    public boolean validate(String inpUsername, String inpPassword){
        return inpUsername.equals(username) && inpPassword.equals(password);
    }
}
