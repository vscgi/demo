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
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Value("${error.message.user}")
	private String errorMessage;
	
	@GetMapping(path="/userList")
	public String getAllUsers(Model model) {
		Iterable<User> allUsers = userRepository.findAll();
		if (allUsers.iterator().hasNext())	model.addAttribute("users", allUsers);
		else								model.addAttribute("users", new ArrayList<User>());
		return "userList";
	}
	
	@GetMapping(path="/addUser")
	public String showAddUserPage(Model model) {
		model.addAttribute("user", new User());
		return "addUser";
	}
	
	@PostMapping(path="/addUser")
	public String saveUser(Model model, @ModelAttribute User user) {
		if (user.getName() == null || user.getName().isEmpty() ||
				user.getEmail() == null || user.getEmail().isEmpty()) {
			model.addAttribute("errorMessage", errorMessage);
			return "addUser";
		}
		userRepository.save(user);
		return "redirect:/userList";
	}
}