package com.example.accessingdatamysql;

import com.example.accessingdatamysql.model.Book;
import com.example.accessingdatamysql.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Service
public class BookService {


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    static final private  Double DISCOUNT = 0.9;

    public String addBook(@RequestParam String title, @RequestParam String author,
                           @RequestParam Double price, @RequestParam Book.Edition edition,
                           @RequestParam Integer ownerID, @RequestParam Integer isbn) {
        Book b = new Book();
        b.setTitle(title);
        b.setAuthor(author);
        b.setPrice(price);
        b.setEdition(edition);
        b.setIsbn(isbn);

        Date date = new Date(System.currentTimeMillis());
        b.setDateAdded(date);



        User owner = userRepository.findById(ownerID).get(); //get the user with the given id
        if (owner.getName().equalsIgnoreCase("Library")) {
            b.setAvailable(true);
        } else {
            b.setAvailable(false);
        }
        b.setOwner(owner);
        bookRepository.save(b);
        return "Book added";
    }

    public String sellBook(@RequestParam Integer id) {
        try {
            Book b = bookRepository.findById(id).get();
            if (b.isAvailable()) {
                return "Book is already in the system...\n";
            } else {
                b.setOwner(userRepository.findById(1).get()); //set the owner of the book to the library
                b.setAvailable(true);
                Double newPrice = b.getPrice() * DISCOUNT;
                b.setPrice(newPrice);
                bookRepository.save(b);
                return "Book Sold for a price of: $" + b.getPrice() + "!";
            }
        } catch (Exception e) {
            return "Book not in system";

        }
    }

    public String sellNewBook(@RequestParam String title, @RequestParam String author,
                              @RequestParam Double price, @RequestParam Book.Edition edition,
                              @RequestParam Integer isbn) {
        try {
            Book b = new Book();
            b.setTitle(title);
            b.setAuthor(author);
            b.setEdition(edition);
            b.setOwner(userRepository.findById(1).get()); //set the owner of the book to the library
            b.setAvailable(true);
            b.setIsbn(isbn);
            b.setPrice(price);
            bookRepository.save(b);
            return "Book Sold for a price of: $" + price + "!\n";
        }
        catch (Exception e){
            return "Failed to sell book";
        }
    }

    public String sellBookByIsbn(@RequestParam Integer isbn){
        try {
            Book b = bookRepository.findByIsbn(isbn);
            if (b.isAvailable()) {
                return "Book is already in the system...\n";
            } else {
                b.setOwner(userRepository.findById(1).get()); //set the owner of the book to the library
                b.setAvailable(true);
                Double newPrice = b.getPrice() * DISCOUNT;
                b.setPrice(newPrice);
                bookRepository.save(b);
                return "Book Sold for a price of: $" + b.getPrice() + "!";
            }
        } catch (Exception e) {
            return "Book not in system";

        }
    }

    public String buyBook(@RequestParam Integer id, @RequestParam Integer userID){
        try{
            User u = userRepository.findById(userID).get();
        }
        catch (Exception e){
            return "User not in system, please add user before buying book";
        }

        try {
            Book b = bookRepository.findById(id).get();

            if(b.isAvailable()) {
                b.setAvailable(false);
                b.setPrice(b.getPrice() * DISCOUNT); //apply discount at EACH TRANSACTION
                b.setOwner(userRepository.findById(userID).get());
                bookRepository.save(b);
                return "Book successfully purchased for: $" + b.getPrice();

            }
            else{
                return "Book is not available for purchase at this time.......";
            }
        }
        catch(Exception e){
            return "Error buying book.....";
        }
    }

    private Double applyDiscount(Double price, Date date){
        // discount based on number of times sold and how old the book is
        return 0.0;
    }
}
