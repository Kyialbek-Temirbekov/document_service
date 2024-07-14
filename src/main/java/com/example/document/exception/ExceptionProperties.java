package com.example.document.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ExceptionProperties {
    private final MessageSource messageSource;

    public String getMessage(Exception e) {
        Locale locale = LocaleContextHolder.getLocale();
        String localizedMessage = e.getLocalizedMessage();

        if(localizedMessage.contains(":")) {
            String[] parts = localizedMessage.split(":");
            return messageSource.getMessage(parts[0], parts[1].split(","), locale);
        }
        return messageSource.getMessage(localizedMessage, null, locale);
    }
}
