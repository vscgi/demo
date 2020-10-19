package com.cgi.springboot.mysqlaccess;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
	ArrayList<Book> findDistinctBooksByTitleLikeOrAuthorLikeAllIgnoreCaseOrderByTitleAsc(String title, String author);
}
