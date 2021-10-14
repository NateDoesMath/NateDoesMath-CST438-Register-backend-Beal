package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cst438.domain.AdminRepository;
import com.cst438.domain.StudentRepository;


@Controller
public class LoginController {
	/*
	 * used by React Login front end component to test if user is 
	 * logged in.  
	 *   response 401 indicates user is not logged in
	 *   a redirect response take user to Semester front end page.
	 */
	
	@Autowired
	AdminRepository adminRepo;
	
	@Autowired
	StudentRepository studentRepo;
	
//	@Value("${frontend.post.login.student_url}")
//	String student_redirect_url;
//	
//	@Value("${frontend.post.login.admin_url}")
//	String admin_redirect_url;
	
	@Value("${frontend.post.login.url")
	String redirect_url;
	
	
	private boolean isAdmin(String email) {
		
		if(adminRepo.findByEmail(email) == null) return false;
		
		else return true;
	}
	
	private boolean isStudent(String email) {
		
		if(studentRepo.findByEmail(email) == null) return false;
		else return true;
		
	}
	
	@GetMapping("/user")
	public String user (@AuthenticationPrincipal OAuth2User principal){
		
		// used by front end to display user name.
		String email = principal.getAttribute("email");
		if (isAdmin(email)) return "redirect" + redirect_url; //admin_redirect_url;
		
		else if (isStudent(email)) return "redirect" + redirect_url; //student_redirect_url;
		
		else return "redirect" + redirect_url; //student_redirect_url;
	}
}