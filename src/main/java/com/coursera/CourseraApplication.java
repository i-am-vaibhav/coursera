package com.coursera;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableEncryptableProperties
public class CourseraApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseraApplication.class, args);
    }

}
