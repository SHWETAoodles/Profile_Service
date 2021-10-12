package com.profileService.service;

import java.util.Map;

public interface MailService {
    public void mailSend(String email, String subject, String text);
    public void mailSendForLinkVerification(String to, String text, Map<String, Object> map);

}
