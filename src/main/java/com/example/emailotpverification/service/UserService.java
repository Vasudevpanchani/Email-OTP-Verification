package com.example.emailotpverification.service;

import com.example.emailotpverification.dto.RequestDto;
import com.example.emailotpverification.dto.ResponseDto;

public interface UserService {
	
	public ResponseDto registerUser(RequestDto request);
	
	public String verifyUser(String email, String otp);

}
