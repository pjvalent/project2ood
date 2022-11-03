package com.example.accessingdatamysql.model;

import javax.persistence.*;
import java.sql.Date;


@Entity
public class Book {

    public enum Edition{
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

    private Date dateAdded; //date added, no time included

    private int numTimesSold;


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

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getNumTimesSold() {
        return numTimesSold;
    }

    public void setNumTimesSold(int numTimesSold) {
        this.numTimesSold = numTimesSold;
    }
}
