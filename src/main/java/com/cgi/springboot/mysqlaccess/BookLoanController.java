package com.cgi.springboot.mysqlaccess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookLoanController {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookLoanRepository bookLoanRepository;
	
	@Value("${error.message.bookloan.book.not.found}")
	private String errorMessageBook;
	
	@Value("${error.message.bookloan.loaner.not.found}")
	private String errorMessageLoaner;
	
	@Value("${error.message.bookloan.nomatch}")
	private String noMatchMessage;
	
	@GetMapping(path="/bookLoanList")
	public String getAllBookLoans(Model model) {
		Iterable<BookLoan> allBookLoans = bookLoanRepository.findAll();
		if (allBookLoans.iterator().hasNext())	model.addAttribute("bookLoans", allBookLoans);
		else									model.addAttribute("bookLoans", new ArrayList<BookLoan>());
		return "bookLoanList";
	}
	
	@PostMapping(path="/bookLoanList")
	public String getMatchingBookLoans(Model model, @RequestParam String searchQuery) {
		model.addAttribute("searchQuery", searchQuery);
		model.addAttribute("bookLoans", new ArrayList<BookLoan>());
		if (!searchQuery.isEmpty()) {
			ArrayList<Book> books = bookRepository.findDistinctBooksByTitleLikeOrAuthorLikeAllIgnoreCaseOrderByTitleAsc("%" + searchQuery + "%", "%" + searchQuery + "%");
			ArrayList<User> users = userRepository.findDistinctUsersByNameLikeOrEmailLikeAllIgnoreCaseOrderByIdAsc("%" + searchQuery + "%", "%" + searchQuery + "%");
			Iterator<BookLoan> iter = bookLoanRepository.findAll().iterator();
			ArrayList<BookLoan> results = new ArrayList<BookLoan>();
			
			while (iter.hasNext()) {
				BookLoan bookLoan = iter.next();
				if (books.contains(bookLoan.getBook()) || users.contains(bookLoan.getLoaner()))
					results.add(bookLoan);
			}
			
			if (results.size() > 0) {
				model.addAttribute("bookLoans", results);
			} else {
				model.addAttribute("noMatchMessage", noMatchMessage);
			}
		} else {
			Iterable<BookLoan> allBookLoans = bookLoanRepository.findAll();
			if (allBookLoans.iterator().hasNext())
				model.addAttribute("bookLoans", allBookLoans);
		}
		return "bookLoanList";
	}
	
	@GetMapping(path="/addBookLoan")
	public String showAddBookLoanPage(Model model) {
		return "addBookLoan";
	}
	
	@PostMapping(path="/addBookLoan")
	public String saveBookLoan(Model model, @RequestParam Integer isbn, @RequestParam Integer loanerId) {
		model.addAttribute("isbn", isbn);
		model.addAttribute("loanerId", loanerId);
		Optional<Book> book = bookRepository.findById(isbn);
		Optional<User> loaner = userRepository.findById(loanerId);
		if (!book.isPresent())
			model.addAttribute("errorMessageBook", errorMessageBook);
		if (!loaner.isPresent())
			model.addAttribute("errorMessageLoaner", errorMessageLoaner);
		if (!book.isPresent() || !loaner.isPresent())
			return "addBookLoan";
		
		BookLoan bookLoan = new BookLoan();
		bookLoan.setBook(book.get());
		bookLoan.setLoaner(loaner.get());
		bookLoanRepository.save(bookLoan);
		return "redirect:/bookLoanList";
	}
}
