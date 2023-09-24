package com.example.uasmp;

import java.util.ArrayList;

public class Customer extends User{
    private ArrayList<Transaction> history = new ArrayList<>();

    public Customer(String id, String username, String password, String name, String email, String phone, String address, String bdate){
        super(id, username, password, name, email, phone, address, bdate);
    }

    public void setHistory(ArrayList<Transaction> tlist){
        this.history = tlist;
    }
}
