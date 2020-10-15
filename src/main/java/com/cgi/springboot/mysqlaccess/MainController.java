package com.cgi.springboot.mysqlaccess;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/mysqldemo")
public class MainController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@PostMapping(path="/adduser")
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return "Saved";
	}
	
	@PostMapping(path="/updateemail")
	public @ResponseBody String updateEmail(@RequestParam Integer id, @RequestParam String newEmail) {
		Optional<User> toBeUpdated = userRepository.findById(id);
		toBeUpdated.ifPresent(user -> {
			user.setEmail(newEmail);
			userRepository.save(user);
		});
		
		if (toBeUpdated.isEmpty())	return "No such user: " + id;
		return "Updated";
	}
	
	@GetMapping(path = "/allusers")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@PostMapping(path="/addbook")
	public @ResponseBody String addNewBook(@RequestParam Integer isbn, @RequestParam String title, @RequestParam String author) {
		Book b = new Book();
		b.setIsbn(isbn);
		b.setTitle(title);
		b.setAuthor(author);
		bookRepository.save(b);
		return "Saved";
	}
	
	@GetMapping(path = "/allbooks")
	public @ResponseBody Iterable<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	@PostMapping(path="/removebook")
	public @ResponseBody String removeBook(@RequestParam Integer isbn) {
		Optional<Book> toBeRemoved = bookRepository.findById(isbn);
		toBeRemoved.ifPresent(book -> {bookRepository.delete(book);});
		if (toBeRemoved.isEmpty())	return "No such book: " + isbn;
		return "Removed";
	}
}
