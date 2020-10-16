package com.cgi.springboot.mysqlaccess;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	@Value("${error.message.book}")
	private String errorMessage;
	
	@GetMapping(path="/bookList")
	public String getAllBooks(Model model) {
		Iterable<Book> allBooks = bookRepository.findAll();
		if (allBooks.iterator().hasNext())	model.addAttribute("books", allBooks);
		else								model.addAttribute("books", new ArrayList<Book>());
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
