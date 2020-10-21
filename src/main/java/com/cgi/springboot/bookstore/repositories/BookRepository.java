package com.cgi.springboot.bookstore.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.cgi.springboot.bookstore.entities.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
	ArrayList<Book> findDistinctBooksByTitleLikeOrAuthorLikeAllIgnoreCaseOrderByTitleAsc(String title, String author);
}
