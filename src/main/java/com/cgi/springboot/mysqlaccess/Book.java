package com.cgi.springboot.mysqlaccess;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Book {
	@Id
	private int isbn;
	
	private String title;
	
	private String author;
}
