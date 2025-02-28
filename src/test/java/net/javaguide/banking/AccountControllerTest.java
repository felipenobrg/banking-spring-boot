package net.javaguide.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import net.javaguide.banking.controller.AccountController;
import net.javaguide.banking.dto.AccountDto;
import net.javaguide.banking.entity.Account;
import net.javaguide.banking.entity.AccountType;
import net.javaguide.banking.entity.User;
import net.javaguide.banking.repository.AccountRepository;
import net.javaguide.banking.repository.UserRepository;
import net.javaguide.banking.service.AccountService;
import net.javaguide.banking.utils.SecurityUtils;

class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void getAccount() throws Exception {
        String fakeUsername = "testuser";
        try (MockedStatic<SecurityUtils> securityUtilsMock = Mockito.mockStatic(SecurityUtils.class)) {
            securityUtilsMock.when(SecurityUtils::getAuthenticatedUsername).thenReturn(fakeUsername);

            User mockUser = new User();
            mockUser.setUsername(fakeUsername);
            when(userRepository.findByUsername(fakeUsername)).thenReturn(Optional.of(mockUser));

            Account mockAccount = new Account();
            mockAccount.setAccountHolderName(fakeUsername);
            mockAccount.setBalance(500.0);
            when(accountRepository.findByAccountHolderName(fakeUsername)).thenReturn(mockAccount);

            AccountDto mockAccountDto = new AccountDto();
            mockAccountDto.setBalance(500.0);
            when(accountService.getAccount()).thenReturn(mockAccountDto);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/getAccount")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                    .andExpect(status().is2xxSuccessful());
        }
    }

    @Test
    void createAccount() throws Exception {
        String fakeUsername = "testuser";

        try (MockedStatic<SecurityUtils> securityUtilsMock = Mockito.mockStatic(SecurityUtils.class)) {
            securityUtilsMock.when(SecurityUtils::getAuthenticatedUsername).thenReturn(fakeUsername);

            User mockUser = new User();
            mockUser.setUsername(fakeUsername);
            when(userRepository.findByUsername(fakeUsername)).thenReturn(Optional.of(mockUser));

            Account mockAccount = new Account();
            mockAccount.setAccountHolderName(fakeUsername);
            mockAccount.setBalance(500.0);
            mockAccount.setAccountType(AccountType.SAVINGS);
            mockAccount.setCurrency("USD");
            mockAccount.setOverdraftLimit(0.0);

            when(accountRepository.findByAccountHolderName(fakeUsername)).thenReturn(mockAccount);

            AccountDto mockInputDto = new AccountDto();
            mockInputDto.setBalance(500.0);

            AccountDto mockAccountDto = new AccountDto();
            mockAccountDto.setBalance(500.0);
            when(accountService.createAccount(mockInputDto)).thenReturn(mockAccountDto);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                    .andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
