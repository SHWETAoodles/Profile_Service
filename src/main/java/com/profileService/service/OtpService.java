package com.profileService.service;


import com.profileService.model.Otp;
import com.profileService.model.User;


public interface OtpService {

    public Otp setIsVerified(Otp otpObject);
    public Otp findByOTPNumber(Integer otp);
public void deleteOtpObject(Otp otp);
public Otp findByOtpAndUser(Integer otp, User user);

public void saveOtp(Otp otpObject);
public boolean validateExpiryOfOtp(Otp otpObj);
}
