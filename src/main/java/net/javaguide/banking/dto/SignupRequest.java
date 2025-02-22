package net.javaguide.banking.dto;

import lombok.Getter;
import lombok.Setter;
import net.javaguide.banking.security.model.Role;

@Getter
@Setter
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private Role role;

}
