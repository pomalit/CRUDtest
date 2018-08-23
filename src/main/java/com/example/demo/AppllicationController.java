package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppllicationController {
	
	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping("/")
	public String showMain(Model model){
		return "main";
	}
	
	@RequestMapping("/login")
	public String showLogin(Model model){
		return "login";
	}
	
	@RequestMapping("/registration")
	public String showRegistration(Model model){
		model.addAttribute("registration", new Registration());
		return "registration";
	}
	
	@PostMapping("/registration")
	public String processRegister(Model model, @ModelAttribute Registration registration) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("USER"));
		String encodedPassword = bCryptPasswordEncoder.encode(registration.getPassword());
		User user = new User(registration.getUsername(), encodedPassword, authorities);
		jdbcUserDetailsManager.createUser(user);
		return "redirect:/login";
	}

	@RequestMapping("/user/{username}")
	public String showUserpage(Model model, @PathVariable String username){
		if(jdbcUserDetailsManager.userExists(username)) {
			model.addAttribute("messages", messageRepository.findByUser(username));
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName();
			if(name.equals(username)) {
				model.addAttribute("allowed", true);
				model.addAttribute("message", new Message());
			}else {
				model.addAttribute("allowed", false);
			}
			return "user";
		}else{
			return "/404";
		}
	}
	
	@PostMapping("/user/{username}/add")
	public String addMessage(Model model, @PathVariable String username, @ModelAttribute Message message) {
		message.setUser(username);
		messageRepository.addNewMessage(message);
		return "redirect:/user/{username}";
	}
	
	@RequestMapping("/user/{username}/delete/{messageID}")
	public String deleteMessage(Model model, @PathVariable String username, @PathVariable int messageID) {
		messageRepository.deleteMessageById(messageID);
		return "redirect:/user/{username}";
	}

}
