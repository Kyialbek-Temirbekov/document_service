package com.example.document.service.impl;

import com.example.document.dto.MessageDto;
import com.example.document.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailMessageService implements MessageService {
    private final JavaMailSender mailSender;

    @Override
    public void sendMessage(MessageDto message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(message.recipient());
        simpleMailMessage.setSubject(message.subject());
        simpleMailMessage.setText(message.body());
        mailSender.send(simpleMailMessage);
    }
}
