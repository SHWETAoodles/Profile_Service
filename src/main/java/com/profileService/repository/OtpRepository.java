package com.profileService.repository;


import com.profileService.model.Otp;
import com.profileService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp,Long> {
    Otp findByOtp(int otp);
    Otp findByUser(User user);
    Otp findByOtpAndUser(int otp,User user);

}
