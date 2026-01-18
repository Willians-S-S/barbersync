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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    @DisplayName("Deve retornar todas as contas")
    void shouldReturnAllAccounts() {
        // ARRANGE (Preparação)
        when(accountRepository.findAll()).thenReturn(Arrays.asList(account, account));

        // ACT (Ação)
        List<Account> result = accountBusiness.findAll();

        // ASSERT (Verificação)
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existirem contas")
    void shouldReturnEmptyListWhenNoAccountsExist() {
        // ARRANGE (Preparação)
        when(accountRepository.findAll()).thenReturn(Collections.emptyList());

        // ACT (Ação)
        List<Account> result = accountBusiness.findAll();

        // ASSERT (Verificação)
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Deve filtrar contas pelo UUID e retornar a página correta")
    void shouldFilterAccountsByUid() {
        // ARRANGE (Preparação)
        Pageable pageable = PageRequest.of(0, 10);

        List<Account> accounts = new ArrayList<>();
        accounts.add(account);

        Page<Account> page = new PageImpl<>(accounts, pageable, accounts.size());

        String uuid = UUID.randomUUID().toString();

        when(accountRepository.findByParams(uuid,
                null, null, null, null,
                null, null, null,
                null, null, null, pageable)).thenReturn(page);


        // ACT (Ação)
        Page<Account> result = accountBusiness.findByParams(uuid,
                null, null, null, null,
                null, null, null,
                null, null, null, pageable);

        // ASSERT (Verifição)
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Deve retornar as contas criadas dentro do intervalo de datas especificado")
    void shouldFindAccountsByDateRange() {
        // ARRANGE (Preparação)
        Account account1 = new Account();
        Account account2 = new Account();
        Account account3 = new Account();

        BeanUtils.copyProperties(account, account1);
        BeanUtils.copyProperties(account, account2);
        BeanUtils.copyProperties(account, account3);

        account1.setCreatedAt(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
        account2.setCreatedAt(LocalDateTime.of(2025, 1, 5, 0, 0, 0));
        account3.setCreatedAt(LocalDateTime.of(2025, 1, 10, 0, 0, 0));

        List<Account> accounts = List.of(account1, account2, account3);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Account> page = new PageImpl<>(accounts, pageable, accounts.size());

        when(accountRepository.findByParams(null,
                null, null, null, null,
                null, null, null,
                null, LocalDateTime.of(2025, 1, 1, 0, 0, 0),
                LocalDateTime.of(2025, 1, 10, 0, 0, 0), pageable)).thenReturn(page);

        // ACT (Ação)
        Page<Account> result = accountBusiness.findByParams(null,
                null, null, null, null,
                null, null, null,
                null, LocalDateTime.of(2025, 1, 1, 0, 0, 0),
                LocalDateTime.of(2025, 1, 10, 0, 0, 0), pageable);

        // ASSERT (Verificação)
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        verify(accountRepository).findByParams(
                eq(null), eq(null), eq(null), eq(null), eq(null),
                eq(null), eq(null), eq(null), eq(null),
                eq(LocalDateTime.of(2025, 1, 1, 0, 0, 0)),
                eq(LocalDateTime.of(2025, 1, 10, 0, 0, 0)),
                eq(pageable)
        );
    }

    @Test
    @DisplayName("Deve filtrar contas pelo Nome (parcial) e retornar a página correta")
    void shouldFilterAccountsByName() {
        Pageable pageable = PageRequest.of(0, 10);
        String name = "João";
        Page<Account> page = new PageImpl<>(List.of(account), pageable, 1);

        when(accountRepository.findByParams(eq(null), eq(name), any(), any(), any(),
                any(), any(), any(), any(), any(), any(), eq(pageable))).thenReturn(page);

        Page<Account> result = accountBusiness.findByParams(null, name, null, null, null,
                null, null, null, null, null, null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(accountRepository).findByParams(null, name, null, null, null, null, null, null, null, null, null, pageable);
    }

    @Test
    @DisplayName("Deve filtrar pelo Email, TaxNumber e Phone")
    void shouldFilterByContactDetails() {
        Pageable pageable = PageRequest.of(0, 10);
        String email = "teste@email.com";
        String taxNumber = "123456";
        String phone = "99999";

        Page<Account> page = new PageImpl<>(List.of(account), pageable, 1);

        when(accountRepository.findByParams(null, null, taxNumber, email, phone,
                null, null, null, null, null, null, pageable)).thenReturn(page);

        Page<Account> result = accountBusiness.findByParams(null, null, taxNumber, email, phone,
                null, null, null, null, null, null, pageable);

        assertNotNull(result);
        verify(accountRepository).findByParams(null, null, taxNumber, email, phone, null, null, null, null, null, null, pageable);
    }

    @Test
    @DisplayName("Deve filtrar pelo criador e pelo papel (Role)")
    void shouldFilterByCreatorAndRole() {
        Pageable pageable = PageRequest.of(0, 10);
        String creator = "Admin";
        Role role = Role.ROLE_ADM;

        Page<Account> page = new PageImpl<>(List.of(account), pageable, 1);

        when(accountRepository.findByParams(null, null, null, null, null,
                creator, null, role, null, null, null, pageable)).thenReturn(page);

        Page<Account> result = accountBusiness.findByParams(null, null, null, null, null,
                creator, null, role, null, null, null, pageable);

        assertNotNull(result);
        verify(accountRepository).findByParams(null, null, null, null, null, creator, null, role, null, null, null, pageable);
    }

    @Test
    @DisplayName("Deve retornar todas as contas quando todos os parâmetros forem nulos")
    void shouldReturnAllAccountsWhenParamsAreNull() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Account> page = new PageImpl<>(List.of(account), pageable, 1);

        when(accountRepository.findByParams(null, null, null, null, null,
                null, null, null, null, null, null, pageable)).thenReturn(page);

        Page<Account> result = accountBusiness.findByParams(null, null, null, null, null,
                null, null, null, null, null, null, pageable);

        assertNotNull(result);
        verify(accountRepository).findByParams(null, null, null, null, null, null, null, null, null, null, null, pageable);
    }

    @Test
    @DisplayName("Deve retornar uma conta presente quando o e-mail cadastrado for fornecido")
    void shouldReturnAccountWhenEmailExists() {
        String email = account.getEmail();
        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(account));

        Optional<Account> result = accountBusiness.findByEmail(email);

        assertNotNull(result);
        assertEquals(account.getEmail(), result.get().getEmail());
        verify(accountRepository).findByEmail(email);
    }

    @Test
    @DisplayName("Deve retornar vazio quando buscar por um e-mail que não consta na base")
    void shouldReturnEmptyWhenEmailNotFound() {
        String email = account.getEmail();
        when(accountRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<Account> result = accountBusiness.findByEmail(email);

        assertNotNull(result);
        assertThat(result).isEmpty();
        verify(accountRepository).findByEmail(email);
    }

    @Test
    void shouldReturnCurrentAccountWhenUserIsAuthenticated() {
        UserTokenDetails userTokenDetails = new UserTokenDetails("user", account, "jwt");
        when(jwtToken.getUserDetails()).thenReturn(userTokenDetails);

        Account result = accountBusiness.getCurrentAccount();

        assertNotNull(result);
        assertEquals(account.getName(),  result.getName());
    }

    @Test
    void shouldThrowBusinessExceptionWhenUserIsNotAuthenticated(){
        UserTokenDetails userTokenDetails = new UserTokenDetails("anonymousUser", null, null);
        when(jwtToken.getUserDetails()).thenReturn(userTokenDetails);

        BusinessException exception = assertThrows(BusinessException.class, () -> accountBusiness.getCurrentAccount());

        assertNotNull(exception);
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Usuário não autenticado.", exception.getBody().getDetail());
    }

    @Test
    @DisplayName("Deve atualizar a conta com sucesso quando todos os dados são válidos e novos")
    void shouldUpdateAccountSuccessfully() {
        // ARRANGE
        Account loggedAccount = new Account();
        loggedAccount.setUid("123");
        loggedAccount.setName("Antigo Nome");
        loggedAccount.setEmail("antigo@email.com");
        loggedAccount.setPhone("1111111");
        loggedAccount.setTaxNumber("111.111.111-11");

        Account updateData = new Account();
        updateData.setName("Novo Nome");
        updateData.setEmail("novo@email.com");
        updateData.setPhone("2222222");
        updateData.setTaxNumber("222.222.222-22");

        UserTokenDetails userTokenDetails = new UserTokenDetails("user", loggedAccount, "jwt");
        when(jwtToken.getUserDetails()).thenReturn(userTokenDetails);

        // Mocks para garantir que os novos dados não existem no banco
        when(accountRepository.findByEmail(updateData.getEmail())).thenReturn(Optional.empty());
        when(accountRepository.findByPhone(updateData.getPhone())).thenReturn(Optional.empty());
        when(accountRepository.findByTaxNumber(updateData.getTaxNumber())).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ACT
        Account result = accountBusiness.update(updateData);

        // ASSERT
        assertNotNull(result);
        assertEquals("Novo Nome", result.getName());
        assertEquals("novo@email.com", result.getEmail());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o novo e-mail já estiver em uso")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // ARRANGE
        Account loggedAccount = new Account();
        loggedAccount.setEmail("antigo@email.com");

        Account updateData = new Account();
        updateData.setEmail("existente@email.com");

        UserTokenDetails userTokenDetails = new UserTokenDetails("user", loggedAccount, "jwt");
        when(jwtToken.getUserDetails()).thenReturn(userTokenDetails);

        when(accountRepository.findByEmail(updateData.getEmail())).thenReturn(Optional.of(new Account()));

        // ACT & ASSERT
        BusinessException exception = assertThrows(BusinessException.class, () -> accountBusiness.update(updateData));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertTrue(exception.getBody().getDetail().contains("e-mail"));
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o novo telefone já estiver em uso")
    void shouldThrowExceptionWhenPhoneAlreadyExists() {
        // ARRANGE
        Account loggedAccount = new Account();
        loggedAccount.setEmail("mesmo@email.com");
        loggedAccount.setPhone("111");

        Account updateData = new Account();
        updateData.setEmail("mesmo@email.com");
        updateData.setPhone("222");

        UserTokenDetails userTokenDetails = new UserTokenDetails("user", loggedAccount, "jwt");
        when(jwtToken.getUserDetails()).thenReturn(userTokenDetails);

        when(accountRepository.findByPhone("222")).thenReturn(Optional.of(new Account()));

        // ACT & ASSERT
        BusinessException exception = assertThrows(BusinessException.class, () -> accountBusiness.update(updateData));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertTrue(exception.getBody().getDetail().contains("número"));
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o novo TaxNumber já estiver em uso")
    void shouldThrowExceptionWhenTaxNumberAlreadyExists() {
        // ARRANGE
        Account loggedAccount = new Account();
        loggedAccount.setEmail("mesmo@email.com");
        loggedAccount.setPhone("111");
        loggedAccount.setTaxNumber("OLD-TAX");

        Account updateData = new Account();
        updateData.setEmail("mesmo@email.com");
        updateData.setPhone("111");
        updateData.setTaxNumber("NEW-TAX");

        UserTokenDetails userTokenDetails = new UserTokenDetails("user", loggedAccount, "jwt");
        when(jwtToken.getUserDetails()).thenReturn(userTokenDetails);

        when(accountRepository.findByTaxNumber("NEW-TAX")).thenReturn(Optional.of(new Account()));

        // ACT & ASSERT
        BusinessException exception = assertThrows(BusinessException.class, () -> accountBusiness.update(updateData));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }

    @Test
    @DisplayName("Deve chamar o delete da superclasse com o UID da conta logada")
    void shouldDeleteCurrentAccountSuccessfully() {
        // ARRANGE
        String expectedUid = UUID.randomUUID().toString();

        Account loggedAccount = account;
        loggedAccount.setUid(expectedUid);
        loggedAccount.setName("User Test");

        UserTokenDetails userTokenDetails = new UserTokenDetails("user", loggedAccount, "jwt");

        when(jwtToken.getUserDetails()).thenReturn(userTokenDetails);

        when(accountRepository.findById(expectedUid)).thenReturn(Optional.of(loggedAccount));

        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));

        // ACT
        accountBusiness.delete();

        // ASSERT
        // No soft delete, o repository.save é chamado com deleted = true
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountCaptor.capture());

        assertTrue(accountCaptor.getValue().getDeleted());
        assertEquals(expectedUid, accountCaptor.getValue().getDeletedByUid());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver conta no contexto ao tentar deletar")
    void shouldThrowExceptionWhenDeletingWithoutAccount() {
        // ARRANGE
        when(jwtToken.getUserDetails()).thenReturn(null);

        // ACT & ASSERT
        assertThrows(NullPointerException.class, () -> {
            accountBusiness.delete();
        });
    }
}
