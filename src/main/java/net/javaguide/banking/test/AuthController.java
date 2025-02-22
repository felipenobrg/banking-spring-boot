// package net.javaguide.banking.test;

// import static org.mockito.Mockito.*;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import net.javaguide.banking.controller.AuthController;
// import net.javaguide.banking.payload.LoginRequest;
// import net.javaguide.banking.security.jwt.JwtUtils;
// import net.javaguide.banking.service.impl.UserDetailServiceImpl;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.http.MediaType;
// import org.springframework.security.authentication.AuthenticationManager;
// import
// org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.userdetails.User;

// import java.util.Collections;

// class AuthControllerTest {

// private MockMvc mockMvc;

// @Mock
// private AuthenticationManager authenticationManager;

// @Mock
// private JwtUtils jwtUtils;

// @InjectMocks
// private AuthController authController;

// @BeforeEach
// void setUp() {
// MockitoAnnotations.openMocks(this);
// mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
// }

// @Test
// void testSignin_Success() throws Exception {
// // Mock login request
// LoginRequest loginRequest = new LoginRequest("testuser", "password");

// // Mock authentication
// Authentication authentication = new UsernamePasswordAuthenticationToken(
// new User("testuser", "password", Collections.emptyList()), null);
// when(authenticationManager.authenticate(any())).thenReturn(authentication);

// // Mock JWT token generation
// when(jwtUtils.generateJwtToken(authentication)).thenReturn("mocked-jwt-token");

// // Perform the request
// mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
// .contentType(MediaType.APPLICATION_JSON)
// .content(new ObjectMapper().writeValueAsString(loginRequest)))
// .andExpect(status().isOk()) // Expect 200 OK
// .andExpect(jsonPath("$.token").value("mocked-jwt-token")); // Verify token
// response
// }

// @Test
// void testSignin_Failure() throws Exception {
// // Mock login request
// LoginRequest loginRequest = new LoginRequest("wronguser", "wrongpassword");

// // Simulate failed authentication
// when(authenticationManager.authenticate(any())).thenThrow(new
// RuntimeException("Invalid credentials"));

// // Perform the request
// mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
// .contentType(MediaType.APPLICATION_JSON)
// .content(new ObjectMapper().writeValueAsString(loginRequest)))
// .andExpect(status().isUnauthorized()); // Expect 401 Unauthorized
// }
// }
