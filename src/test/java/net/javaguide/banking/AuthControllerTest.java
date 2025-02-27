package net.javaguide.banking;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.javaguide.banking.controller.AuthController;
import net.javaguide.banking.dto.LoginRequest;
import net.javaguide.banking.dto.SignupRequest;
import net.javaguide.banking.repository.UserRepository;
import net.javaguide.banking.security.jwt.JwtUtils;
import net.javaguide.banking.security.model.Role;
import net.javaguide.banking.security.model.UserDetailImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testSignin_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test");
        loginRequest.setPassword("test");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailImpl(1L, "test", "test@example.com", "password", Role.USER, Collections.emptyList()),
                null);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtils.generateTokenFromUsername("test")).thenReturn("mocked-jwt-token");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void testSignup_Success() throws Exception {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("teste");
        signUpRequest.setEmail("tester@gmail.com");
        signUpRequest.setPassword("tester");

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());
    }
}
