package net.javaguide.banking.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.javaguide.banking.security.model.UserDetail;

public interface UserDetailService {
    UserDetail loadUserByUsername(String username) throws UsernameNotFoundException;
}