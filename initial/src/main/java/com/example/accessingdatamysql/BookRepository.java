package com.example.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

import com.example.accessingdatamysql.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

}
