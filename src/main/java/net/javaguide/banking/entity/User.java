package net.javaguide.banking.entity;

import jakarta.persistence.*;
import lombok.*;
import net.javaguide.banking.security.model.Role;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = true)
    private String verificationCode;

    @Column(nullable = false)
    private boolean isVerified = false;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public User(String username, String email, String password, Role role, String verificationCode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.verificationCode = verificationCode;
    }
}
