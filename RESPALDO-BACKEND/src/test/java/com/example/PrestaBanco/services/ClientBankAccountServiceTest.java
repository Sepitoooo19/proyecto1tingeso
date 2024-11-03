package com.example.PrestaBanco.services;

import com.example.PrestaBanco.entities.ClientBankAccountEntity;
import com.example.PrestaBanco.repositories.ClientBankAccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ClientBankAccountServiceTest {

    @InjectMocks
    private  ClientBankAccountService clientBankAccountService;

    @Mock
    private ClientBankAccountRepository clientBankAccountRepository;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Prueba cuando se encuentran cuentas bancarias
    @Test
    public void whenFindAll_thenReturnAccounts() {
        // given
        List<ClientBankAccountEntity> accounts = Arrays.asList(
                new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1)),
                new ClientBankAccountEntity(2L, 2L, "Bank B", "67890", 2000.0, "Withdrawal", LocalDate.now(), LocalDate.of(2021, 6, 15))
        );
        given(clientBankAccountRepository.findAll()).willReturn(accounts);

        // when
        List<ClientBankAccountEntity> foundAccounts = clientBankAccountService.findAll();

        // then
        assertThat(foundAccounts).isNotEmpty();
        assertThat(foundAccounts.size()).isEqualTo(2);
    }

    // 2. Prueba cuando no se encuentran cuentas bancarias
    @Test
    public void whenFindAll_thenReturnEmptyList() {
        // given
        given(clientBankAccountRepository.findAll()).willReturn(Arrays.asList());

        // when
        List<ClientBankAccountEntity> foundAccounts = clientBankAccountService.findAll();

        // then
        assertThat(foundAccounts).isEmpty();
    }

    // 3. Prueba para verificar que el repositorio es llamado una sola vez
    @Test
    public void whenFindAll_thenRepositoryMethodCalledOnce() {
        // given
        given(clientBankAccountRepository.findAll()).willReturn(Arrays.asList());

        // when
        clientBankAccountService.findAll();

        // then
        verify(clientBankAccountRepository, times(1)).findAll();
    }

    // 4. Prueba cuando hay una sola cuenta bancaria
    @Test
    public void whenFindAllWithOneAccount_thenReturnOneAccount() {
        // given
        List<ClientBankAccountEntity> accounts = Arrays.asList(
                new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1))
        );
        given(clientBankAccountRepository.findAll()).willReturn(accounts);

        // when
        List<ClientBankAccountEntity> foundAccounts = clientBankAccountService.findAll();

        // then
        assertThat(foundAccounts).hasSize(1);
    }

    // 5. Prueba para verificar si los datos de las cuentas son correctos
    @Test
    public void whenFindAll_thenCheckAccountData() {
        // given
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findAll()).willReturn(Arrays.asList(account));

        // when
        List<ClientBankAccountEntity> foundAccounts = clientBankAccountService.findAll();

        // then
        assertThat(foundAccounts.get(0).getBank_name()).isEqualTo("Bank A");
        assertThat(foundAccounts.get(0).getAccount_number()).isEqualTo("12345");
    }

    // 6. Prueba para verificar que la lista devuelta no contiene nulos
    @Test
    public void whenFindAll_thenListHasNoNulls() {
        // given
        List<ClientBankAccountEntity> accounts = Arrays.asList(
                new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1)),
                new ClientBankAccountEntity(2L, 2L, "Bank B", "67890", 2000.0, "Withdrawal", LocalDate.now(), LocalDate.of(2021, 6, 15))
        );
        given(clientBankAccountRepository.findAll()).willReturn(accounts);

        // when
        List<ClientBankAccountEntity> foundAccounts = clientBankAccountService.findAll();

        // then
        assertThat(foundAccounts).doesNotContainNull();
    }

    // 7. Prueba para manejar una gran cantidad de cuentas
    @Test
    public void whenFindAllWithLargeNumberOfAccounts_thenReturnAll() {
        // given
        List<ClientBankAccountEntity> accounts = Arrays.asList(
                new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1)),
                new ClientBankAccountEntity(2L, 2L, "Bank B", "67890", 2000.0, "Withdrawal", LocalDate.now(), LocalDate.of(2021, 6, 15)),
                new ClientBankAccountEntity(3L, 3L, "Bank C", "54321", 1500.0, "Deposit", LocalDate.now(), LocalDate.of(2019, 11, 3))
        );
        given(clientBankAccountRepository.findAll()).willReturn(accounts);

        // when
        List<ClientBankAccountEntity> foundAccounts = clientBankAccountService.findAll();

        // then
        assertThat(foundAccounts).hasSize(3);
    }

    // 1. Prueba cuando se encuentra una cuenta bancaria válida
    @Test
    public void whenFindByClientBankAccountId_thenReturnAccount() {
        // given
        Long accountId = 1L;
        ClientBankAccountEntity account = new ClientBankAccountEntity(accountId, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByClientBankAccountId(accountId)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByClientBankAccountId(accountId);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getClient_bank_account_id()).isEqualTo(accountId);
    }

    // 2. Prueba cuando no se encuentra ninguna cuenta bancaria (resultado nulo)
    @Test
    public void whenFindByClientBankAccountId_thenReturnNull() {
        // given
        Long accountId = 2L;
        given(clientBankAccountRepository.findByClientBankAccountId(accountId)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByClientBankAccountId(accountId);

        // then
        assertThat(foundAccount).isNull();
    }

    // 3. Prueba para verificar que el repositorio es llamado una sola vez
    @Test
    public void whenFindByClientBankAccountId_thenRepositoryMethodCalledOnce() {
        // given
        Long accountId = 1L;
        ClientBankAccountEntity account = new ClientBankAccountEntity(accountId, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByClientBankAccountId(accountId)).willReturn(account);

        // when
        clientBankAccountService.findByClientBankAccountId(accountId);

        // then
        verify(clientBankAccountRepository, times(1)).findByClientBankAccountId(accountId);
    }

    // 4. Prueba con un ID de cuenta bancaria que no existe
    @Test
    public void whenFindByInvalidClientBankAccountId_thenReturnNull() {
        // given
        Long invalidAccountId = 99L;
        given(clientBankAccountRepository.findByClientBankAccountId(invalidAccountId)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByClientBankAccountId(invalidAccountId);

        // then
        assertThat(foundAccount).isNull();
    }

    // 5. Prueba cuando se busca una cuenta bancaria con ID existente y se verifica el contenido
    @Test
    public void whenFindByClientBankAccountId_thenCheckAccountData() {
        // given
        Long accountId = 1L;
        ClientBankAccountEntity account = new ClientBankAccountEntity(accountId, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByClientBankAccountId(accountId)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByClientBankAccountId(accountId);

        // then
        assertThat(foundAccount.getBank_name()).isEqualTo("Bank A");
        assertThat(foundAccount.getAccount_number()).isEqualTo("12345");
    }

    // 6. Prueba con un ID de cuenta negativa (no válida)
    @Test
    public void whenFindByNegativeClientBankAccountId_thenReturnNull() {
        // given
        Long negativeAccountId = -1L;
        given(clientBankAccountRepository.findByClientBankAccountId(negativeAccountId)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByClientBankAccountId(negativeAccountId);

        // then
        assertThat(foundAccount).isNull();
    }

    // 7. Prueba para manejar múltiples llamadas al método (sin afectar el resultado)
    @Test
    public void whenFindByClientBankAccountId_thenMultipleCallsReturnSameResult() {
        // given
        Long accountId = 1L;
        ClientBankAccountEntity account = new ClientBankAccountEntity(accountId, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByClientBankAccountId(accountId)).willReturn(account);

        // when
        ClientBankAccountEntity firstCall = clientBankAccountService.findByClientBankAccountId(accountId);
        ClientBankAccountEntity secondCall = clientBankAccountService.findByClientBankAccountId(accountId);

        // then
        assertThat(firstCall).isEqualTo(secondCall);
        assertThat(firstCall).isNotNull();
        assertThat(secondCall).isNotNull();
    }

    // 1. Prueba cuando se encuentran cuentas bancarias para un cliente válido
    @Test
    public void whenFindByClientId_thenReturnAccounts() {
        // given
        Long clientId = 1L;
        List<ClientBankAccountEntity> accounts = Arrays.asList(
                new ClientBankAccountEntity(1L, clientId, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1)),
                new ClientBankAccountEntity(2L, clientId, "Bank B", "67890", 2000.0, "Withdrawal", LocalDate.now(), LocalDate.of(2021, 6, 15))
        );
        given(clientBankAccountRepository.findByClientId(clientId)).willReturn(accounts);

        // when
        List<ClientBankAccountEntity> foundAccounts = clientBankAccountService.findByClientId(clientId);

        // then
        assertThat(foundAccounts).isNotEmpty();
        assertThat(foundAccounts.size()).isEqualTo(2);
    }

    // 2. Prueba cuando no se encuentran cuentas bancarias para un cliente
    @Test
    public void whenFindByClientId_thenReturnEmptyList() {
        // given
        Long clientId = 2L;
        given(clientBankAccountRepository.findByClientId(clientId)).willReturn(Arrays.asList());

        // when
        List<ClientBankAccountEntity> foundAccounts = clientBankAccountService.findByClientId(clientId);

        // then
        assertThat(foundAccounts).isEmpty();
    }

    // 3. Prueba para verificar que el repositorio es llamado una sola vez
    @Test
    public void whenFindByClientId_thenRepositoryMethodCalledOnce() {
        // given
        Long clientId = 1L;
        given(clientBankAccountRepository.findByClientId(clientId)).willReturn(Arrays.asList());

        // when
        clientBankAccountService.findByClientId(clientId);

        // then
        verify(clientBankAccountRepository, times(1)).findByClientId(clientId);
    }

    // 4. Prueba cuando hay una sola cuenta bancaria para un cliente
    @Test
    public void whenFindByClientIdWithOneAccount_thenReturnOneAccount() {
        // given
        Long clientId = 1L;
        List<ClientBankAccountEntity> accounts = Arrays.asList(
                new ClientBankAccountEntity(1L, clientId, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1))
        );
        given(clientBankAccountRepository.findByClientId(clientId)).willReturn(accounts);

        // when
        List<ClientBankAccountEntity> foundAccounts = clientBankAccountService.findByClientId(clientId);

        // then
        assertThat(foundAccounts).hasSize(1);
    }

    // 5. Prueba para verificar si los datos de las cuentas son correctos
    @Test
    public void whenFindByClientId_thenCheckAccountData() {
        // given
        Long clientId = 1L;
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, clientId, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByClientId(clientId)).willReturn(Arrays.asList(account));

        // when
        List<ClientBankAccountEntity> foundAccounts = clientBankAccountService.findByClientId(clientId);

        // then
        assertThat(foundAccounts.get(0).getBank_name()).isEqualTo("Bank A");
        assertThat(foundAccounts.get(0).getAccount_number()).isEqualTo("12345");
    }

    // 6. Prueba para verificar que la lista devuelta no contiene nulos
    @Test
    public void whenFindByClientId_thenListHasNoNulls() {
        // given
        Long clientId = 1L;
        List<ClientBankAccountEntity> accounts = Arrays.asList(
                new ClientBankAccountEntity(1L, clientId, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1)),
                new ClientBankAccountEntity(2L, clientId, "Bank B", "67890", 2000.0, "Withdrawal", LocalDate.now(), LocalDate.of(2021, 6, 15))
        );
        given(clientBankAccountRepository.findByClientId(clientId)).willReturn(accounts);

        // when
        List<ClientBankAccountEntity> foundAccounts = clientBankAccountService.findByClientId(clientId);

        // then
        assertThat(foundAccounts).doesNotContainNull();
    }

    // 7. Prueba para manejar una gran cantidad de cuentas para un cliente
    @Test
    public void whenFindByClientIdWithLargeNumberOfAccounts_thenReturnAll() {
        // given
        Long clientId = 1L;
        List<ClientBankAccountEntity> accounts = Arrays.asList(
                new ClientBankAccountEntity(1L, clientId, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1)),
                new ClientBankAccountEntity(2L, clientId, "Bank B", "67890", 2000.0, "Withdrawal", LocalDate.now(), LocalDate.of(2021, 6, 15)),
                new ClientBankAccountEntity(3L, clientId, "Bank C", "54321", 1500.0, "Deposit", LocalDate.now(), LocalDate.of(2019, 11, 3))
        );
        given(clientBankAccountRepository.findByClientId(clientId)).willReturn(accounts);

        // when
        List<ClientBankAccountEntity> foundAccounts = clientBankAccountService.findByClientId(clientId);

        // then
        assertThat(foundAccounts).hasSize(3);
    }

    // 1. Prueba cuando se encuentra una cuenta bancaria por nombre de banco válido
    @Test
    public void whenFindByBankName_thenReturnAccount() {
        // given
        String bankName = "Bank A";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, bankName, "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByBankName(bankName)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByBankName(bankName);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getBank_name()).isEqualTo(bankName);
    }

    // 2. Prueba cuando no se encuentra una cuenta bancaria por nombre de banco
    @Test
    public void whenFindByBankName_thenReturnNull() {
        // given
        String bankName = "Nonexistent Bank";
        given(clientBankAccountRepository.findByBankName(bankName)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByBankName(bankName);

        // then
        assertThat(foundAccount).isNull();
    }

    // 3. Prueba cuando el nombre del banco es nulo
    @Test
    public void whenBankNameIsNull_thenReturnNull() {
        // given
        String bankName = null;
        given(clientBankAccountRepository.findByBankName(bankName)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByBankName(bankName);

        // then
        assertThat(foundAccount).isNull();
    }

    // 4. Prueba cuando el nombre del banco es vacío
    @Test
    public void whenBankNameIsEmpty_thenReturnNull() {
        // given
        String bankName = "";
        given(clientBankAccountRepository.findByBankName(bankName)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByBankName(bankName);

        // then
        assertThat(foundAccount).isNull();
    }

    // 5. Prueba para verificar que el repositorio es llamado una sola vez
    @Test
    public void whenFindByBankName_thenRepositoryMethodCalledOnce() {
        // given
        String bankName = "Bank A";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, bankName, "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByBankName(bankName)).willReturn(account);

        // when
        clientBankAccountService.findByBankName(bankName);

        // then
        verify(clientBankAccountRepository, times(1)).findByBankName(bankName);
    }

    // 6. Prueba para verificar que no se devuelve una cuenta si el nombre del banco no coincide
    @Test
    public void whenFindByBankNameDoesNotMatch_thenReturnNull() {
        // given
        String bankName = "Bank A";
        String wrongBankName = "Bank B";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, bankName, "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByBankName(bankName)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByBankName(wrongBankName);

        // then
        assertThat(foundAccount).isNull();
    }

    // 7. Prueba para verificar que el nombre del banco es correcto
    @Test
    public void whenFindByBankName_thenCheckBankName() {
        // given
        String bankName = "Bank A";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, bankName, "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByBankName(bankName)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByBankName(bankName);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getBank_name()).isEqualTo(bankName);
    }

    // 1. Prueba cuando se encuentra una cuenta bancaria por número de cuenta válido
    @Test
    public void whenFindByAccountNumber_thenReturnAccount() {
        // given
        String accountNumber = "12345";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", accountNumber, 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByAccountNumber(accountNumber)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAccountNumber(accountNumber);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getAccount_number()).isEqualTo(accountNumber);
    }

    // 2. Prueba cuando no se encuentra una cuenta bancaria por número de cuenta
    @Test
    public void whenFindByAccountNumber_thenReturnNull() {
        // given
        String accountNumber = "67890";
        given(clientBankAccountRepository.findByAccountNumber(accountNumber)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAccountNumber(accountNumber);

        // then
        assertThat(foundAccount).isNull();
    }

    // 3. Prueba cuando el número de cuenta es nulo
    @Test
    public void whenAccountNumberIsNull_thenReturnNull() {
        // given
        String accountNumber = null;
        given(clientBankAccountRepository.findByAccountNumber(accountNumber)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAccountNumber(accountNumber);

        // then
        assertThat(foundAccount).isNull();
    }

    // 4. Prueba cuando el número de cuenta es vacío
    @Test
    public void whenAccountNumberIsEmpty_thenReturnNull() {
        // given
        String accountNumber = "";
        given(clientBankAccountRepository.findByAccountNumber(accountNumber)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAccountNumber(accountNumber);

        // then
        assertThat(foundAccount).isNull();
    }

    // 5. Prueba para verificar que el repositorio es llamado una sola vez
    @Test
    public void whenFindByAccountNumber_thenRepositoryMethodCalledOnce() {
        // given
        String accountNumber = "12345";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", accountNumber, 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByAccountNumber(accountNumber)).willReturn(account);

        // when
        clientBankAccountService.findByAccountNumber(accountNumber);

        // then
        verify(clientBankAccountRepository, times(1)).findByAccountNumber(accountNumber);
    }

    // 6. Prueba para verificar que no se devuelve una cuenta si el número de cuenta no coincide
    @Test
    public void whenFindByAccountNumberDoesNotMatch_thenReturnNull() {
        // given
        String accountNumber = "12345";
        String wrongAccountNumber = "67890";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", accountNumber, 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByAccountNumber(accountNumber)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAccountNumber(wrongAccountNumber);

        // then
        assertThat(foundAccount).isNull();
    }

    // 7. Prueba para verificar que el número de cuenta es correcto
    @Test
    public void whenFindByAccountNumber_thenCheckAccountNumber() {
        // given
        String accountNumber = "12345";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", accountNumber, 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByAccountNumber(accountNumber)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAccountNumber(accountNumber);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getAccount_number()).isEqualTo(accountNumber);
    }

    // 1. Prueba cuando se encuentra una cuenta bancaria por monto válido
    @Test
    public void whenFindByAmount_thenReturnAccount() {
        // given
        double amount = 1000.0;
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", amount, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByAmount(amount)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAmount(amount);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getAmount()).isEqualTo(amount);
    }

    // 2. Prueba cuando no se encuentra una cuenta bancaria por monto
    @Test
    public void whenFindByAmount_thenReturnNull() {
        // given
        double amount = 2000.0;
        given(clientBankAccountRepository.findByAmount(amount)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAmount(amount);

        // then
        assertThat(foundAccount).isNull();
    }

    // 3. Prueba cuando el monto es cero
    @Test
    public void whenAmountIsZero_thenReturnNull() {
        // given
        double amount = 0.0;
        given(clientBankAccountRepository.findByAmount(amount)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAmount(amount);

        // then
        assertThat(foundAccount).isNull();
    }

    // 4. Prueba para verificar que el repositorio es llamado una sola vez
    @Test
    public void whenFindByAmount_thenRepositoryMethodCalledOnce() {
        // given
        double amount = 1000.0;
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", amount, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByAmount(amount)).willReturn(account);

        // when
        clientBankAccountService.findByAmount(amount);

        // then
        verify(clientBankAccountRepository, times(1)).findByAmount(amount);
    }

    // 5. Prueba para verificar que no se devuelve una cuenta si el monto no coincide
    @Test
    public void whenFindByAmountDoesNotMatch_thenReturnNull() {
        // given
        double amount = 1000.0;
        double wrongAmount = 2000.0;
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", amount, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByAmount(amount)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAmount(wrongAmount);

        // then
        assertThat(foundAccount).isNull();
    }

    // 6. Prueba para verificar que el monto de la cuenta es correcto
    @Test
    public void whenFindByAmount_thenCheckAccountAmount() {
        // given
        double amount = 1000.0;
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", amount, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByAmount(amount)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAmount(amount);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getAmount()).isEqualTo(amount);
    }

    // 7. Prueba para verificar que el método maneja correctamente montos negativos
    @Test
    public void whenFindByNegativeAmount_thenReturnNull() {
        // given
        double amount = -500.0;
        given(clientBankAccountRepository.findByAmount(amount)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAmount(amount);

        // then
        assertThat(foundAccount).isNull();
    }

    // 1. Prueba cuando se encuentra una cuenta bancaria por transacción válida
    @Test
    public void whenFindByTransaction_thenReturnAccount() {
        // given
        String transaction = "Deposit";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, transaction, LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByTransaction(transaction)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByTransaction(transaction);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getTransaction()).isEqualTo(transaction);
    }

    // 2. Prueba cuando no se encuentra una cuenta bancaria por transacción
    @Test
    public void whenFindByTransaction_thenReturnNull() {
        // given
        String transaction = "Withdrawal";
        given(clientBankAccountRepository.findByTransaction(transaction)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByTransaction(transaction);

        // then
        assertThat(foundAccount).isNull();
    }

    // 3. Prueba cuando la transacción es nula
    @Test
    public void whenTransactionIsNull_thenReturnNull() {
        // given
        String transaction = null;
        given(clientBankAccountRepository.findByTransaction(transaction)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByTransaction(transaction);

        // then
        assertThat(foundAccount).isNull();
    }

    // 4. Prueba cuando la transacción es una cadena vacía
    @Test
    public void whenTransactionIsEmpty_thenReturnNull() {
        // given
        String transaction = "";
        given(clientBankAccountRepository.findByTransaction(transaction)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByTransaction(transaction);

        // then
        assertThat(foundAccount).isNull();
    }

    // 5. Prueba para verificar que el repositorio es llamado una sola vez
    @Test
    public void whenFindByTransaction_thenRepositoryMethodCalledOnce() {
        // given
        String transaction = "Deposit";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, transaction, LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByTransaction(transaction)).willReturn(account);

        // when
        clientBankAccountService.findByTransaction(transaction);

        // then
        verify(clientBankAccountRepository, times(1)).findByTransaction(transaction);
    }

    // 6. Prueba para verificar que la cuenta devuelta tiene la transacción correcta
    @Test
    public void whenFindByTransaction_thenCheckAccountTransaction() {
        // given
        String transaction = "Deposit";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, transaction, LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByTransaction(transaction)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByTransaction(transaction);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getTransaction()).isEqualTo(transaction);
    }

    // 7. Prueba para manejar transacciones en mayúsculas o minúsculas (si es relevante)
    @Test
    public void whenFindByTransactionWithDifferentCase_thenReturnAccount() {
        // given
        String transaction = "deposit";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByTransaction(transaction)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByTransaction(transaction);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getTransaction()).isEqualTo("Deposit");
    }

    // 1. Prueba cuando se encuentra una cuenta bancaria por fecha de transacción válida
    @Test
    public void whenFindByTransactionDate_thenReturnAccount() {
        // given
        String transactionDate = "2024-10-01"; // Formato: yyyy-MM-dd
        LocalDate localDate = LocalDate.parse(transactionDate);
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", localDate, LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByTransactionDate(transactionDate)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByTransactionDate(transactionDate);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getTransaction_date()).isEqualTo(localDate);
    }

    // 2. Prueba cuando no se encuentra una cuenta bancaria por fecha de transacción
    @Test
    public void whenFindByTransactionDate_thenReturnNull() {
        // given
        String transactionDate = "2024-10-01";
        given(clientBankAccountRepository.findByTransactionDate(transactionDate)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByTransactionDate(transactionDate);

        // then
        assertThat(foundAccount).isNull();
    }

    // 3. Prueba cuando la fecha de transacción es nula
    @Test
    public void whenTransactionDateIsNull_thenReturnNull() {
        // given
        String transactionDate = null;
        given(clientBankAccountRepository.findByTransactionDate(transactionDate)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByTransactionDate(transactionDate);

        // then
        assertThat(foundAccount).isNull();
    }

    // 4. Prueba cuando la fecha de transacción es una cadena vacía
    @Test
    public void whenTransactionDateIsEmpty_thenReturnNull() {
        // given
        String transactionDate = "";
        given(clientBankAccountRepository.findByTransactionDate(transactionDate)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByTransactionDate(transactionDate);

        // then
        assertThat(foundAccount).isNull();
    }

    // 5. Prueba para verificar que el repositorio es llamado una sola vez
    @Test
    public void whenFindByTransactionDate_thenRepositoryMethodCalledOnce() {
        // given
        String transactionDate = "2024-10-01";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByTransactionDate(transactionDate)).willReturn(account);

        // when
        clientBankAccountService.findByTransactionDate(transactionDate);

        // then
        verify(clientBankAccountRepository, times(1)).findByTransactionDate(transactionDate);
    }

    // 6. Prueba para verificar que la cuenta devuelta tiene la fecha de transacción correcta
    @Test
    public void whenFindByTransactionDate_thenCheckAccountTransactionDate() {
        // given
        String transactionDate = "2024-10-01";
        LocalDate localDate = LocalDate.parse(transactionDate);
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", localDate, LocalDate.of(2020, 1, 1));
        given(clientBankAccountRepository.findByTransactionDate(transactionDate)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByTransactionDate(transactionDate);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getTransaction_date()).isEqualTo(localDate);
    }

    // 7. Prueba para verificar que la fecha de transacción se puede manejar con distintos formatos (si es relevante)
    @Test
    public void whenFindByTransactionDateWithDifferentFormat_thenReturnAccount() {
        // given
        String transactionDate = "01-10-2024"; // Formato: dd-MM-yyyy
        LocalDate localDate = LocalDate.parse("2024-10-01"); // Se espera que sea convertido
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", localDate, LocalDate.of(2020, 1, 1));
        // El repositorio debería manejar el formato diferente. Esto dependerá de la implementación.
        given(clientBankAccountRepository.findByTransactionDate(localDate.toString())).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByTransactionDate(localDate.toString());

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getTransaction_date()).isEqualTo(localDate);
    }

    // 1. Prueba cuando se encuentra una cuenta bancaria por fecha de apertura válida
    @Test
    public void whenFindByAccountOpening_thenReturnAccount() {
        // given
        String accountOpeningDate = "2020-01-01"; // Formato: yyyy-MM-dd
        LocalDate localDate = LocalDate.parse(accountOpeningDate);
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), localDate);
        given(clientBankAccountRepository.findByAccountOpening(accountOpeningDate)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAccountOpening(accountOpeningDate);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getAccount_opening()).isEqualTo(localDate);
    }

    // 2. Prueba cuando no se encuentra una cuenta bancaria por fecha de apertura
    @Test
    public void whenFindByAccountOpening_thenReturnNull() {
        // given
        String accountOpeningDate = "2020-01-01";
        given(clientBankAccountRepository.findByAccountOpening(accountOpeningDate)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAccountOpening(accountOpeningDate);

        // then
        assertThat(foundAccount).isNull();
    }

    // 3. Prueba cuando la fecha de apertura es nula
    @Test
    public void whenAccountOpeningIsNull_thenReturnNull() {
        // given
        String accountOpeningDate = null;
        given(clientBankAccountRepository.findByAccountOpening(accountOpeningDate)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAccountOpening(accountOpeningDate);

        // then
        assertThat(foundAccount).isNull();
    }

    // 4. Prueba cuando la fecha de apertura es una cadena vacía
    @Test
    public void whenAccountOpeningIsEmpty_thenReturnNull() {
        // given
        String accountOpeningDate = "";
        given(clientBankAccountRepository.findByAccountOpening(accountOpeningDate)).willReturn(null);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAccountOpening(accountOpeningDate);

        // then
        assertThat(foundAccount).isNull();
    }

    // 5. Prueba para verificar que el repositorio es llamado una sola vez
    @Test
    public void whenFindByAccountOpening_thenRepositoryMethodCalledOnce() {
        // given
        String accountOpeningDate = "2020-01-01";
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), LocalDate.parse(accountOpeningDate));
        given(clientBankAccountRepository.findByAccountOpening(accountOpeningDate)).willReturn(account);

        // when
        clientBankAccountService.findByAccountOpening(accountOpeningDate);

        // then
        verify(clientBankAccountRepository, times(1)).findByAccountOpening(accountOpeningDate);
    }

    // 6. Prueba para verificar que la cuenta devuelta tiene la fecha de apertura correcta
    @Test
    public void whenFindByAccountOpening_thenCheckAccountOpeningDate() {
        // given
        String accountOpeningDate = "2020-01-01";
        LocalDate localDate = LocalDate.parse(accountOpeningDate);
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), localDate);
        given(clientBankAccountRepository.findByAccountOpening(accountOpeningDate)).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAccountOpening(accountOpeningDate);

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getAccount_opening()).isEqualTo(localDate);
    }

    // 7. Prueba para verificar que la fecha de apertura se puede manejar con distintos formatos (si es relevante)
    @Test
    public void whenFindByAccountOpeningWithDifferentFormat_thenReturnAccount() {
        // given
        String accountOpeningDate = "01-01-2020"; // Formato: dd-MM-yyyy
        LocalDate localDate = LocalDate.parse("2020-01-01"); // Se espera que sea convertido
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "12345", 1000.0, "Deposit", LocalDate.now(), localDate);
        // El repositorio debería manejar el formato diferente. Esto dependerá de la implementación.
        given(clientBankAccountRepository.findByAccountOpening(localDate.toString())).willReturn(account);

        // when
        ClientBankAccountEntity foundAccount = clientBankAccountService.findByAccountOpening(localDate.toString());

        // then
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getAccount_opening()).isEqualTo(localDate);
    }

    // 1. Prueba cuando existe una cuenta bancaria para un cliente dado
    @Test
    public void whenExistsByClientIdAndAccountExists_thenReturnTrue() {
        // given
        Long clientId = 1L;
        given(clientBankAccountRepository.existsByClientId(clientId)).willReturn(true);

        // when
        boolean exists = clientBankAccountService.existsByClientId(clientId);

        // then
        assertThat(exists).isTrue();
    }

    // 2. Prueba cuando no existe ninguna cuenta bancaria para un cliente dado
    @Test
    public void whenExistsByClientIdAndAccountDoesNotExist_thenReturnFalse() {
        // given
        Long clientId = 1L;
        given(clientBankAccountRepository.existsByClientId(clientId)).willReturn(false);

        // when
        boolean exists = clientBankAccountService.existsByClientId(clientId);

        // then
        assertThat(exists).isFalse();
    }

    // 3. Prueba cuando el ID del cliente es nulo
    @Test
    public void whenClientIdIsNull_thenReturnFalse() {
        // given
        Long clientId = null;

        // when
        boolean exists = clientBankAccountService.existsByClientId(clientId);

        // then
        assertThat(exists).isFalse(); // Suponiendo que null no debería devolver verdadero
    }

    // 4. Prueba cuando se llama al repositorio una sola vez
    @Test
    public void whenExistsByClientId_thenRepositoryMethodCalledOnce() {
        // given
        Long clientId = 1L;
        given(clientBankAccountRepository.existsByClientId(clientId)).willReturn(true);

        // when
        clientBankAccountService.existsByClientId(clientId);

        // then
        verify(clientBankAccountRepository, times(1)).existsByClientId(clientId);
    }

    // 5. Prueba cuando se verifica la existencia de una cuenta bancaria para un cliente diferente
    @Test
    public void whenExistsByDifferentClientId_thenReturnFalse() {
        // given
        Long clientId = 2L;
        given(clientBankAccountRepository.existsByClientId(clientId)).willReturn(false);

        // when
        boolean exists = clientBankAccountService.existsByClientId(clientId);

        // then
        assertThat(exists).isFalse();
    }

    // 6. Prueba para asegurar que el método maneja IDs negativos correctamente
    @Test
    public void whenClientIdIsNegative_thenReturnFalse() {
        // given
        Long clientId = -1L;

        // when
        boolean exists = clientBankAccountService.existsByClientId(clientId);

        // then
        assertThat(exists).isFalse(); // Suponiendo que un ID negativo no debería devolver verdadero
    }

    // 7. Prueba para verificar que el método maneja ID de cliente de forma válida
    @Test
    public void whenClientIdIsValid_thenCheckReturnValue() {
        // given
        Long clientId = 3L;
        given(clientBankAccountRepository.existsByClientId(clientId)).willReturn(true);

        // when
        boolean exists = clientBankAccountService.existsByClientId(clientId);

        // then
        assertThat(exists).isTrue(); // Asegura que el método devuelve verdadero para un cliente existente
    }

    // 1. Prueba cuando existe al menos una cuenta bancaria para un nombre de banco dado
    @Test
    public void whenExistsByBankNameAndBankExists_thenReturnTrue() {
        // given
        String bankName = "Bank A";
        given(clientBankAccountRepository.existsByBankName(bankName)).willReturn(true);

        // when
        boolean exists = clientBankAccountService.existsByBankName(bankName);

        // then
        assertThat(exists).isTrue();
    }

    // 2. Prueba cuando no existe ninguna cuenta bancaria para un nombre de banco dado
    @Test
    public void whenExistsByBankNameAndBankDoesNotExist_thenReturnFalse() {
        // given
        String bankName = "Bank B";
        given(clientBankAccountRepository.existsByBankName(bankName)).willReturn(false);

        // when
        boolean exists = clientBankAccountService.existsByBankName(bankName);

        // then
        assertThat(exists).isFalse();
    }

    // 3. Prueba cuando el nombre del banco es nulo
    @Test
    public void whenBankNameIsNull_thenReturnFalse() {
        // given
        String bankName = null;

        // when
        boolean exists = clientBankAccountService.existsByBankName(bankName);

        // then
        assertThat(exists).isFalse(); // Suponiendo que un nombre nulo no debería devolver verdadero
    }

    // 4. Prueba para verificar que el repositorio es llamado una sola vez
    @Test
    public void whenExistsByBankName_thenRepositoryMethodCalledOnce() {
        // given
        String bankName = "Bank A";
        given(clientBankAccountRepository.existsByBankName(bankName)).willReturn(true);

        // when
        clientBankAccountService.existsByBankName(bankName);

        // then
        verify(clientBankAccountRepository, times(1)).existsByBankName(bankName);
    }

    // 5. Prueba cuando se verifica la existencia de un nombre de banco diferente
    @Test
    public void whenExistsByDifferentBankName_thenReturnFalse() {
        // given
        String bankName = "Bank C";
        given(clientBankAccountRepository.existsByBankName(bankName)).willReturn(false);

        // when
        boolean exists = clientBankAccountService.existsByBankName(bankName);

        // then
        assertThat(exists).isFalse();
    }

    // 6. Prueba para asegurar que el método maneja nombres de bancos vacíos correctamente
    @Test
    public void whenBankNameIsEmpty_thenReturnFalse() {
        // given
        String bankName = "";

        // when
        boolean exists = clientBankAccountService.existsByBankName(bankName);

        // then
        assertThat(exists).isFalse(); // Suponiendo que un nombre vacío no debería devolver verdadero
    }

    // 7. Prueba para verificar que el método maneja nombres de bancos de forma válida
    @Test
    public void whenBankNameIsValid_thenCheckReturnValue() {
        // given
        String bankName = "Bank D";
        given(clientBankAccountRepository.existsByBankName(bankName)).willReturn(true);

        // when
        boolean exists = clientBankAccountService.existsByBankName(bankName);

        // then
        assertThat(exists).isTrue(); // Asegura que el método devuelve verdadero para un banco existente
    }







}
