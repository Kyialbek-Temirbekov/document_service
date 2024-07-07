package com.example.document.security;

import com.example.document.exception.ExceptionMessages;
import com.example.document.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerRepository.findByUsername(email).orElseThrow(() ->
                new UsernameNotFoundException(ExceptionMessages.USER_NOT_FOUND_BY_EMAIL + ":" + email));
    }
}
