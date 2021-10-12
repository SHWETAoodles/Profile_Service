package com.profileService.dto;

public class VerifyPasswordForm {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    private String token;
    private int otp;
}
