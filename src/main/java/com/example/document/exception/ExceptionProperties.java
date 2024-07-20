package com.example.document.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExceptionProperties {
    private final MessageSource messageSource;

    public String getMessage(Exception e) {
        Locale locale = LocaleContextHolder.getLocale();
        String localizedMessage = e.getLocalizedMessage();

        try {
            if(localizedMessage.contains(":")) {
                String[] parts = localizedMessage.split(":");
                return messageSource.getMessage(parts[0], parts[1].split(","), locale);
            }
            return messageSource.getMessage(localizedMessage, null, locale);
        } catch (NoSuchMessageException ex) {
            return e.getLocalizedMessage();
        }
    }
    public String getMessageAndLog(Exception e) {
        log.error(e.getLocalizedMessage(), e);
        return getMessage(e);
    }
}
