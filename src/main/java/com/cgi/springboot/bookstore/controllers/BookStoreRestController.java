package com.cgi.springboot.bookstore.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cgi.springboot.bookstore.entities.Book;
import com.cgi.springboot.bookstore.entities.User;
import com.cgi.springboot.bookstore.repositories.BookRepository;
import com.cgi.springboot.bookstore.repositories.UserRepository;

@Controller
@RequestMapping(path = "/bookstore")
public class BookStoreRestController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@PostMapping(path = "/users")
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return "Saved";
	}
	
	@GetMapping(path = "/users")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@PostMapping(path = "/updateemail")
	public @ResponseBody String updateEmail(@RequestParam Integer id, @RequestParam String newEmail) {
		Optional<User> toBeUpdated = userRepository.findById(id);
		toBeUpdated.ifPresent(user -> {
			user.setEmail(newEmail);
			userRepository.save(user);
		});
		
		if (toBeUpdated.isEmpty())	return "No such user: " + id;
		return "Updated";
	}
	
	@PostMapping(path="/books")
	public @ResponseBody String addNewBook(@RequestParam Integer isbn, @RequestParam String title, @RequestParam String author) {
		Book b = new Book();
		b.setIsbn(isbn);
		b.setTitle(title);
		b.setAuthor(author);
		bookRepository.save(b);
		return "Saved";
	}
	
	@GetMapping(path = "/books")
	public @ResponseBody Iterable<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	@DeleteMapping(path="/books")
	public @ResponseBody String removeBook(@RequestParam Integer isbn) {
		Optional<Book> toBeRemoved = bookRepository.findById(isbn);
		toBeRemoved.ifPresent(book -> {bookRepository.delete(book);});
		if (toBeRemoved.isEmpty())	return "No such book: " + isbn;
		return "Removed";
	}
}
