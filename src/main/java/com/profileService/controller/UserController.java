package com.profileService.controller;

import com.profileService.dto.ChangePassword;
import com.profileService.dto.ChangePasswordForm;
import com.profileService.dto.VerifyPasswordForm;
import com.profileService.model.AuthenticationToken;
import com.profileService.model.Otp;
import com.profileService.model.User;
import com.profileService.repository.OtpRepository;
import com.profileService.repository.UserRepository;
import com.profileService.service.OtpService;
import com.profileService.service.SendMailServiceImpl;
import com.profileService.service.UserService;
import com.profileService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
public class UserController {
    @Autowired
    RestTemplate restTemplate;
@Autowired
    UserService userService;
@Autowired
    OtpService otpService;
@Autowired
    OtpRepository otpRepository;
@Autowired
    SendMailServiceImpl mailService;
@Autowired
    UserRepository userRepository;
    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId){
        User user =restTemplate.getForObject("http://user-service/"+userId,User.class);


        return user;



    }
    @PostMapping("/sendCodeForChangePassword")
    public ResponseEntity<Object> sendCodeChangePassword(@RequestBody ChangePasswordForm changePasswordForm,@RequestHeader String authenticationToken){
        if(changePasswordForm.getEmail().isEmpty()){
            return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"invalid email");

        }
        AuthenticationToken token=restTemplate.getForObject("http://user-service/authToken?authenticationToken="+authenticationToken,AuthenticationToken.class);
        System.out.println(token);
        User user=token.getUser();
 if(Objects.isNull(token)){
     return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"invalid token");
 }
 if(token.getUser().getEmail().equals(changePasswordForm.getEmail())){
    Otp otp= userService.sendOtpOnEmail(token.getUser().getEmail());
    otp.setUser(user);
    otpRepository.save(otp);
userRepository.save(user);


     return ResponseHandler.response(HttpStatus.OK,false,"otp send successfully");
 }
 return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"something went wrong");
    }
@PutMapping("verifyOtp/changePassword")
    public ResponseEntity<Object> verifyOtpChangePassword(@RequestBody VerifyPasswordForm verifyPasswordForm){
if(verifyPasswordForm.getToken().isEmpty()){
    return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"invalid token");

}
  AuthenticationToken token=restTemplate.getForObject("http://user-service/authToken?authenticationToken="+verifyPasswordForm.getToken(),AuthenticationToken.class);
if(Objects.isNull(token) || token.isDeleted()){
    return ResponseHandler.response(HttpStatus.NOT_FOUND,true,"token invalid");

}
User user=token.getUser();
if(Objects.isNull(user)||!user.isUserRegistered()){
    return ResponseHandler.response(HttpStatus.NOT_FOUND,true,"user is not registered");

}
Otp otpObj1=otpRepository.findByOtp(verifyPasswordForm.getOtp());
System.out.println(otpObj1);
Otp otpObj=otpRepository.findByOtpAndUser(verifyPasswordForm.getOtp(),user);
System.out.println(otpObj);
//    if(otpObj.getEmail().equals(user.getEmail())){
//        System.out.println("hello user");
//    }


if(Objects.isNull(otpObj)){
    return ResponseHandler.response(HttpStatus.NOT_FOUND,true,"otp not found");
}
if(!otpObj.isDeleted()&&!otpObj.isVerfied()){
    otpObj.setVerfied(true);
    otpObj.setDeleted(true);
    otpService.saveOtp(otpObj);
    return ResponseHandler.response(HttpStatus.ACCEPTED,false,"otp verification successful");
}
return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"something went wrong");



}
@PutMapping("/changePassword")
public ResponseEntity<Object> resetPassword(@RequestBody ChangePassword form){
     if(form.getToken().isEmpty()||form.getEmail().isEmpty()||form.getNewPassword().isEmpty()||form.getConfirmPassword().isEmpty()){
         ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"data insufficient");

     }
     if(form.getOldPassword().equals(form.getNewPassword())){
         return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"old password can not be same as new apssword");
     }
     AuthenticationToken token=restTemplate.getForObject("http://user-service/authToken?authenticationToken="+form.getToken(),AuthenticationToken.class);)
    User user=token.getUser();
     if(form.getNewPassword().equals(form.getConfirmPassword())){

         user.setPassword(form.getNewPassword());
         userRepository.save(user);
     }
}



    }


