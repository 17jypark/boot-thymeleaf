package idu.cs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import idu.cs.domain.User;
import idu.cs.exception.ResourceNotFoundException;
import idu.cs.repository.UserRepository;

@Controller
public class HomeController {
	@Autowired UserRepository userRepo; // Dependency Injection

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("test", "인덕컴소");
		model.addAttribute("pjy", "박준용");
		return "index";
	}
	@GetMapping("/users")
	public String getAllUser(Model model){
		model.addAttribute("users", userRepo.findAll());
		
		return "userlist";
	}
	@GetMapping("/register")
	public String regform(Model model) {		
		return "regform";
	}
	@PostMapping("/create")
	public String createUser(@Valid @RequestBody User user, Model model) {
		userRepo.save(user);
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/users";
	}
	
	@GetMapping("/users/{id}")
	public String getuserById(@PathVariable(value = "id") Long userId, 
			Model model ) throws ResourceNotFoundException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("not found " + userId ));
		model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		model.addAttribute("company", user.getCompany());
		return "user";
	}
	
	@GetMapping("/welcome")
	public String loadWelcome(Model model, 
			Long userId) throws ResourceNotFoundException {
		String userName = userRepo.getOne(userId).getName();
		model.addAttribute("name", userName);
		return "welcome";
	}
}
