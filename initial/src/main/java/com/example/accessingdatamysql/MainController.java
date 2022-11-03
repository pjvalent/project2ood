package com.example.accessingdatamysql;

import com.example.accessingdatamysql.model.Book;
import com.example.accessingdatamysql.model.User;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired // This means to get the bean called userRepository
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email) {


        return userService.addUser(name, email);
    }

    @PostMapping(path="/addBook")
    public @ResponseBody String addNewBook(@RequestParam String title, @RequestParam String author,
                                           @RequestParam Double price, @RequestParam Book.Edition edition,
                                           @RequestParam Integer ownerID, @RequestParam Integer isbn){
        return bookService.addBook(title, author, price, edition, ownerID, isbn);
    }

    @PostMapping(path="/sellExistingBook")
    public @ResponseBody String sellBook(@RequestParam Integer id){
        return bookService.sellBook(id);
    }

    @PostMapping(path="/sellBook/isbn")
    public @ResponseBody String sellBookByIsbn(@RequestParam Integer isbn){
        return bookService.sellBookByIsbn(isbn);
    }

    @PostMapping(path="/sellNewBook")
    public @ResponseBody String sellBook(@RequestParam String title, @RequestParam String author,
                                         @RequestParam Double price, @RequestParam Book.Edition edition,
                                         @RequestParam Integer isbn){
        return bookService.sellNewBook(title, author, price, edition, isbn);
    }

    @PostMapping(path="/buyBook")
    public @ResponseBody String buyBook(@RequestParam Integer id, @RequestParam Integer userID){
        return bookService.buyBook(id, userID);
    }


    @GetMapping(path="allBooks")
    public @ResponseBody Iterable<Book> getAllBooks(){
        return bookRepository.findAll();
    }



    @GetMapping(path="/allUsers")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}