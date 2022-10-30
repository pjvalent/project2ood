package com.example.accessingdatamysql.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

    public enum Edition{ // maps to (0 - n-1) in database where n is number of enum values
        FIRST,
        ANNIVERSARY,
        COLLECTOR,
        LARGE_PRINT,
        ELECTRONIC,
        AUDIO,
        OTHER;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rfid;

    private String author;

    private String title;

    private Double price;

    private Edition edition;

    private Boolean isAvailable;

    private int isbn;

    @ManyToOne
    private User owner;


    public Integer getRfid() {
        return rfid;
    }

    public void setRfid(Integer rfid) {
        this.rfid = rfid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
}
