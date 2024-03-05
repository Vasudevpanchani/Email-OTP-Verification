package com.example.emailotpverification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.emailotpverification.dto.RequestDto;
import com.example.emailotpverification.dto.ResponseDto;
import com.example.emailotpverification.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/user-register")
	public ResponseEntity<ResponseDto> registerUser(@RequestBody RequestDto request){
		ResponseDto registerUser = this.userService.registerUser(request);
		return new ResponseEntity<>(registerUser,HttpStatus.CREATED);
	}
	
	@PostMapping("/user-verify")
	public ResponseEntity<?> verifyUser(@RequestParam String email, @RequestParam String otp){
		String verifyUser = this.userService.verifyUser(email, otp);
		return new ResponseEntity<>(verifyUser,HttpStatus.OK);
	}
	

}
