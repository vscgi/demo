package com.cgi.springboot.mysqlaccess;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Book {
	@Id
	private int isbn;
	
	private String title;
	
	private String author;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "book")
	private Set<BookLoan> loans;
}
