package com.cgi.springboot.mysqlaccess;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
	
}
