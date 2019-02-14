package com.authentication.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScan({ "com.authentication.service" })
@PropertySource("classpath:email.properties")
public class ServiceConfig {

	@Autowired
    private Environment env;

    public ServiceConfig() {
        super();
    }

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {
        final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(env.getProperty("spring.mail.host"));
        mailSenderImpl.setPort(env.getProperty("spring.mail.port", Integer.class));
        mailSenderImpl.setProtocol(env.getProperty("spring.mail.properties.mail.transport.protocol"));
        mailSenderImpl.setUsername(env.getProperty("spring.mail.username"));
        mailSenderImpl.setPassword(env.getProperty("spring.mail.password"));
        final Properties javaMailProps = new Properties();
        javaMailProps.put("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth", Boolean.class));
        javaMailProps.put("mail.smtp.starttls.enable", env.getProperty("spring.mail.properties.mail.smtp.starttls.enable", Boolean.class));
        javaMailProps.put("mail.smtp.starttls.required", env.getProperty("spring.mail.properties.mail.smtp.starttls.required", Boolean.class));
        mailSenderImpl.setJavaMailProperties(javaMailProps);
        return mailSenderImpl;
    }
    
}
