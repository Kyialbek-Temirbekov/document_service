package com.example.document.service.impl;

import com.example.document.dto.MessageDto;
import com.example.document.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailMessageService implements MessageService {
    private final JavaMailSender mailSender;

    @Override
    public void sendMessage(MessageDto message) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(message.recipient());
            simpleMailMessage.setSubject(message.subject());
            simpleMailMessage.setText(message.body());
            mailSender.send(simpleMailMessage);
        } catch (MailException e) {
            log.warn(e.getLocalizedMessage());
        }
    }
}
