package com.coursera.aspect;

import com.coursera.security.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AspectLogging {

    private final JavaMailSender javaMailSender;

    public AspectLogging(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Around("execution(* com.coursera.repository.*.*(..)) ")
    public Object logging(ProceedingJoinPoint point) {
        Object obj = null;
        try {
            log.debug("start-time for {} is {}", point.getSignature().toShortString(), LocalDateTime.now());
            obj = point.proceed();
            log.debug("end-time for {} is {}", point.getSignature().toShortString(), LocalDateTime.now());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    @AfterReturning(value = "execution(* com.coursera.security.CustomUserDetailsService.loadUserByUsername(..)) ",
            returning = "user")
    public void authenticationNotification(JoinPoint joinPoint, AuthenticatedUser user) {
        if (user != null) {
            sendMail(user);
        }
    }

    private void sendMail(AuthenticatedUser user) {
        Thread thread = new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("vg566556@gmail.com");
            message.setTo(user.getUser().getEmail());
            message.setSubject("Access Notification");
            message.setText("Some is trying to access your account, Ignore if it is you.");
            javaMailSender.send(message);
        });
        thread.start();
    }

}
