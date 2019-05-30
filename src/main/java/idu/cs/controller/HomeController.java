package idu.cs.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import idu.cs.domain.UserEntity;
import idu.cs.exception.ResourceNotFoundException;
import idu.cs.repository.UserRepository;

@Controller
//애노테이션(Annotation) : 컴파일러에게 설정 내용이나 상태를 알려주는 목적, 적용버무이가 클래스 내부로 한정
public class HomeController {
	@Autowired UserRepository userRepo; // Dependency Injection

	/*@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("test", "인덕컴소");
		model.addAttribute("pjy", "박준용");
		return "index";
	}*/
	@GetMapping("/")
	public String welcome(Model model) {		
		return "index";
	}
	
	@GetMapping("/login")
	public String loginForm() {		
		return "login";
	}
	
	@PostMapping("/login")
	// 실제로 로그인 처리, user : 입력한 내용에 대한 객체
	// sessionUser : 리파지터리로 부터 가져온 내용의 객체
	public String loginUser(@Valid UserEntity user, HttpSession session) {
		System.out.println("login process : ");
		UserEntity sessionUser = userRepo.findByUserId(user.getUserId());
		if(sessionUser == null) {
			System.out.println("id error : ");
			return "redirect:/login";
		}
		if(!sessionUser.getUserPw().equals(user.getUserPw())) {
			System.out.println("pw error : ");
			return "redirect:/login";
		}
		session.setAttribute("user", sessionUser);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logoutUser(HttpSession session) {
		//session.invalidate();
		session.removeAttribute("user");
		return "redirect:/";
	}
	
	@GetMapping("/users")
	public String getAllUser(Model model){
		model.addAttribute("users", userRepo.findAll());
		
		return "userlist";
	}
	
	@GetMapping("/users/byname") // byname?name=***, ***값이 name 변수
	public String getUsersByName(@Param(value = "name") String name, Model model){
		List<UserEntity> users = userRepo.findByName(name);
		model.addAttribute("users", users);
		
		return "userlist";
	}
	
	@GetMapping("/users/nameasc") // byname?name=***, ***값이 name 변수
	public String getUsersByNameAsc(@Param(value = "name") String name, Model model){
		List<UserEntity> users = userRepo.findByNameOrderByIdAsc(name);
		model.addAttribute("users", users);
		
		return "userlist";
	}
	
	@GetMapping("/users/bycom") // byname?name=***, ***값이 name 변수
	public String getUsersByCompany(@Param(value = "company") String company, Model model){
		List<UserEntity> users = userRepo.findByCompany(company);
		model.addAttribute("users", users);
		
		return "userlist";
	}
	
	@GetMapping("/users/{id}")
	public String getUserById(@PathVariable(value = "id") Long userId, 
			Model model ) throws ResourceNotFoundException {
		UserEntity user = userRepo.findById(userId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("not found " + userId ));
		model.addAttribute("user", user);
		
		return "info";
	}
	
	@GetMapping("/register")
	public String regform(Model model) {		
		return "register";
	}

	@PostMapping("/users")
	public String createUser(@Valid UserEntity user, Model model) {
		userRepo.save(user);
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/";
	}
	@PutMapping("/users/{id}")
	//@RequestMapping(value=""/users/{id}" method=RequestMethod.Update)
	public String updateUserById(@PathVariable(value = "id") Long userId, 
			@Valid UserEntity userDetails, Model model ) throws ResourceNotFoundException {
		// userDetails 폼을 통해 전송된 객체, user는 id로 jpa를 통해서 가져온 객체
		UserEntity user = userRepo.findById(userId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("not found " + userId ));
		user.setName(userDetails.getName());
		user.setCompany(userDetails.getCompany());
		UserEntity userUpdate = userRepo.save(user); // 객체 삭제 -> jpa : record 삭제로 적용
		
		//model.addAttribute("user", userUpdate);
		return "redirect:/users"; //
		//return ResponseEntity.ok(userUpdate);
	}
	@DeleteMapping("/users/{id}")
	//@RequestMapping(value=""/users/{id}" method=RequestMethod.DELETE)
	public String deleteUserById(@PathVariable(value = "id") Long userId, 
			Model model ) throws ResourceNotFoundException {
		UserEntity user = userRepo.findById(userId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("not found " + userId ));
		userRepo.delete(user); // 객체 삭제 -> jpa : record 삭제로 적용
		model.addAttribute("name", user.getName());
		return "disjoin";
	}
	
	/*
	@GetMapping("/welcome")
	public String loadWelcome(Model model, 
			Long userId) throws ResourceNotFoundException {
		String userName = userRepo.getOne(userId).getName();
		model.addAttribute("name", userName);
		return "welcome";
	}
	*/
}
