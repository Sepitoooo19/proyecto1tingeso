package com.example.PrestaBanco.services;

import com.example.PrestaBanco.entities.DebtEntity;
import com.example.PrestaBanco.repositories.DebtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
public class DebtServiceTest {

    @Mock
    private DebtRepository debtRepository;

    @InjectMocks
    private DebtService debtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Prueba cuando no hay deudas
    @Test
    public void whenFindAll_thenReturnEmptyList() {
        // given
        given(debtRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<DebtEntity> foundDebts = debtService.findAll();

        // then
        assertThat(foundDebts).isEmpty();
        verify(debtRepository).findAll();
    }

    // 2. Prueba cuando hay deudas en la base de datos
    @Test
    public void whenFindAll_thenReturnDebtList() {
        // given
        DebtEntity debt = new DebtEntity(1L, 123L, 5000.0, "Personal", "2024-01-01", "Pending");
        given(debtRepository.findAll()).willReturn(List.of(debt));

        // when
        List<DebtEntity> foundDebts = debtService.findAll();

        // then
        assertThat(foundDebts).isNotEmpty();
        assertThat(foundDebts.size()).isEqualTo(1);
        assertThat(foundDebts.get(0).getDebt_id()).isEqualTo(1L);
        verify(debtRepository).findAll();
    }

    // 3. Prueba cuando hay múltiples deudas en la base de datos
    @Test
    public void whenFindAll_thenReturnMultipleDebts() {
        // given
        DebtEntity debt1 = new DebtEntity(1L, 123L, 5000.0, "Personal", "2024-01-01", "Pending");
        DebtEntity debt2 = new DebtEntity(2L, 124L, 1500.0, "Mortgage", "2024-02-01", "Paid");
        given(debtRepository.findAll()).willReturn(List.of(debt1, debt2));

        // when
        List<DebtEntity> foundDebts = debtService.findAll();

        // then
        assertThat(foundDebts).hasSize(2);
        verify(debtRepository).findAll();
    }

    // 4. Prueba con un fallo simulado al obtener las deudas
    @Test
    public void whenFindAll_thenHandleException() {
        // given
        given(debtRepository.findAll()).willThrow(new RuntimeException("Database error"));

        // when / then
        try {
            debtService.findAll();
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Database error");
        }
        verify(debtRepository).findAll();
    }

    // Pruebas para el método findByDebtId()

    // 1. Prueba cuando la deuda no existe
    @Test
    public void whenFindByDebtId_thenReturnNull() {
        // given
        Long debtId = 1L;
        given(debtRepository.findByDebtId(debtId)).willReturn(null);

        // when
        DebtEntity foundDebt = debtService.findByDebtId(debtId);

        // then
        assertThat(foundDebt).isNull();
        verify(debtRepository).findByDebtId(debtId);
    }

    // 2. Prueba cuando la deuda existe
    @Test
    public void whenFindByDebtId_thenReturnDebt() {
        // given
        Long debtId = 1L;
        DebtEntity debt = new DebtEntity(debtId, 123L, 5000.0, "Personal", "2024-01-01", "Pending");
        given(debtRepository.findByDebtId(debtId)).willReturn(debt);

        // when
        DebtEntity foundDebt = debtService.findByDebtId(debtId);

        // then
        assertThat(foundDebt).isNotNull();
        assertThat(foundDebt.getDebt_id()).isEqualTo(debtId);
        verify(debtRepository).findByDebtId(debtId);
    }

    // 3. Prueba con un ID nulo
    @Test
    public void whenFindByDebtIdWithNullId_thenReturnNull() {
        // given
        given(debtRepository.findByDebtId(null)).willReturn(null);

        // when
        DebtEntity foundDebt = debtService.findByDebtId(null);

        // then
        assertThat(foundDebt).isNull();
        verify(debtRepository).findByDebtId(null);
    }

    // 4. Prueba con un fallo simulado al buscar por ID
    @Test
    public void whenFindByDebtId_thenHandleException() {
        // given
        Long debtId = 1L;
        given(debtRepository.findByDebtId(debtId)).willThrow(new RuntimeException("Database error"));

        // when / then
        try {
            debtService.findByDebtId(debtId);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Database error");
        }
        verify(debtRepository).findByDebtId(debtId);
    }

    // Pruebas para findByDebtAmount()

    @Test
    public void whenFindByDebtAmount_thenReturnDebt() {
        double amount = 5000.0;
        DebtEntity debt = new DebtEntity(1L, 123L, amount, "Personal", "2024-01-01", "Pending");
        given(debtRepository.findByDebtAmount(amount)).willReturn(debt);

        DebtEntity foundDebt = debtService.findByDebtAmount(amount);

        assertThat(foundDebt).isNotNull();
        assertThat(foundDebt.getDebt_amount()).isEqualTo(amount);
        verify(debtRepository).findByDebtAmount(amount);
    }

    @Test
    public void whenFindByDebtAmount_thenReturnNull() {
        double amount = 5000.0;
        given(debtRepository.findByDebtAmount(amount)).willReturn(null);

        DebtEntity foundDebt = debtService.findByDebtAmount(amount);

        assertThat(foundDebt).isNull();
        verify(debtRepository).findByDebtAmount(amount);
    }

    @Test
    public void whenFindByDebtAmount_thenReturnCorrectEntity() {
        double amount = 3000.0;
        DebtEntity debt = new DebtEntity(2L, 124L, amount, "Mortgage", "2024-02-01", "Paid");
        given(debtRepository.findByDebtAmount(amount)).willReturn(debt);

        DebtEntity foundDebt = debtService.findByDebtAmount(amount);

        assertThat(foundDebt.getDebt_id()).isEqualTo(2L);
        verify(debtRepository).findByDebtAmount(amount);
    }

    @Test
    public void whenFindByDebtAmount_givenNegativeValue_thenReturnNull() {
        double amount = -100.0;
        given(debtRepository.findByDebtAmount(amount)).willReturn(null);

        DebtEntity foundDebt = debtService.findByDebtAmount(amount);

        assertThat(foundDebt).isNull();
        verify(debtRepository).findByDebtAmount(amount);
    }

    // Pruebas para findByDebtType()

    @Test
    public void whenFindByDebtType_thenReturnDebt() {
        String type = "Personal";
        DebtEntity debt = new DebtEntity(1L, 123L, 5000.0, type, "2024-01-01", "Pending");
        given(debtRepository.findByDebtType(type)).willReturn(debt);

        DebtEntity foundDebt = debtService.findByDebtType(type);

        assertThat(foundDebt).isNotNull();
        assertThat(foundDebt.getDebt_type()).isEqualTo(type);
        verify(debtRepository).findByDebtType(type);
    }

    @Test
    public void whenFindByDebtType_thenReturnNull() {
        String type = "Unknown";
        given(debtRepository.findByDebtType(type)).willReturn(null);

        DebtEntity foundDebt = debtService.findByDebtType(type);

        assertThat(foundDebt).isNull();
        verify(debtRepository).findByDebtType(type);
    }

    @Test
    public void whenFindByDebtType_givenEmptyString_thenReturnNull() {
        String type = "";
        given(debtRepository.findByDebtType(type)).willReturn(null);

        DebtEntity foundDebt = debtService.findByDebtType(type);

        assertThat(foundDebt).isNull();
        verify(debtRepository).findByDebtType(type);
    }

    @Test
    public void whenFindByDebtType_thenReturnCorrectDebtEntity() {
        String type = "Loan";
        DebtEntity debt = new DebtEntity(3L, 125L, 4000.0, type, "2024-03-01", "Approved");
        given(debtRepository.findByDebtType(type)).willReturn(debt);

        DebtEntity foundDebt = debtService.findByDebtType(type);

        assertThat(foundDebt.getDebt_id()).isEqualTo(3L);
        verify(debtRepository).findByDebtType(type);
    }

    // Pruebas para findByDebtDate()

    @Test
    public void whenFindByDebtDate_thenReturnDebt() {
        String date = "2024-01-01";
        DebtEntity debt = new DebtEntity(1L, 123L, 5000.0, "Personal", date, "Pending");
        given(debtRepository.findByDebtDate(date)).willReturn(debt);

        DebtEntity foundDebt = debtService.findByDebtDate(date);

        assertThat(foundDebt).isNotNull();
        assertThat(foundDebt.getDebt_date()).isEqualTo(date);
        verify(debtRepository).findByDebtDate(date);
    }

    @Test
    public void whenFindByDebtDate_thenReturnNull() {
        String date = "2025-01-01";
        given(debtRepository.findByDebtDate(date)).willReturn(null);

        DebtEntity foundDebt = debtService.findByDebtDate(date);

        assertThat(foundDebt).isNull();
        verify(debtRepository).findByDebtDate(date);
    }

    @Test
    public void whenFindByDebtDate_givenInvalidDate_thenReturnNull() {
        String date = "invalid-date";
        given(debtRepository.findByDebtDate(date)).willReturn(null);

        DebtEntity foundDebt = debtService.findByDebtDate(date);

        assertThat(foundDebt).isNull();
        verify(debtRepository).findByDebtDate(date);
    }

    @Test
    public void whenFindByDebtDate_thenReturnCorrectDebtEntity() {
        String date = "2024-02-01";
        DebtEntity debt = new DebtEntity(2L, 124L, 3000.0, "Mortgage", date, "Paid");
        given(debtRepository.findByDebtDate(date)).willReturn(debt);

        DebtEntity foundDebt = debtService.findByDebtDate(date);

        assertThat(foundDebt.getDebt_id()).isEqualTo(2L);
        verify(debtRepository).findByDebtDate(date);
    }

    // Pruebas para findByDebtStatus()

    @Test
    public void whenFindByDebtStatus_thenReturnDebt() {
        String status = "Pending";
        DebtEntity debt = new DebtEntity(1L, 123L, 5000.0, "Personal", "2024-01-01", status);
        given(debtRepository.findByDebtStatus(status)).willReturn(debt);

        DebtEntity foundDebt = debtService.findByDebtStatus(status);

        assertThat(foundDebt).isNotNull();
        assertThat(foundDebt.getDebt_status()).isEqualTo(status);
        verify(debtRepository).findByDebtStatus(status);
    }

    @Test
    public void whenFindByDebtStatus_thenReturnNull() {
        String status = "Closed";
        given(debtRepository.findByDebtStatus(status)).willReturn(null);

        DebtEntity foundDebt = debtService.findByDebtStatus(status);

        assertThat(foundDebt).isNull();
        verify(debtRepository).findByDebtStatus(status);
    }

    @Test
    public void whenFindByDebtStatus_givenEmptyString_thenReturnNull() {
        String status = "";
        given(debtRepository.findByDebtStatus(status)).willReturn(null);

        DebtEntity foundDebt = debtService.findByDebtStatus(status);

        assertThat(foundDebt).isNull();
        verify(debtRepository).findByDebtStatus(status);
    }

    @Test
    public void whenFindByDebtStatus_thenReturnCorrectDebtEntity() {
        String status = "Approved";
        DebtEntity debt = new DebtEntity(3L, 125L, 4000.0, "Loan", "2024-03-01", status);
        given(debtRepository.findByDebtStatus(status)).willReturn(debt);

        DebtEntity foundDebt = debtService.findByDebtStatus(status);

        assertThat(foundDebt.getDebt_id()).isEqualTo(3L);
        verify(debtRepository).findByDebtStatus(status);
    }

    // Pruebas para existsByClientId()

    @Test
    public void whenExistsByClientId_thenReturnTrue() {
        Long clientId = 123L;
        given(debtRepository.existsByClientId(clientId)).willReturn(true);

        boolean exists = debtService.existsByClientId(clientId);

        assertThat(exists).isTrue();
        verify(debtRepository).existsByClientId(clientId);
    }

    @Test
    public void whenExistsByClientId_thenReturnFalse() {
        Long clientId = 999L;
        given(debtRepository.existsByClientId(clientId)).willReturn(false);

        boolean exists = debtService.existsByClientId(clientId);

        assertThat(exists).isFalse();
        verify(debtRepository).existsByClientId(clientId);
    }

    // 1. Prueba cuando no hay deudas asociadas al client_id
    @Test
    public void whenFindByClientId_thenReturnEmptyList() {
        // given
        Long clientId = 123L;
        given(debtRepository.findByClientId(clientId)).willReturn(Collections.emptyList());

        // when
        List<DebtEntity> foundDebts = debtService.findByClientId(clientId);

        // then
        assertThat(foundDebts).isEmpty();
        verify(debtRepository).findByClientId(clientId);
    }

    // 2. Prueba cuando hay una sola deuda asociada al client_id
    @Test
    public void whenFindByClientId_thenReturnSingleDebt() {
        // given
        Long clientId = 123L;
        DebtEntity debt = new DebtEntity(1L, clientId, 5000.0, "Personal", "2024-01-01", "Pending");
        given(debtRepository.findByClientId(clientId)).willReturn(List.of(debt));

        // when
        List<DebtEntity> foundDebts = debtService.findByClientId(clientId);

        // then
        assertThat(foundDebts).isNotEmpty();
        assertThat(foundDebts.size()).isEqualTo(1);
        assertThat(foundDebts.get(0).getClient_id()).isEqualTo(clientId);
        verify(debtRepository).findByClientId(clientId);
    }

    // 3. Prueba cuando hay múltiples deudas asociadas al client_id
    @Test
    public void whenFindByClientId_thenReturnMultipleDebts() {
        // given
        Long clientId = 123L;
        DebtEntity debt1 = new DebtEntity(1L, clientId, 5000.0, "Personal", "2024-01-01", "Pending");
        DebtEntity debt2 = new DebtEntity(2L, clientId, 2500.0, "Car", "2024-02-01", "Paid");
        given(debtRepository.findByClientId(clientId)).willReturn(List.of(debt1, debt2));

        // when
        List<DebtEntity> foundDebts = debtService.findByClientId(clientId);

        // then
        assertThat(foundDebts).hasSize(2);
        assertThat(foundDebts.get(0).getClient_id()).isEqualTo(clientId);
        assertThat(foundDebts.get(1).getClient_id()).isEqualTo(clientId);
        verify(debtRepository).findByClientId(clientId);
    }

    // 4. Prueba con un fallo simulado al buscar deudas por client_id
    @Test
    public void whenFindByClientId_thenHandleException() {
        // given
        Long clientId = 123L;
        given(debtRepository.findByClientId(clientId)).willThrow(new RuntimeException("Database error"));

        // when / then
        try {
            debtService.findByClientId(clientId);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Database error");
        }
        verify(debtRepository).findByClientId(clientId);
    }


}
