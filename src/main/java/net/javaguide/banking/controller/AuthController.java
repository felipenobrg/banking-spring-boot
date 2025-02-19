package net.javaguide.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguide.banking.dto.RegisterRequest;
import net.javaguide.banking.dto.UserDto;
// import net.javaguide.banking.security.jwt.JwtService;
import net.javaguide.banking.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // @Autowired
    // private AuthenticationManager authenticationManager;

    // @Autowired
    // private JwtService jwtService;

    @Autowired
    private UserServiceImpl userService;

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    // try {
    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(
    // loginRequest.getUsername(),
    // loginRequest.getPassword()));

    // UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    // String jwt = jwtService.generateToken(userDetails);

    // return ResponseEntity.ok(new AuthResponse(jwt));
    // } catch (BadCredentialsException e) {
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    // .body("Invalid username or password");
    // }
    // }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            UserDto userDto = new UserDto();
            userDto.setName(registerRequest.getName());
            userDto.setPassword(registerRequest.getPassword());
            userDto.setEmail(registerRequest.getEmail());

            userService.createUser(userDto);
            return ResponseEntity.ok("User registered successfully!");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

}
