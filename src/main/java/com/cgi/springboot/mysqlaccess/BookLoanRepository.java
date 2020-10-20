package com.cgi.springboot.mysqlaccess;

import org.springframework.data.repository.CrudRepository;

public interface BookLoanRepository extends CrudRepository<BookLoan, Integer> {

}
