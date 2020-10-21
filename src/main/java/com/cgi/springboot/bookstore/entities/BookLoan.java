package com.cgi.springboot.bookstore.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class BookLoan {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "bookIsbn")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "loanerId")
	private User loaner;
	
	private boolean returned;
}
