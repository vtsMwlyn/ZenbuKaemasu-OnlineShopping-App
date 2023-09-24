package com.example.uasmp;

public class Transaction {
    private String id, payment;
    private int qty;
    private long total;
    private User buyer;
    private Product item;

    public Transaction(String id, int qty, String payment, User buyer, Product item) {
        this.id = id;
        this.qty = qty;
        this.payment = payment;
        this.buyer = buyer;
        this.item = item;
        this.total = this.qty * this.item.getPrice();
    }

    public String getId(){
        return id;
    }

    public String getPayment(){
        return payment;
    }

    public int getQty(){
        return qty;
    }

    public long getTotal(){
        return total;
    }

    public User getBuyer(){
        return buyer;
    }

    public Product getItem(){
        return item;
    }
}
