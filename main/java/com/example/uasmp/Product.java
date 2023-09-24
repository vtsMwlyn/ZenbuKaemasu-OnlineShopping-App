package com.example.uasmp;

public class Product {
    private String id, name;
    private int qty;
    private long price;
    private Outlet shop;

    public Product(String id, String name, int qty, long price, Outlet shop){
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.shop = shop;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getQty(){
        return qty;
    }

    public long getPrice(){
        return price;
    }

    public Outlet getShop(){
        return shop;
    }

    public void setQty(int qty){
        this.qty = qty;
    }
}
