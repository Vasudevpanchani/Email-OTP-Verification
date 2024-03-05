package com.example.emailotpverification.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.emailotpverification.dto.RequestDto;
import com.example.emailotpverification.dto.ResponseDto;
import com.example.emailotpverification.model.User;
import com.example.emailotpverification.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmailService emailService;

	@Override
	public ResponseDto registerUser(RequestDto request) {
		ResponseDto responseDto = new ResponseDto();
		
		User existingUser = this.userRepo.findByEmail(request.getEmail());
		if (existingUser != null) {
			responseDto.setMessage("User already registered!!");
		}else {
			Random random = new Random();
			String otp = String.format("%06d", random.nextInt(100000));
			User newUser = new User();
			newUser.setUsername(request.getUsername());
			newUser.setEmail(request.getEmail());
			newUser.setOtp(otp);
			newUser.setVerified(false);
			
			User saveUser = this.userRepo.save(newUser);
			
			//Email Send
			
			String subject = "Email Verification";
			
			String body = "Your verification OTP is " + saveUser.getOtp();
			
			this.emailService.sendEmail(saveUser.getEmail(), subject, body);
			
			responseDto.setUser_id(saveUser.getUser_id());
			responseDto.setUsername(saveUser.getUsername());
			responseDto.setEmail(saveUser.getEmail());
			responseDto.setMessage("OTP sent successfully...");
		}
		
		return responseDto;
	}

	@Override
	public String verifyUser(String email, String otp) {
		String response = "";
		User user = this.userRepo.findByEmail(email);

		if (user != null && user.isVerified()) {
			response = "User is already verified!!";
			emailService.sendEmail(user.getEmail(), "Email Verification", response);
		}else if (otp.equals(user.getOtp())) {
			response = "User verified successfully...";
			user.setVerified(true);
			this.userRepo.save(user);
			emailService.sendEmail(user.getEmail(), "Email Verification", response);
		}else {
			response = "User not verify!!";
			emailService.sendEmail(user.getEmail(), "Email Verification", response);
		}
		return response;
	}

}
