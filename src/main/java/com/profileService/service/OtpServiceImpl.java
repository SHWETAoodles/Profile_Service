package com.profileService.service;


import com.profileService.model.Otp;
import com.profileService.model.User;
import com.profileService.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OtpServiceImpl implements OtpService{
    @Autowired
    OtpRepository otpRepository;

    @Override
    public Otp setIsVerified(Otp otpObject) {
        otpObject.setVerfied(true);
        return otpRepository.save(otpObject);
    }

    @Override
    public Otp findByOTPNumber(Integer otp) {
        return otpRepository.findByOtp(otp);
    }



    @Override
    public void deleteOtpObject(Otp otp) {
otpRepository.delete(otp);
    }



    @Override
    public Otp findByOtpAndUser(Integer otp, User user) {
     return    otpRepository.findByOtpAndUser(otp,user);
    }



    @Override
    public void saveOtp(Otp otpObject) {
        otpRepository.save(otpObject);


    }

    @Override
    public boolean validateExpiryOfOtp(Otp otpObj) {
       return false;
    }
}
