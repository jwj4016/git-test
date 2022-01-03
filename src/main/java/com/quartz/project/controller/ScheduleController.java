package com.quartz.project.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequiredArgsConstructor
//@ResponseBody
public class ScheduleController {
	
//	private final UserService userSerivce;
	
//	@PostMapping("/signup")
//	public void signUpUser(@RequestBody @Valid UserForm userForm) throws Exception {
//		userSerivce.signUpUser(userForm);
//		
//	}
	
	@GetMapping("/api/hello")
	public String hello() {
		return "hello";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

}
