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

    static final private Double TIME_WEIGHT = 0.5; //weight for the time

    static final private Double NUM_SOLD_WEIGHT = 0.5; //weight for the number of transactions

    static final private Double MAX_DISCOUNT = 90.0; //max discount

    public String addBook(@RequestParam String title, @RequestParam String author,
                           @RequestParam Double price, @RequestParam Book.Edition edition,
                           @RequestParam Integer ownerID, @RequestParam Integer isbn) {
        Book b = new Book();
        b.setTitle(title);
        b.setAuthor(author);
        b.setPrice(price);
        b.setEdition(edition);
        b.setIsbn(isbn);
        b.setNumTimesSold(0); //new book gets 0 times sold

        Date date = new Date(System.currentTimeMillis());
        b.setDateAdded(date);

        double discount = applyDiscount(price, date, 1);


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
                Double newPrice = applyDiscount(b.getPrice(), b.getDateAdded(), b.getNumTimesSold());
//                Double newPrice = b.getPrice() * discount;
                b.setPrice(newPrice);
                b.setNumTimesSold(b.getNumTimesSold() + 1);
                bookRepository.save(b);
                return "Book Sold for a price of: $" + newPrice + "!";
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
            b.setNumTimesSold(1); //new book gets sold 1 time to start
            b.setPrice(price); //don't discount new books
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
                b.setNumTimesSold(b.getNumTimesSold() + 1); // increment number of times sold
                Double newPrice = applyDiscount(b.getPrice(), b.getDateAdded(), b.getNumTimesSold());
                b.setPrice(newPrice);
                bookRepository.save(b);
                return "Book Sold for a price of: $" + newPrice + "!";
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
                Double newPrice = applyDiscount(b.getPrice(), b.getDateAdded(), b.getNumTimesSold());
                b.setPrice(newPrice);
                b.setNumTimesSold(b.getNumTimesSold() + 1); // increment number of times sold
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

    private Double applyDiscount(Double price, Date date, int numTimesSold){

        Date currentDate = new Date(System.currentTimeMillis()); // discount form current date to date added

        assert(date.compareTo(currentDate) <= 0); //date added must be before or equal to current date

        long diff = currentDate.getTime() - date.getTime(); // difference in milliseconds

        long diffDays = diff / (24 * 60 * 60 * 1000); // difference in days

        // discount based on time and number of times sold
        // 365 and 100 are arbitrary numbers change as desired, or don't
        Double discount = ((TIME_WEIGHT * (diffDays/365.0)) + (NUM_SOLD_WEIGHT * (numTimesSold/100.0))) * 100;

        System.out.println("Discount: " + discount);

        if(discount > MAX_DISCOUNT){
            discount = MAX_DISCOUNT;
        }
        else if(discount < 0){
            discount = 0.0; // something's fucked up if it's less than 0, make sure to try-catch this code at some point
        } else if (discount.isNaN()) {
            discount = 0.0; // ignore NAN for now, fix this with try-catch later
        }

        // apply the discount to the original price

        Double newPrice = price * (1 - (discount/100.0));

        return newPrice;

    }


}
