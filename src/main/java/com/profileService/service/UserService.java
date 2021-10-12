package com.profileService.service;

import com.profileService.model.Otp;

public interface UserService {
    public Otp sendOtpOnEmail(String email);
}

