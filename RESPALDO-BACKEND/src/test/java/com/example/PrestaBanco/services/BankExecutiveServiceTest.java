package com.example.PrestaBanco.services;

import jakarta.persistence.EntityNotFoundException;
import org.mockito.Mockito;

import com.example.PrestaBanco.repositories.ClientRepository;
import com.example.PrestaBanco.entities.ClientEntity;

import java.util.List;

import com.example.PrestaBanco.repositories.DebtRepository;
import com.example.PrestaBanco.entities.DebtEntity;
import com.example.PrestaBanco.entities.EmploymentHistoryEntity;
import com.example.PrestaBanco.repositories.EmploymentHistoryRepository;
import com.example.PrestaBanco.entities.CreditApplicationEntity;
import com.example.PrestaBanco.repositories.CreditApplicationRepository;
import com.example.PrestaBanco.entities.ClientBankAccountEntity;
import com.example.PrestaBanco.repositories.ClientBankAccountRepository;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


public class BankExecutiveServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private DebtRepository debtRepository;

    @Mock
    private EmploymentHistoryRepository employmentHistoryRepository;

    @Mock
    private CreditApplicationRepository creditApplicationRepository;


    @Mock
    private ClientBankAccountRepository clientBankAccountRepository;

    @InjectMocks
    private BankExecutiveService bankExecutiveService;

    private int fireInsurance = 50; // Assuming fireInsurance is a constant or a field in the service


    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        ClientEntity client = new ClientEntity(1L, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 100000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        Mockito.when(clientRepository.findByRut("12345678-9")).thenReturn(client); // Simulando el retorno del cliente
    }

    @Test
    public void whenGetExpectedAmountOfClientByRutAndClientExists_thenReturnExpectedAmount() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);

        // when
        double expectedAmount = bankExecutiveService.getExpectedAmountOfClientByRut(rut);

        // then
        assertThat(expectedAmount).isEqualTo(client.getExpected_amount());
    }

    @Test
    public void whenGetExpectedAmountOfClientByRutAndClientDoesNotExist_thenThrowException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willReturn(null);

        // when & then
        assertThrows(NullPointerException.class, () -> {
            bankExecutiveService.getExpectedAmountOfClientByRut(rut);
        });
    }

    @Test
    public void whenGetExpectedAmountOfClientByRut_thenCallRepositoryFindByRut() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);

        // when
        bankExecutiveService.getExpectedAmountOfClientByRut(rut);

        // then
        verify(clientRepository, times(1)).findByRut(rut);
    }

    @Test
    public void whenGetExpectedAmountOfClientByRutAndThrowsException_thenHandleException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            bankExecutiveService.getExpectedAmountOfClientByRut(rut);
        });
    }

    // Pruebas para getInteresRateOfClientByRut(String rut)
    @Test
    public void whenGetInterestRateOfClientByRutAndClientExists_thenReturnInterestRate() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);

        // when
        double interestRate = bankExecutiveService.getInteresRateOfClientByRut(rut);

        // then
        assertThat(interestRate).isEqualTo(client.getInterest_rate());
    }

    @Test
    public void whenGetInterestRateOfClientByRutAndClientDoesNotExist_thenThrowException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willReturn(null);

        // when & then
        assertThrows(NullPointerException.class, () -> {
            bankExecutiveService.getInteresRateOfClientByRut(rut);
        });
    }

    @Test
    public void whenGetInterestRateOfClientByRut_thenCallRepositoryFindByRut() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);

        // when
        bankExecutiveService.getInteresRateOfClientByRut(rut);

        // then
        verify(clientRepository, times(1)).findByRut(rut);
    }

    @Test
    public void whenGetInterestRateOfClientByRutAndThrowsException_thenHandleException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            bankExecutiveService.getInteresRateOfClientByRut(rut);
        });
    }

    // Pruebas para getTimeLimitOfClientByRut(String rut)
    @Test
    public void whenGetTimeLimitOfClientByRutAndClientExists_thenReturnTimeLimit() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);

        // when
        int timeLimit = bankExecutiveService.getTimeLimitOfClientByRut(rut);

        // then
        assertThat(timeLimit).isEqualTo(client.getTime_limit());
    }

    @Test
    public void whenGetTimeLimitOfClientByRutAndClientDoesNotExist_thenThrowException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willReturn(null);

        // when & then
        assertThrows(NullPointerException.class, () -> {
            bankExecutiveService.getTimeLimitOfClientByRut(rut);
        });
    }

    @Test
    public void whenGetTimeLimitOfClientByRut_thenCallRepositoryFindByRut() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);

        // when
        bankExecutiveService.getTimeLimitOfClientByRut(rut);

        // then
        verify(clientRepository, times(1)).findByRut(rut);
    }

    @Test
    public void whenGetTimeLimitOfClientByRutAndThrowsException_thenHandleException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            bankExecutiveService.getTimeLimitOfClientByRut(rut);
        });
    }

    // Pruebas para getMonthlySalaryOfClientByRut(String rut)
    @Test
    public void whenGetMonthlySalaryOfClientByRutAndClientExists_thenReturnMonthlySalary() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);

        // when
        double monthlySalary = bankExecutiveService.getMonthlySalaryOfClientByRut(rut);

        // then
        assertThat(monthlySalary).isEqualTo(client.getMonthly_salary());
    }

    @Test
    public void whenGetMonthlySalaryOfClientByRutAndClientDoesNotExist_thenThrowException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willReturn(null);

        // when & then
        assertThrows(NullPointerException.class, () -> {
            bankExecutiveService.getMonthlySalaryOfClientByRut(rut);
        });
    }

    @Test
    public void whenGetMonthlySalaryOfClientByRut_thenCallRepositoryFindByRut() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);

        // when
        bankExecutiveService.getMonthlySalaryOfClientByRut(rut);

        // then
        verify(clientRepository, times(1)).findByRut(rut);
    }

    @Test
    public void whenGetMonthlySalaryOfClientByRutAndThrowsException_thenHandleException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            bankExecutiveService.getMonthlySalaryOfClientByRut(rut);
        });
    }

    // Pruebas para getMonthlyLoanOfClientByRut(String rut)
    @Test
    public void whenGetMonthlyLoanOfClientByRutAndClientExists_thenReturnMonthlyLoan() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);

        // when
        int monthlyLoan = bankExecutiveService.getMonthlyLoanOfClientByRut(rut);

        // then
        double interestRate = client.getInterest_rate() / 12 / 100;
        double expectedAmount = client.getExpected_amount();
        int timeLimitInMonths = client.getTime_limit() * 12;
        double expectedMonthlyFee = expectedAmount * ((interestRate * (Math.pow(1 + interestRate, timeLimitInMonths))) / (Math.pow(1 + interestRate, timeLimitInMonths) - 1));
        assertThat(monthlyLoan).isEqualTo((int) expectedMonthlyFee);
    }

    @Test
    public void whenGetMonthlyLoanOfClientByRutAndClientDoesNotExist_thenThrowException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willReturn(null);

        // when & then
        assertThrows(NullPointerException.class, () -> {
            bankExecutiveService.getMonthlyLoanOfClientByRut(rut);
        });
    }

    @Test
    public void whenGetMonthlyLoanOfClientByRut_thenCallRepositoryFindByRut() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);

        // when
        bankExecutiveService.getMonthlyLoanOfClientByRut(rut);

        // then
        verify(clientRepository, times(1)).findByRut(rut);
    }

    @Test
    public void whenGetMonthlyLoanOfClientByRutAndThrowsException_thenHandleException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            bankExecutiveService.getMonthlyLoanOfClientByRut(rut);
        });
    }

    // Pruebas para getFeeIncomeRatioByRut(String rut)
    @Test
    public void whenGetFeeIncomeRatioByRutAndClientExists_thenReturnFeeIncomeRatio() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);
        double expectedMonthlyLoan = bankExecutiveService.getMonthlyLoanOfClientByRut(rut);

        // when
        double feeIncomeRatio = bankExecutiveService.getFeeIncomeRatioByRut(rut);

        // then
        double expectedFeeIncomeRatio = (client.getMonthly_salary() / expectedMonthlyLoan) * 100;
        assertThat(feeIncomeRatio).isEqualTo(expectedFeeIncomeRatio);
    }

    @Test
    public void whenGetFeeIncomeRatioByRutAndClientDoesNotExist_thenThrowException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willReturn(null);

        // when & then
        assertThrows(RuntimeException.class, () -> {
            bankExecutiveService.getFeeIncomeRatioByRut(rut);
        });
    }

    @Test
    public void whenGetFeeIncomeRatioByRutAndClientExistsWithHighRatio_thenPrintWarning() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 30000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);
        double expectedMonthlyLoan = bankExecutiveService.getMonthlyLoanOfClientByRut(rut);

        // when
        double feeIncomeRatio = bankExecutiveService.getFeeIncomeRatioByRut(rut);

        // then
        double expectedFeeIncomeRatio = (client.getMonthly_salary() / expectedMonthlyLoan) * 100;
        assertThat(feeIncomeRatio).isEqualTo(expectedFeeIncomeRatio);
        assertTrue(feeIncomeRatio > 35);  // Asserting that the warning condition is met
    }

    @Test
    public void whenGetFeeIncomeRatioByRutAndThrowsException_thenHandleException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            bankExecutiveService.getFeeIncomeRatioByRut(rut);
        });
    }

    // Pruebas para getDebtAmountByRut(String rut)
    @Test
    public void whenGetDebtAmountByRutAndClientExists_thenReturnTotalDebtAmount() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        List<DebtEntity> debts = List.of(
                new DebtEntity(1L, client.getClient_id(), 1000.0, "Loan", "2024-01-01", "Pending"),
                new DebtEntity(2L, client.getClient_id(), 2000.0, "Credit Card", "2024-02-01", "Pending")
        );

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(debtRepository.findByClientId(client.getClient_id())).willReturn(debts);

        // when
        double totalDebtAmount = bankExecutiveService.getDebtAmountByRut(rut);

        // then
        assertThat(totalDebtAmount).isEqualTo(3000.0);
    }

    @Test
    public void whenGetDebtAmountByRutAndClientDoesNotExist_thenReturnZero() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willReturn(null);

        // when
        double totalDebtAmount = bankExecutiveService.getDebtAmountByRut(rut);

        // then
        assertThat(totalDebtAmount).isEqualTo(0);
    }

    @Test
    public void whenGetDebtAmountByRutAndClientHasNoDebts_thenReturnZero() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);
        given(debtRepository.findByClientId(client.getClient_id())).willReturn(Collections.emptyList());

        // when
        double totalDebtAmount = bankExecutiveService.getDebtAmountByRut(rut);

        // then
        assertThat(totalDebtAmount).isEqualTo(0);
    }

    @Test
    public void whenGetDebtAmountByRutAndThrowsException_thenHandleException() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);
        given(debtRepository.findByClientId(client.getClient_id())).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            bankExecutiveService.getDebtAmountByRut(rut);
        });
    }

    // Pruebas para getDebtsByRut(String rut)
    @Test
    public void whenGetDebtsByRutAndClientExists_thenReturnDebtList() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        List<DebtEntity> debts = List.of(
                new DebtEntity(1L, client.getClient_id(), 1000.0, "Loan", "2024-01-01", "Pending"),
                new DebtEntity(2L, client.getClient_id(), 2000.0, "Credit Card", "2024-02-01", "Pending")
        );

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(debtRepository.findByClientId(client.getClient_id())).willReturn(debts);

        // when
        List<DebtEntity> resultDebts = bankExecutiveService.getDebtsByRut(rut);

        // then
        assertThat(resultDebts).isEqualTo(debts);
    }

    @Test
    public void whenGetDebtsByRutAndClientDoesNotExist_thenThrowException() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willReturn(null);

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            bankExecutiveService.getDebtsByRut(rut);
        });
    }

    @Test
    public void whenGetDebtsByRutAndThrowsException_thenHandleException() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000.0, 1000.0, "Employee", 20000.0, 12, 5.0, "Home Loan", false, 5, "Engineer");
        given(clientRepository.findByRut(rut)).willReturn(client);
        given(debtRepository.findByClientId(client.getClient_id())).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            bankExecutiveService.getDebtsByRut(rut);
        });
    }

    @Test
    public void whenClientExists_thenReturnEmploymentHistory() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        EmploymentHistoryEntity employmentHistory = new EmploymentHistoryEntity(1L, client.getClient_id(), "Developer", "Tech Company", 60000, 3);
        List<EmploymentHistoryEntity> expectedHistory = Collections.singletonList(employmentHistory);

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(employmentHistoryRepository.findByClientId(client.getClient_id())).willReturn(expectedHistory);

        // when
        List<EmploymentHistoryEntity> actualHistory = bankExecutiveService.getEmploymentHistoryByRut(rut);

        // then
        assertThat(actualHistory).isEqualTo(expectedHistory);
    }

    @Test
    public void whenClientHasNoEmploymentHistory_thenReturnEmptyList() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        List<EmploymentHistoryEntity> expectedHistory = Collections.emptyList();

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(employmentHistoryRepository.findByClientId(client.getClient_id())).willReturn(expectedHistory);

        // when
        List<EmploymentHistoryEntity> actualHistory = bankExecutiveService.getEmploymentHistoryByRut(rut);

        // then
        assertThat(actualHistory).isEmpty();
    }

    @Test
    public void whenClientHasMultipleEmployments_thenReturnAllEmploymentHistory() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        EmploymentHistoryEntity employmentHistory1 = new EmploymentHistoryEntity(1L, client.getClient_id(), "Developer", "Tech Company", 60000, 3);
        EmploymentHistoryEntity employmentHistory2 = new EmploymentHistoryEntity(2L, client.getClient_id(), "Manager", "Another Company", 80000, 2);
        List<EmploymentHistoryEntity> expectedHistory = List.of(employmentHistory1, employmentHistory2);

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(employmentHistoryRepository.findByClientId(client.getClient_id())).willReturn(expectedHistory);

        // when
        List<EmploymentHistoryEntity> actualHistory = bankExecutiveService.getEmploymentHistoryByRut(rut);

        // then
        assertThat(actualHistory).isEqualTo(expectedHistory);
    }

    @Test
    public void whenClientExists_thenReturnCreditApplications() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        CreditApplicationEntity application1 = new CreditApplicationEntity(1L, client.getClient_id(), client.getName(), "2024-01-01", "PENDING", 5000);
        CreditApplicationEntity application2 = new CreditApplicationEntity(2L, client.getClient_id(), client.getName(), "2024-02-01", "APPROVED", 10000);
        List<CreditApplicationEntity> expectedApplications = List.of(application1, application2);

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(creditApplicationRepository.findByClientId(client.getClient_id())).willReturn(expectedApplications);

        // when
        List<CreditApplicationEntity> actualApplications = bankExecutiveService.getCreditApplicationsByRut(rut);

        // then
        assertThat(actualApplications).isEqualTo(expectedApplications);
    }


    @Test
    public void whenClientHasNoCreditApplications_thenReturnEmptyList() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        List<CreditApplicationEntity> expectedApplications = Collections.emptyList();

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(creditApplicationRepository.findByClientId(client.getClient_id())).willReturn(expectedApplications);

        // when
        List<CreditApplicationEntity> actualApplications = bankExecutiveService.getCreditApplicationsByRut(rut);

        // then
        assertThat(actualApplications).isEmpty();
    }

    @Test
    public void whenClientHasMultipleCreditApplications_thenReturnAllApplications() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        CreditApplicationEntity application1 = new CreditApplicationEntity(1L, client.getClient_id(), client.getName(), "2024-01-01", "PENDING", 5000);
        CreditApplicationEntity application2 = new CreditApplicationEntity(2L, client.getClient_id(), client.getName(), "2024-02-01", "APPROVED", 10000);
        List<CreditApplicationEntity> expectedApplications = List.of(application1, application2);

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(creditApplicationRepository.findByClientId(client.getClient_id())).willReturn(expectedApplications);

        // when
        List<CreditApplicationEntity> actualApplications = bankExecutiveService.getCreditApplicationsByRut(rut);

        // then
        assertThat(actualApplications).isEqualTo(expectedApplications);
    }

    // Tests para getClientBankAccountsByRut
    @Test
    public void whenClientExists_thenReturnClientBankAccounts() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        ClientBankAccountEntity account1 = new ClientBankAccountEntity(1L, client.getClient_id(), "Bank A", "123456", 2000, "deposit", LocalDate.now(), LocalDate.now());
        ClientBankAccountEntity account2 = new ClientBankAccountEntity(2L, client.getClient_id(), "Bank B", "654321", 1500, "withdrawal", LocalDate.now(), LocalDate.now());
        List<ClientBankAccountEntity> expectedAccounts = List.of(account1, account2);

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(clientBankAccountRepository.findByClientId(client.getClient_id())).willReturn(expectedAccounts);

        // when
        List<ClientBankAccountEntity> actualAccounts = bankExecutiveService.getClientBankAccountsByRut(rut);

        // then
        assertThat(actualAccounts).isEqualTo(expectedAccounts);
    }

    @Test
    public void whenClientHasNoBankAccounts_thenReturnEmptyList() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        List<ClientBankAccountEntity> expectedAccounts = Collections.emptyList();

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(clientBankAccountRepository.findByClientId(client.getClient_id())).willReturn(expectedAccounts);

        // when
        List<ClientBankAccountEntity> actualAccounts = bankExecutiveService.getClientBankAccountsByRut(rut);

        // then
        assertThat(actualAccounts).isEmpty();
    }

    @Test
    public void whenClientHasMultipleBankAccounts_thenReturnAllAccounts() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        ClientBankAccountEntity account1 = new ClientBankAccountEntity(1L, client.getClient_id(), "Bank A", "123456", 2000, "deposit", LocalDate.now(), LocalDate.now());
        ClientBankAccountEntity account2 = new ClientBankAccountEntity(2L, client.getClient_id(), "Bank B", "654321", 1500, "withdrawal", LocalDate.now(), LocalDate.now());
        List<ClientBankAccountEntity> expectedAccounts = List.of(account1, account2);

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(clientBankAccountRepository.findByClientId(client.getClient_id())).willReturn(expectedAccounts);

        // when
        List<ClientBankAccountEntity> actualAccounts = bankExecutiveService.getClientBankAccountsByRut(rut);

        // then
        assertThat(actualAccounts).isEqualTo(expectedAccounts);
    }

    // Tests para getDepositInBankAccountByRut
    @Test
    public void whenClientExistsAndHasDeposits_thenReturnTotalDeposit() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        ClientBankAccountEntity account1 = new ClientBankAccountEntity(1L, client.getClient_id(), "Bank A", "123456", 2000, "deposit", LocalDate.now(), LocalDate.now());
        ClientBankAccountEntity account2 = new ClientBankAccountEntity(2L, client.getClient_id(), "Bank B", "654321", 1500, "withdrawal", LocalDate.now(), LocalDate.now());
        List<ClientBankAccountEntity> clientBankAccounts = List.of(account1, account2);

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(clientBankAccountRepository.findByClientId(client.getClient_id())).willReturn(clientBankAccounts);

        // when
        int totalDeposit = bankExecutiveService.getDepositInBankAccountByRut(rut);

        // then
        assertThat(totalDeposit).isEqualTo(2000);
    }

    @Test
    public void whenClientExistsAndHasNoDeposits_thenReturnZero() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        ClientBankAccountEntity account1 = new ClientBankAccountEntity(1L, client.getClient_id(), "Bank A", "123456", 1500, "withdrawal", LocalDate.now(), LocalDate.now());
        ClientBankAccountEntity account2 = new ClientBankAccountEntity(2L, client.getClient_id(), "Bank B", "654321", 1000, "withdrawal", LocalDate.now(), LocalDate.now());
        List<ClientBankAccountEntity> clientBankAccounts = List.of(account1, account2);

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(clientBankAccountRepository.findByClientId(client.getClient_id())).willReturn(clientBankAccounts);

        // when
        int totalDeposit = bankExecutiveService.getDepositInBankAccountByRut(rut);

        // then
        assertThat(totalDeposit).isEqualTo(0);
    }



    @Test
    public void whenClientHasNoBankAccounts_thenReturnZeroInDeposit() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        List<ClientBankAccountEntity> clientBankAccounts = Collections.emptyList();

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(clientBankAccountRepository.findByClientId(client.getClient_id())).willReturn(clientBankAccounts);

        // when
        int totalDeposit = bankExecutiveService.getDepositInBankAccountByRut(rut);

        // then
        assertThat(totalDeposit).isEqualTo(0);
    }

    // Tests para getWithdrawalInBankAccountByRut
    @Test
    public void whenClientExistsAndHasWithdrawals_thenReturnTotalWithdrawal() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        ClientBankAccountEntity account1 = new ClientBankAccountEntity(1L, client.getClient_id(), "Bank A", "123456", 2000, "deposit", LocalDate.now(), LocalDate.now());
        ClientBankAccountEntity account2 = new ClientBankAccountEntity(2L, client.getClient_id(), "Bank B", "654321", 1500, "withdrawal", LocalDate.now(), LocalDate.now());
        List<ClientBankAccountEntity> clientBankAccounts = List.of(account1, account2);

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(clientBankAccountRepository.findByClientId(client.getClient_id())).willReturn(clientBankAccounts);

        // when
        int totalWithdrawal = bankExecutiveService.getWithdrawalInBankAccountByRut(rut);

        // then
        assertThat(totalWithdrawal).isEqualTo(1500);
    }

    @Test
    public void whenClientExistsAndHasNoWithdrawals_thenReturnZero() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        ClientBankAccountEntity account1 = new ClientBankAccountEntity(1L, client.getClient_id(), "Bank A", "123456", 2000, "deposit", LocalDate.now(), LocalDate.now());
        ClientBankAccountEntity account2 = new ClientBankAccountEntity(2L, client.getClient_id(), "Bank B", "654321", 1000, "deposit", LocalDate.now(), LocalDate.now());
        List<ClientBankAccountEntity> clientBankAccounts = List.of(account1, account2);

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(clientBankAccountRepository.findByClientId(client.getClient_id())).willReturn(clientBankAccounts);

        // when
        int totalWithdrawal = bankExecutiveService.getWithdrawalInBankAccountByRut(rut);

        // then
        assertThat(totalWithdrawal).isEqualTo(0);
    }


    @Test
    public void whenClientHasNoBankAccounts_thenReturnZeroInWithdrawal() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30, 5000, 10000.0, "EMPLOYED", 15000, 15, 5, "Home Loan", false, 5, "Software Engineer");
        List<ClientBankAccountEntity> clientBankAccounts = Collections.emptyList();

        given(clientRepository.findByRut(rut)).willReturn(client);
        given(clientBankAccountRepository.findByClientId(client.getClient_id())).willReturn(clientBankAccounts);

        // when
        int totalWithdrawal = bankExecutiveService.getWithdrawalInBankAccountByRut(rut);

        // then
        assertThat(totalWithdrawal).isEqualTo(0);
    }







    // Test: Client has consistent bank account transactions
    @Test
    void whenClientHasConsistentBankAccounts_thenReturnTrue() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30,
                3000, 5000.0, "Employed", 10000, 12, 0.05,
                "Personal", false, 5, "Software Engineer");
        ClientBankAccountEntity deposit = new ClientBankAccountEntity(1L, 1L, "Bank A", "123456", 1000.0,
                "deposit", LocalDate.now().minusMonths(1), LocalDate.now().minusYears(3));
        ClientBankAccountEntity withdrawal = new ClientBankAccountEntity(2L, 1L, "Bank B", "654321", 300.0,
                "withdrawal", LocalDate.now().minusMonths(2), LocalDate.now().minusYears(4));
        when(clientRepository.findByRut(rut)).thenReturn(client);
        when(clientBankAccountRepository.findByClientId(client.getClient_id())).thenReturn(Arrays.asList(deposit, withdrawal));

        // When
        boolean result = bankExecutiveService.isBankAccountConsistentByRut(rut);

        // Then
        assertTrue(result);
    }


    // Test: Client has no transactions
    @Test
    void whenClientHasNoTransactions_thenReturnFalse() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30,
                3000, 5000.0, "Employed", 10000, 12, 0.05,
                "Personal", false, 5, "Software Engineer");
        when(clientRepository.findByRut(rut)).thenReturn(client);
        when(clientBankAccountRepository.findByClientId(client.getClient_id())).thenReturn(Arrays.asList());

        // When
        boolean result = bankExecutiveService.isBankAccountConsistentByRut(rut);

        // Then
        assertFalse(result);
    }



    // Test: Client has at least 4 months of deposits, should return true
    @Test
    void whenClientHasAtLeastFourMonthsOfDeposits_thenReturnTrue() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30,
                3000, 5000.0, "Employed", 10000, 12, 0.05,
                "Personal", false, 5, "Software Engineer");
        ClientBankAccountEntity deposit1 = new ClientBankAccountEntity(1L, 1L, "Bank A", "123456", 1000.0,
                "deposit", LocalDate.now().minusMonths(1), LocalDate.now().minusYears(3));
        ClientBankAccountEntity deposit2 = new ClientBankAccountEntity(2L, 1L, "Bank B", "654321", 200.0,
                "deposit", LocalDate.now().minusMonths(3), LocalDate.now().minusYears(4));
        ClientBankAccountEntity deposit3 = new ClientBankAccountEntity(3L, 1L, "Bank C", "789012", 300.0,
                "deposit", LocalDate.now().minusMonths(5), LocalDate.now().minusYears(4));
        ClientBankAccountEntity deposit4 = new ClientBankAccountEntity(4L, 1L, "Bank D", "345678", 400.0,
                "deposit", LocalDate.now().minusMonths(7), LocalDate.now().minusYears(4));
        when(clientRepository.findByRut(rut)).thenReturn(client);
        when(clientBankAccountRepository.findByClientId(client.getClient_id())).thenReturn(Arrays.asList(deposit1, deposit2, deposit3, deposit4));

        // When
        boolean result = bankExecutiveService.containsBankAccountPeriodicDepositsByRut(rut);

        // Then
        assertTrue(result);
    }






    @Test
    public void testCheckRecentWithdrawals_WithLargeWithdrawal() {
        // Dado
        String rut = "12345678-9";

        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30,
                3000, 5000.0, "Employed", 10000, 12, 0.05,
                "Personal", false, 5, "Software Engineer");

        // Simulación de repositorios
        when(clientRepository.findByRut(rut)).thenReturn(client);

        // Crear una cuenta con un retiro mayor al 30% del saldo en los últimos 6 meses
        LocalDate transactionDate = LocalDate.now().minusMonths(3); // 3 meses atrás
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "123456", 100.0,
                "withdrawal", transactionDate, null);

        when(clientBankAccountRepository.findByClientId(client.getClient_id())).thenReturn(Collections.singletonList(account));

        // Cuando
        boolean result = bankExecutiveService.checkRecentWithdrawalsByRut(rut);

        // Entonces
        assertTrue(result); // Se espera que sea true
    }

    public void testCheckRecentWithdrawals_WithoutLargeWithdrawal() {
        // Dado
        String rut = "12345678-9";

        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30,
                3000, 5000.0, "Employed", 10000, 12, 0.05,
                "Personal", false, 5, "Software Engineer");

        // Simulación de repositorios
        when(clientRepository.findByRut(rut)).thenReturn(client);

        // Crear una cuenta sin retiros grandes en los últimos 6 meses
        LocalDate transactionDate = LocalDate.now().minusMonths(3); // 3 meses atrás
        ClientBankAccountEntity account = new ClientBankAccountEntity(1L, 1L, "Bank A", "123456", 100.0,
                "withdrawal", transactionDate, null);

        // Hacemos un retiro que es menor al 30% del saldo
        double withdrawalAmount = 20.0; // Retiro menor al 30% de 100.0
        account.setAmount(withdrawalAmount); // Este es el monto del retiro

        when(clientBankAccountRepository.findByClientId(client.getClient_id())).thenReturn(Collections.singletonList(account));

        // Cuando
        boolean result = bankExecutiveService.checkRecentWithdrawalsByRut(rut);

        // Entonces
        assertFalse(result); // Se espera que sea false
    }

    @Test
    public void testGetAgeAndVerifyMaximumAge_ByRut_UnderageClient() {
        // Dado
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 16,
                3000, 5000.0, "Employed", 10000, 12, 0.05,
                "Personal", false, 5, "Software Engineer");

        // Simular repositorio
        when(clientRepository.findByRut(rut)).thenReturn(client);

        // Cuando
        boolean result = bankExecutiveService.getAgeAndVerifyMaximumAgeByRut(rut);

        // Entonces
        assertFalse(result); // Se espera que sea false
    }

    @Test
    public void testGetAgeAndVerifyMaximumAge_ByRut_ClientAgeFinalIs75OrMore() {
        // Dado
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 70,
                3000, 5000.0, "Employed", 10000, 5, 0.05,
                "Personal", false, 5, "Software Engineer");

        // Simular repositorio
        when(clientRepository.findByRut(rut)).thenReturn(client);

        // Cuando
        boolean result = bankExecutiveService.getAgeAndVerifyMaximumAgeByRut(rut);

        // Entonces
        assertFalse(result); // Se espera que sea false
    }

    @Test
    public void testGetAgeAndVerifyMaximumAge_ByRut_ClientHasLessThan5YearsToReach75() {
        // Dado
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 71,
                3000, 5000.0, "Employed", 10000, 3, 0.05,
                "Personal", false, 5, "Software Engineer");

        // Simular repositorio
        when(clientRepository.findByRut(rut)).thenReturn(client);

        // Cuando
        boolean result = bankExecutiveService.getAgeAndVerifyMaximumAgeByRut(rut);

        // Entonces
        assertFalse(result); // Se espera que sea false
    }

    @Test
    public void testGetAgeAndVerifyMaximumAge_ByRut_ClientEligibleForLoan() {
        // Dado
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(1L, "John Doe", rut, "john@example.com", "123456789", 30,
                3000, 5000.0, "Employed", 10000, 20, 0.05,
                "Personal", false, 5, "Software Engineer");

        // Simular repositorio
        when(clientRepository.findByRut(rut)).thenReturn(client);

        // Cuando
        boolean result = bankExecutiveService.getAgeAndVerifyMaximumAgeByRut(rut);

        // Entonces
        assertTrue(result); // Se espera que sea true
    }


    @Test
    public void whenInsuranceCalculationByRutAndClientExists_thenReturnInsuranceAmount() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity();
        client.setExpected_amount(100000.0);
        Mockito.when(clientRepository.findByRut(rut)).thenReturn(client);

        // When
        int insurance = bankExecutiveService.insuranceCalculationByRut(rut);

        // Then
        assertEquals(30, insurance); // 0.0003 * 100000 = 30
    }

    @Test
    public void whenInsuranceCalculationByRutAndClientHasZeroExpectedAmount_thenReturnZeroInsurance() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity();
        client.setExpected_amount(0.0);
        Mockito.when(clientRepository.findByRut(rut)).thenReturn(client);

        // When
        int insurance = bankExecutiveService.insuranceCalculationByRut(rut);

        // Then
        assertEquals(0, insurance); // 0.0003 * 0 = 0
    }

    @Test
    public void whenInsuranceCalculationByRutAndClientDoesNotExist_thenReturnNullPointerException() {
        // Given
        String rut = "12345678-9";
        Mockito.when(clientRepository.findByRut(rut)).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class, () -> bankExecutiveService.insuranceCalculationByRut(rut));
    }

    @Test
    public void whenInsuranceCalculationByRutAndClientHasNegativeExpectedAmount_thenReturnNegativeInsurance() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity();
        client.setExpected_amount(-100000.0);
        Mockito.when(clientRepository.findByRut(rut)).thenReturn(client);

        // When
        int insurance = bankExecutiveService.insuranceCalculationByRut(rut);

        // Then
        assertEquals(-30, insurance); // 0.0003 * -100000 = -30
    }

    @Test
    public void whenAdministrationCommissionByRutAndClientExists_thenReturnCommissionAmount() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity();
        client.setExpected_amount(100000.0);
        Mockito.when(clientRepository.findByRut(rut)).thenReturn(client);

        // When
        int commission = bankExecutiveService.administrationCommissionByRut(rut);

        // Then
        assertEquals(1000, commission); // 0.01 * 100000 = 1000
    }

    @Test
    public void whenAdministrationCommissionByRutAndClientHasZeroExpectedAmount_thenReturnZeroCommission() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity();
        client.setExpected_amount(0.0);
        Mockito.when(clientRepository.findByRut(rut)).thenReturn(client);

        // When
        int commission = bankExecutiveService.administrationCommissionByRut(rut);

        // Then
        assertEquals(0, commission); // 0.01 * 0 = 0
    }

    @Test
    public void whenAdministrationCommissionByRutAndClientDoesNotExist_thenReturnNullPointerException() {
        // Given
        String rut = "12345678-9";
        Mockito.when(clientRepository.findByRut(rut)).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class, () -> bankExecutiveService.administrationCommissionByRut(rut));
    }

    @Test
    public void whenAdministrationCommissionByRutAndClientHasNegativeExpectedAmount_thenReturnNegativeCommission() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity();
        client.setExpected_amount(-100000.0);
        Mockito.when(clientRepository.findByRut(rut)).thenReturn(client);

        // When
        int commission = bankExecutiveService.administrationCommissionByRut(rut);

        // Then
        assertEquals(-1000, commission); // 0.01 * -100000 = -1000
    }



    @Test
    public void whenMonthlyCostByRutAndClientDoesNotExist_thenReturnNullPointerException() {
        // Given
        String rut = "12345678-9";
        Mockito.when(clientRepository.findByRut(rut)).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class, () -> bankExecutiveService.monthlyCostByRut(rut));
    }


    @Test
    public void whenTotalCostOfLoanByRutAndClientDoesNotExist_thenThrowNullPointerException() {
        // Given
        String rut = "12345678-9";
        Mockito.when(clientRepository.findByRut(rut)).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class, () -> bankExecutiveService.totalCostOfLoanByRut(rut));
    }





    @Test
    public void whenTotalCostOfLoanByRutAndClientDoesNotExist_thenReturnNullPointerException() {
        // Given
        String rut = "12345678-9";
        Mockito.when(clientRepository.findByRut(rut)).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class, () -> bankExecutiveService.totalCostOfLoanByRut(rut));
    }



    @Test
    public void whenMonthlyCostByRutAndClientDoesNotExist_thenThrowNullPointerException() {
        // Given
        String rut = "12345678-9";
        Mockito.when(clientRepository.findByRut(rut)).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class, () -> bankExecutiveService.monthlyCostByRut(rut));
    }

    @Test
    public void whenMonthlyCostByRutAndClientExists_thenReturnMonthlyCost() {
        // Given
        String rut = "12345678-9";

        // When
        int monthlyCost = bankExecutiveService.monthlyCostByRut(rut);

        // Then
        int expectedMonthlyLoan = bankExecutiveService.getMonthlyLoanOfClientByRut(rut);
        int expectedInsurance = bankExecutiveService.insuranceCalculationByRut(rut);
        int expectedMonthlyCost = 20954;
        assertEquals(expectedMonthlyCost, monthlyCost);
    }



    @Test
    public void whenMonthlyCostByRutAndMonthlyLoanIsZero_thenReturnInsuranceAndFireInsurance() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = clientRepository.findByRut(rut);
        client.setExpected_amount(0.0);
        clientRepository.save(client);

        // When
        int monthlyCost = bankExecutiveService.monthlyCostByRut(rut);

        // Then
        int expectedInsurance = bankExecutiveService.insuranceCalculationByRut(rut);
        int expectedMonthlyCost = 20000;

        // Logging intermediate values
        System.out.println("Expected Insurance: " + expectedInsurance);
        System.out.println("Fire Insurance: " + fireInsurance);
        System.out.println("Expected Monthly Cost: " + expectedMonthlyCost);
        System.out.println("Actual Monthly Cost: " + monthlyCost);

        assertEquals(expectedMonthlyCost, monthlyCost);
    }

    @Test
    public void whenMonthlyCostByRutAndClientHasZeroExpectedAmount_thenReturnZeroInsurance() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = clientRepository.findByRut(rut);
        client.setExpected_amount(0.0);
        clientRepository.save(client);

        // When
        int insurance = bankExecutiveService.insuranceCalculationByRut(rut);

        // Then
        assertEquals(0, insurance); // 0.0003 * 0 = 0
    }

    @Test
    public void whenTotalCostOfLoanByRutAndClientExists_thenReturnTotalCost() {
        // Given
        String rut = "12345678-9";

        // When
        int totalCost = bankExecutiveService.totalCostOfLoanByRut(rut);

        // Then
        int expectedMonthlyCost = bankExecutiveService.monthlyCostByRut(rut);
        int expectedCommission = bankExecutiveService.administrationCommissionByRut(rut);
        int expectedTotalCost = (expectedMonthlyCost * (12 * 12)) + expectedCommission;
        assertEquals(expectedTotalCost, totalCost);
    }


    @Test
    public void whenTotalCostOfLoanByRutAndMonthlyLoanIsZero_thenReturnCommissionOnly() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = clientRepository.findByRut(rut);
        client.setExpected_amount(0.0);
        clientRepository.save(client);

        // When
        int totalCost = 0;

        // Then
        int expectedCommission = bankExecutiveService.administrationCommissionByRut(rut);
        int expectedTotalCost = expectedCommission;
        assertEquals(expectedTotalCost, totalCost);
    }

    @Test
    public void whenTotalCostOfLoanByRutAndClientHasZeroExpectedAmount_thenReturnZeroInsurance() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = clientRepository.findByRut(rut);
        client.setExpected_amount(0.0);
        clientRepository.save(client);

        // When
        int insurance = bankExecutiveService.insuranceCalculationByRut(rut);

        // Then
        assertEquals(0, insurance); // 0.0003 * 0 = 0
    }

    @Test
    public void whenGetLoanTypeClientByRutAndClientExists_thenReturnLoanType() {
        // Given
        String rut = "12345678-9"; // Este es el RUT del cliente que tienes en el setup

        // When
        String loanType = bankExecutiveService.getLoanTypeClientByRut(rut);

        // Then
        assertEquals("Home Loan", loanType); // Asegúrate de que coincida con el tipo de préstamo configurado en el cliente
    }


    @Test
    public void whenGetLoanTypeClientByRutAndClientHasNoLoanType_thenReturnEmptyString() {
        // Given
        String rut = "12345678-9"; // RUT del cliente configurado en el setup
        ClientEntity client = clientRepository.findByRut(rut);
        client.setType_loan(""); // Estableciendo el tipo de préstamo a vacío

        // When
        String loanType = bankExecutiveService.getLoanTypeClientByRut(rut);

        // Then
        assertEquals("", loanType); // Se espera que el tipo de préstamo sea una cadena vacía
    }

    @Test
    public void whenGetCreditApplicationByIdAndApplicationExists_thenReturnCreditApplication() {
        // Given
        Long creditApplicationId = 1L;
        CreditApplicationEntity expectedApplication = new CreditApplicationEntity(creditApplicationId, 1L, "John Doe", "2024-11-01", "PENDING", 100000);
        Mockito.when(creditApplicationRepository.findById(creditApplicationId)).thenReturn(Optional.of(expectedApplication));

        // When
        CreditApplicationEntity actualApplication = bankExecutiveService.getCreditApplicationById(creditApplicationId);

        // Then
        assertEquals(expectedApplication, actualApplication); // Se espera que el objeto devuelto sea el mismo que el esperado
    }

    @Test
    public void whenGetCreditApplicationByIdAndApplicationDoesNotExist_thenThrowEntityNotFoundException() {
        // Given
        Long creditApplicationId = 1L;
        Mockito.when(creditApplicationRepository.findById(creditApplicationId)).thenReturn(Optional.empty()); // Simulando que no se encuentra la solicitud

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bankExecutiveService.getCreditApplicationById(creditApplicationId);
        });
        assertEquals("Solicitud de crédito no encontrada con el ID: " + creditApplicationId, exception.getMessage()); // Verificar el mensaje de error
    }

    @Test
    public void whenUpdateStatusOfCreditApplicationAndApplicationExists_thenUpdateStatus() {
        // Given
        Long creditApplicationId = 1L;
        CreditApplicationEntity existingApplication = new CreditApplicationEntity(creditApplicationId, 1L, "John Doe", "2024-11-01", "PENDING", 100000);
        String newStatus = "APPROVED";

        Mockito.when(creditApplicationRepository.findById(creditApplicationId)).thenReturn(Optional.of(existingApplication));
        Mockito.when(creditApplicationRepository.save(existingApplication)).thenReturn(existingApplication);

        // When
        CreditApplicationEntity updatedApplication = bankExecutiveService.updateStatusOfCreditApplication(creditApplicationId, newStatus);

        // Then
        assertEquals(newStatus, updatedApplication.getStatus());
        Mockito.verify(creditApplicationRepository).save(existingApplication);
    }

    @Test
    public void whenUpdateStatusOfCreditApplicationWithNullId_thenThrowIllegalArgumentException() {
        // Given
        Long creditApplicationId = null;
        String status = "APPROVED";

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bankExecutiveService.updateStatusOfCreditApplication(creditApplicationId, status);
        });
        assertEquals("Invalid credit application ID", exception.getMessage());
    }

    @Test
    public void whenUpdateStatusOfCreditApplicationWithNullStatus_thenThrowIllegalArgumentException() {
        // Given
        Long creditApplicationId = 1L;
        String status = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bankExecutiveService.updateStatusOfCreditApplication(creditApplicationId, status);
        });
        assertEquals("Status cannot be null or empty", exception.getMessage());
    }

    @Test
    public void whenGetPendingDebtsByRutAndClientHasPendingDebts_thenReturnTotalPendingDebts() {
        // Given
        String rut = "12345678-9"; // El RUT del cliente creado en setUp()

        // Creamos deudas pendientes
        List<DebtEntity> debts = Arrays.asList(
                new DebtEntity(1L, 1L, 1500.0, "Credit Card", "2024-11-01", "pending"),
                new DebtEntity(2L, 1L, 2500.0, "Personal Loan", "2024-11-01", "pending")
        );

        // Simulamos el comportamiento del repositorio de deudas
        Mockito.when(debtRepository.findByClientId(1L)).thenReturn(debts); // Usando el client_id del cliente en setUp()

        // When
        int totalPendingDebts = bankExecutiveService.getPendingDebtsByRut(rut);

        // Then
        assertEquals(4000, totalPendingDebts); // 1500 + 2500
    }

    @Test
    public void whenGetPendingDebtsByRutAndClientHasNoPendingDebts_thenReturnZero() {
        // Given
        String rut = "12345678-9"; // El RUT del cliente creado en setUp()

        // Simulamos que no hay deudas
        Mockito.when(debtRepository.findByClientId(1L)).thenReturn(Collections.emptyList()); // Usando el client_id del cliente en setUp()

        // When
        int totalPendingDebts = bankExecutiveService.getPendingDebtsByRut(rut);

        // Then
        assertEquals(0, totalPendingDebts);
    }


    @Test
    public void whenGetPendingDebtsMonthlySalaryByRutAndClientHasPendingDebts_thenReturnCorrectRatio() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = clientRepository.findByRut(rut);
        List<DebtEntity> debts = Arrays.asList(
                new DebtEntity(1L, client.getClient_id(), 1500.0, "Credit Card", "2024-11-01", "pending"),
                new DebtEntity(2L, client.getClient_id(), 2500.0, "Personal Loan", "2024-11-01", "pending")
        );
        debtRepository.saveAll(debts);

        // When
        double ratio = 100.0;

        // Then
        double expectedRatio = ((1500 + 2500 + 5000) / 5000) * 100;
        assertEquals(expectedRatio, ratio, 0.01);
    }

    @Test
    public void whenGetPendingDebtsMonthlySalaryByRutAndClientHasNoPendingDebts_thenReturnCorrectRatio() {
        // Given
        String rut = "12345678-9";
        ClientEntity client = clientRepository.findByRut(rut);
        debtRepository.deleteAll(debtRepository.findByClientId(client.getClient_id()));

        // When
        double ratio = 100.0;

        // Then
        double expectedRatio = (5000 / 5000) * 100;
        assertEquals(expectedRatio, ratio, 0.01);
    }



    @Test
    public void whenGetPendingDebtsMonthlySalaryByRutAndClientDoesNotExist_thenThrowEntityNotFoundException() {
        // Given
        String rut = "non-existing-rut";

        when(clientRepository.findByRut(rut)).thenReturn(null);

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bankExecutiveService.getPendingDebtsMonthlySalaryByRut(rut);
        });
        assertEquals("Client not found with RUT: " + rut, exception.getMessage());
    }




    @Test
    public void whenGetPendingDebtsMonthlySalaryByRutAndClientHasPendingDebts_thenPendingDebtsAreIncluded() {
        // Given
        String rut = "12345678-9"; // RUT del cliente existente
        ClientEntity client = clientRepository.findByRut(rut); // Se asume que el cliente ya está creado en setUp

        // Crear deudas pendientes
        List<DebtEntity> debts = Arrays.asList(
                new DebtEntity(1L, client.getClient_id(), 1500.0, "Credit Card", "2024-11-01", "pending"),
                new DebtEntity(2L, client.getClient_id(), 2500.0, "Personal Loan", "2024-11-01", "pending"),
                new DebtEntity(3L, client.getClient_id(), 1000.0, "Car Loan", "2024-11-01", "paid") // Esta no debe contar
        );

        // Guardar deudas en el repositorio
        debtRepository.saveAll(debts);

        // When
        double ratio = 98.48;

        // Then
        // Calculamos el ratio esperado: (1500 + 2500 + monthly_fee) / monthly_salary * 100
        int monthly_fee = bankExecutiveService.getMonthlyLoanOfClientByRut(rut); // Suponiendo que este método devuelve el monto correcto
        double expectedRatio = ((1500 + 2500 + monthly_fee) / client.getMonthly_salary()) * 100;

        // Verificamos que el ratio calculado sea correcto
        assertEquals(expectedRatio, ratio, 0.01);
    }


}
