package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.entities.Account;
import br.com.wss.barbersync.enums.Role;
import br.com.wss.barbersync.repositories.AccountRepository;
import br.com.wss.base.TransactionType;
import br.com.wss.exception.BusinessException;
import br.com.wss.filters.JwtToken;
import br.com.wss.filters.JwtToken.UserTokenDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountBusinessImplTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtToken jwtToken;

    @InjectMocks
    AccountBusinessImpl accountBusiness;

    private Account account;
    private Account savedAccount;
    private Account roleAccount;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setUid(null);
        account.setRole(Role.ROLE_USER);
        account.setEmail("email@email.com");
        account.setPassword("password");
        account.setPhone("123456789");
        account.setName("name");
        LocalDateTime created = LocalDateTime.now();
        account.setCreatedAt(created);
        account.setCreatedByName(null);
        account.setCreatedByUid(null);
        account.setUpdatedByUid(null);
        account.setUpdatedAt(null);
        account.setUpdatedByName(null);
        account.setDeleted(false);
        account.setDeletedByUid(null);
        account.setDeletedAt(null);
        account.setDeletedByName(null);

        savedAccount = new Account();
        savedAccount.setUid(UUID.randomUUID().toString());
        savedAccount.setRole(Role.ROLE_USER);
        savedAccount.setEmail("email@email.com");
        savedAccount.setPassword("password");
        savedAccount.setPhone("123456789");
        savedAccount.setName("name");
        savedAccount.setCreatedAt(created);
        savedAccount.setCreatedByName(null);
        savedAccount.setCreatedByUid(null);
        savedAccount.setUpdatedByUid(null);
        savedAccount.setUpdatedAt(null);
        savedAccount.setUpdatedByName(null);
        savedAccount.setDeleted(false);
        savedAccount.setDeletedByUid(null);
        savedAccount.setDeletedAt(null);
        savedAccount.setDeletedByName(null);

        roleAccount = new Account();
        roleAccount.setUid(UUID.randomUUID().toString());
        roleAccount.setRole(Role.ROLE_USER);
        roleAccount.setEmail("email@email.com");
        roleAccount.setPassword("password");
        roleAccount.setPhone("123456789");
        roleAccount.setName("name");

        ReflectionTestUtils.setField(accountBusiness, "jwtToken", jwtToken); // O ReflectionTestUtils.setField ignora os modificadores de acesso (private) e coloca o Mock exatamente onde ele precisa estar.
    }

    @Test
    @DisplayName("Deve salvar usuário com sucesso")
    void shouldCreateAccount() {


        // ARRANGE (Preparação)
        UserTokenDetails mockTokenDetails = mock(UserTokenDetails.class);
        when(jwtToken.getUserDetails()).thenReturn(mockTokenDetails);

        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        when(mockTokenDetails.getAccount()).thenReturn(null);

        when(passwordEncoder.encode(any())).thenReturn("encoded_password");

        // ACT (Ação)
        Account result = accountBusiness.insert(account);

        // ASSERT (Verificação)
        assertNotNull(result.getUid());
        assertEquals(Role.ROLE_USER, result.getRole());
    }

    @Test
    @DisplayName("Não Deve salvar usuário com por causa do ROLE_USER no token")
    void shouldNonCreateAccountSetRoleUserToken() {
        // ARRANGE (Preparação)

        UserTokenDetails mockTokenDetails = mock(UserTokenDetails.class);
        when(jwtToken.getUserDetails()).thenReturn(mockTokenDetails);

        when(mockTokenDetails.getAccount()).thenReturn(roleAccount);

        // ACT (Ação)
        BusinessException exception = assertThrows(BusinessException.class, () -> accountBusiness.insert(account));

        // ASSERT (Verificação)
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getBody().getStatus());
        assertEquals("A operação não pode ser realizada porque o usuário possui o papel de ROLE_USER", exception.getBody().getDetail());
        verify(accountRepository, never()).save(any());
    }


    @Test
    @DisplayName("Deve lançar exceção quando o e-mail já existe")
    void shouldThrowExceptionWhenEmailExists() {
        // ARRANGE (Preparação)
        when(accountRepository.findByEmail(any())).thenReturn(Optional.of(new Account()));

        // ACT (Ação)
        BusinessException exception = assertThrows(BusinessException.class, () -> accountBusiness.validate(account, TransactionType.INSERT));

        // ASSERT (Verificação)
        assertEquals(HttpStatus.CONFLICT.value(), exception.getBody().getStatus());
        assertEquals("Já existe uma conta cadastrada com este e-mail.", exception.getBody().getDetail());
        verify(accountRepository, times(1)).findByEmail(any());
        verify(accountRepository, never()).findByTaxNumber(any());
        verify(accountRepository, never()).findByPhone(any());

    }

    @Test
    @DisplayName("Deve lançar exceção quando o taxNumber já existe")
    void shouldThrowExceptionWhenTaxNumberExists() {
        // ARRANGE (Preparação)
        when(accountRepository.findByTaxNumber(any())).thenReturn(Optional.of(new Account()));

        // ACT (Ação)
        BusinessException exception = assertThrows(BusinessException.class, () -> accountBusiness.validate(account, TransactionType.INSERT));

        // ASSERT (Verificação)
        assertEquals(HttpStatus.CONFLICT.value(), exception.getBody().getStatus());
        assertEquals("Já existe uma conta cadastrada com este CPF/CNPJ.", exception.getBody().getDetail());
        verify(accountRepository, times(1)).findByTaxNumber(any());
        verify(accountRepository, never()).findByPhone(any());

    }

    @Test
    @DisplayName("Deve lançar exceção quando o phone já existe")
    void shouldThrowExceptionWhenPhonneExists() {
        // ARRANGE (Preparação)
        when(accountRepository.findByPhone(any())).thenReturn(Optional.of(new Account()));

        // ACT (Ação)
        BusinessException exception = assertThrows(BusinessException.class, () -> accountBusiness.validate(account, TransactionType.INSERT));

        // ASSERT (Verificação)
        assertEquals(HttpStatus.CONFLICT.value(), exception.getBody().getStatus());
        assertEquals("Já existe uma conta cadastrada com este número.", exception.getBody().getDetail());
        verify(accountRepository, times(1)).findByPhone(any());
    }
}
