package com.cgi.springboot.bookstore.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cgi.springboot.bookstore.entities.BookLoan;

public interface BookLoanRepository extends CrudRepository<BookLoan, Integer> {

}
