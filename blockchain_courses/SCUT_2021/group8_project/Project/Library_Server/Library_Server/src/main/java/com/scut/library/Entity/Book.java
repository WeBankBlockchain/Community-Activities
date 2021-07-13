package com.scut.library.Entity;


import lombok.Data;

@Data
public class Book {
    private String id;
    private String name;
    private String price;
    private String borrower_id;
    private String borrow_time;
    private String return_time;
    private String status;

    public Book(String id, String name, String price, String borrower_id, String borrow_time, String return_time, String status){
        this.id=id;
        this.name=name;
        this.price=price;
        this.borrower_id=borrower_id;
        this.borrow_time=borrow_time;
        this.return_time=return_time;
        this.status=status;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getBorrow_time() {
        return borrow_time;
    }

    public String getName() {
        return name;
    }

    public String getReturn_time() {
        return return_time;
    }

    public String getStatus() {
        return status;
    }
}

