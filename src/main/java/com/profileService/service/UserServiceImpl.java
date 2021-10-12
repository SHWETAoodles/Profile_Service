package com.profileService.service;

import com.profileService.model.Otp;
import com.profileService.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    MailService mailService;


    @Override
    public Otp sendOtpOnEmail(String email) {
        Random random = new Random();
        int otpNumber = 100000 + random.nextInt(900000);
        Otp otp = new Otp();
        otp.setOtp(otpNumber);
        otp.setEmail(email);
        Otp savedOtp = otpRepository.save(otp);
//        Map<String,Object> map=new HashMap<>() ;
//        map.put("otp = ",savedOtp.getOtp());
//        map.put("email ",email);

        String subject = "change password otp";
        String sendOtp = " " + (savedOtp.getOtp());
        MailService mailservice;
        mailService.mailSend(email, subject, sendOtp);

        return savedOtp;

    }
}