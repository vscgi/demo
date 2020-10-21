package com.cgi.springboot.bookstore.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cgi.springboot.bookstore.entities.Book;
import com.cgi.springboot.bookstore.repositories.BookRepository;

@Controller
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	@Value("${error.message.book.requiredfields}")
	private String errorMessage;
	
	@Value("${error.message.book.nomatch}")
	private String noMatchMessage;
	
	@GetMapping(path="/bookList")
	public String getAllBooks(Model model) {
		Iterable<Book> allBooks = bookRepository.findAll();
		if (allBooks.iterator().hasNext())	model.addAttribute("books", allBooks);
		else								model.addAttribute("books", new ArrayList<Book>());
		return "bookList";
	}
	
	@PostMapping(path="/bookList")
	public String getMatchingBooks(Model model, @RequestParam String searchQuery) {
		model.addAttribute("searchQuery", searchQuery);
		model.addAttribute("books", new ArrayList<Book>());
		if (!searchQuery.isEmpty()) {
			ArrayList<Book> results = bookRepository.findDistinctBooksByTitleLikeOrAuthorLikeAllIgnoreCaseOrderByTitleAsc("%" + searchQuery + "%", "%" + searchQuery + "%");
			if (results.size() > 0) {
				model.addAttribute("books", results);
			} else {
				model.addAttribute("noMatchMessage", noMatchMessage);
			}
		} else {
			Iterable<Book> allBooks = bookRepository.findAll();
			if (allBooks.iterator().hasNext())	model.addAttribute("books", allBooks);
		}
		return "bookList";
	}
	
	@GetMapping(path="/addBook")
	public String showAddBookPage(Model model) {
		model.addAttribute("book", new Book());
		return "addBook";
	}
	
	@PostMapping(path="/addBook")
	public String saveBook(Model model, @ModelAttribute Book book) {
		if (book.getIsbn() == 0 ||
				book.getTitle() == null || book.getTitle().isEmpty() ||
				book.getAuthor() == null || book.getAuthor().isEmpty()) {
			model.addAttribute("errorMessage", errorMessage);
			return "addBook";
		}
		bookRepository.save(book);
		return "redirect:/bookList";
	}
}
