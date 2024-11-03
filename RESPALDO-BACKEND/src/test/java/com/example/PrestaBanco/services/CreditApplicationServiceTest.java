package com.example.PrestaBanco.services;

import com.example.PrestaBanco.entities.ClientEntity;
import com.example.PrestaBanco.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.PrestaBanco.repositories.CreditApplicationRepository;
import com.example.PrestaBanco.entities.CreditApplicationEntity;
import com.example.PrestaBanco.entities.ClientEntity;
import com.example.PrestaBanco.repositories.ClientRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class CreditApplicationServiceTest {

    @InjectMocks
    private ClientService clientService;



    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private CreditApplicationService creditApplicationService;

    @Mock
    private CreditApplicationRepository creditApplicationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenFindAll_thenReturnListOfCreditApplications() {
        // given
        CreditApplicationEntity application1 = new CreditApplicationEntity(1L, 1L, "Application 1", "2023-10-31", "PENDING", 5000);
        CreditApplicationEntity application2 = new CreditApplicationEntity(2L, 2L, "Application 2", "2023-10-31", "APPROVED", 10000);
        List<CreditApplicationEntity> expectedApplications = Arrays.asList(application1, application2);
        given(creditApplicationRepository.findAll()).willReturn(expectedApplications);

        // when
        List<CreditApplicationEntity> actualApplications = creditApplicationService.findAll();

        // then
        assertThat(actualApplications).isNotNull();
        assertThat(actualApplications).hasSize(2);
        assertThat(actualApplications).containsExactlyInAnyOrder(application1, application2);
    }

    @Test
    public void whenFindAllWithNoApplications_thenReturnEmptyList() {
        // given
        List<CreditApplicationEntity> expectedApplications = Arrays.asList();
        given(creditApplicationRepository.findAll()).willReturn(expectedApplications);

        // when
        List<CreditApplicationEntity> actualApplications = creditApplicationService.findAll();

        // then
        assertThat(actualApplications).isNotNull();
        assertThat(actualApplications).isEmpty();
    }

    @Test
    public void whenFindAllWithNullApplications_thenReturnEmptyList() {
        // givengiven(creditApplicationRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<CreditApplicationEntity> actualApplications = creditApplicationService.findAll();

        // then
        assertThat(actualApplications).isNotNull();
        assertThat(actualApplications).isEmpty();
    }

    @Test
    public void whenFindAll_thenCheckFirstApplicationDetails() {
        // given
        CreditApplicationEntity application1 = new CreditApplicationEntity(1L, 1L, "Application 1", "2023-10-31", "PENDING", 5000);
        given(creditApplicationRepository.findAll()).willReturn(Arrays.asList(application1));

        // when
        List<CreditApplicationEntity> actualApplications = creditApplicationService.findAll();

        // then
        assertThat(actualApplications).isNotEmpty();
        CreditApplicationEntity firstApplication = actualApplications.get(0);
        assertThat(firstApplication.getName()).isEqualTo("Application 1");
        assertThat(firstApplication.getAmount()).isEqualTo(5000);
    }

    @Test
    public void whenFindAll_thenCheckApplicationStatus() {
        // given
        CreditApplicationEntity application2 = new CreditApplicationEntity(2L, 2L, "Application 2", "2023-10-31", "APPROVED", 10000);
        given(creditApplicationRepository.findAll()).willReturn(Arrays.asList(application2));

        // when
        List<CreditApplicationEntity> actualApplications = creditApplicationService.findAll();

        // then
        assertThat(actualApplications).isNotEmpty();
        assertThat(actualApplications.get(0).getStatus()).isEqualTo("APPROVED");
    }

    @Test
    public void whenFindByClientIdWithValidId_thenReturnListOfApplications() {
        // given
        Long clientId = 1L;
        CreditApplicationEntity application1 = new CreditApplicationEntity(1L, clientId, "Application 1", "2023-10-31", "PENDING", 5000);
        CreditApplicationEntity application2 = new CreditApplicationEntity(2L, clientId, "Application 2", "2023-10-31", "APPROVED", 10000);
        List<CreditApplicationEntity> expectedApplications = Arrays.asList(application1, application2);
        given(creditApplicationRepository.findByClientId(clientId)).willReturn(expectedApplications);

        // when
        List<CreditApplicationEntity> actualApplications = creditApplicationService.findByClientId(clientId);

        // then
        assertThat(actualApplications).isNotNull();
        assertThat(actualApplications).hasSize(2);
        assertThat(actualApplications).containsExactlyInAnyOrder(application1, application2);
    }

    @Test
    public void whenFindByClientIdWithNoApplications_thenReturnEmptyList() {
        // given
        Long clientId = 1L;
        List<CreditApplicationEntity> expectedApplications = Collections.emptyList();
        given(creditApplicationRepository.findByClientId(clientId)).willReturn(expectedApplications);

        // when
        List<CreditApplicationEntity> actualApplications = creditApplicationService.findByClientId(clientId);

        // then
        assertThat(actualApplications).isNotNull();
        assertThat(actualApplications).isEmpty();
    }

    @Test
    public void whenFindByClientIdWithNullId_thenReturnEmptyList() {
        // given
        Long clientId = null;

        // when
        List<CreditApplicationEntity> actualApplications = creditApplicationService.findByClientId(clientId);

        // then
        assertThat(actualApplications).isNotNull();
        assertThat(actualApplications).isEmpty();
    }

    @Test
    public void whenFindByClientIdWithInvalidId_thenReturnEmptyList() {
        // given
        Long clientId = 999L;
        List<CreditApplicationEntity> expectedApplications = Collections.emptyList();
        given(creditApplicationRepository.findByClientId(clientId)).willReturn(expectedApplications);

        // when
        List<CreditApplicationEntity> actualApplications = creditApplicationService.findByClientId(clientId);

        // then
        assertThat(actualApplications).isNotNull();
        assertThat(actualApplications).isEmpty();
    }

    @Test
    public void whenFindByClientIdWithMultipleClients_thenReturnOnlySpecificClientApplications() {
        // given
        Long clientId = 1L;
        CreditApplicationEntity application1 = new CreditApplicationEntity(1L, clientId, "Application 1", "2023-10-31", "PENDING", 5000);
        CreditApplicationEntity application2 = new CreditApplicationEntity(2L, 2L, "Application 2", "2023-10-31", "APPROVED", 10000);
        List<CreditApplicationEntity> expectedApplications = Arrays.asList(application1);
        given(creditApplicationRepository.findByClientId(clientId)).willReturn(expectedApplications);

        // when
        List<CreditApplicationEntity> actualApplications = creditApplicationService.findByClientId(clientId);

        // then
        assertThat(actualApplications).isNotNull();
        assertThat(actualApplications).hasSize(1);
        assertThat(actualApplications).containsExactly(application1);
    }

    @Test
    public void whenFindByCreditApplicationId_thenReturnCreditApplication() {
        // given
        Long creditApplicationId = 1L;
        CreditApplicationEntity expectedApplication = new CreditApplicationEntity(creditApplicationId, 1L, "Application 1", "2023-10-31", "PENDING", 5000);
        given(creditApplicationRepository.findByCreditApplicationId(creditApplicationId)).willReturn(expectedApplication);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByCreditApplicationId(creditApplicationId);

        // then
        assertThat(actualApplication).isNotNull();
        assertThat(actualApplication.getCredit_application_id()).isEqualTo(creditApplicationId);
        assertThat(actualApplication.getName()).isEqualTo("Application 1");
    }

    @Test
    public void whenFindByNonExistentCreditApplicationId_thenReturnNull() {
        // given
        Long nonExistentCreditApplicationId = 99L;
        given(creditApplicationRepository.findByCreditApplicationId(nonExistentCreditApplicationId)).willReturn(null);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByCreditApplicationId(nonExistentCreditApplicationId);

        // then
        assertThat(actualApplication).isNull();
    }

    @Test
    public void whenFindByNullCreditApplicationId_thenReturnNull() {
        // given
        given(creditApplicationRepository.findByCreditApplicationId(null)).willReturn(null);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByCreditApplicationId(null);

        // then
        assertThat(actualApplication).isNull();
    }

    @Test
    public void whenFindByEmptyCreditApplicationId_thenReturnNull() {
        // given
        Long emptyCreditApplicationId = 0L; // Considerando que 0 no es un ID válido
        given(creditApplicationRepository.findByCreditApplicationId(emptyCreditApplicationId)).willReturn(null);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByCreditApplicationId(emptyCreditApplicationId);

        // then
        assertThat(actualApplication).isNull();
    }

    @Test
    public void whenFindByCreditApplicationIdWithMultipleApplications_thenReturnCorrectApplication() {
        // given
        Long creditApplicationId1 = 1L;
        Long creditApplicationId2 = 2L;
        CreditApplicationEntity application1 = new CreditApplicationEntity(creditApplicationId1, 1L, "Application 1", "2023-10-31", "PENDING", 5000);
        CreditApplicationEntity application2 = new CreditApplicationEntity(creditApplicationId2, 2L, "Application 2", "2023-10-31", "APPROVED", 10000);
        given(creditApplicationRepository.findByCreditApplicationId(creditApplicationId1)).willReturn(application1);
        given(creditApplicationRepository.findByCreditApplicationId(creditApplicationId2)).willReturn(application2);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByCreditApplicationId(creditApplicationId1);

        // then
        assertThat(actualApplication).isNotNull();
        assertThat(actualApplication.getCredit_application_id()).isEqualTo(creditApplicationId1);
        assertThat(actualApplication.getName()).isEqualTo("Application 1");
    }

    @Test
    public void whenFindByName_thenReturnCreditApplication() {
        // given
        String name = "Application 1";
        CreditApplicationEntity expectedApplication = new CreditApplicationEntity(1L, 1L, name, "2023-10-31", "PENDING", 5000);
        given(creditApplicationRepository.findByName(name)).willReturn(expectedApplication);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByName(name);

        // then
        assertThat(actualApplication).isNotNull();
        assertThat(actualApplication.getName()).isEqualTo(name);
        assertThat(actualApplication.getCredit_application_id()).isEqualTo(1L); // Verificar ID o cualquier otro campo necesario
    }

    @Test
    public void whenFindByNonExistentName_thenReturnNull() {
        // given
        String nonExistentName = "Non-existent Application";
        given(creditApplicationRepository.findByName(nonExistentName)).willReturn(null);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByName(nonExistentName);

        // then
        assertThat(actualApplication).isNull();
    }

    @Test
    public void whenFindByNullName_thenReturnNull() {
        // given
        given(creditApplicationRepository.findByName(null)).willReturn(null);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByName(null);

        // then
        assertThat(actualApplication).isNull();
    }

    @Test
    public void whenFindByEmptyName_thenReturnNull() {
        // given
        String emptyName = "";
        given(creditApplicationRepository.findByName(emptyName)).willReturn(null);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByName(emptyName);

        // then
        assertThat(actualApplication).isNull();
    }

    @Test
    public void whenFindByNameWithMultipleApplications_thenReturnFirstApplication() {
        // given
        String name = "Application 1";
        CreditApplicationEntity application1 = new CreditApplicationEntity(1L, 1L, name, "2023-10-31", "PENDING", 5000);
        CreditApplicationEntity application2 = new CreditApplicationEntity(2L, 2L, "Application 2", "2023-10-31", "APPROVED", 10000);
        given(creditApplicationRepository.findByName(name)).willReturn(application1);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByName(name);

        // then
        assertThat(actualApplication).isNotNull();
        assertThat(actualApplication.getName()).isEqualTo(name);
        assertThat(actualApplication.getCredit_application_id()).isEqualTo(1L); // Verificar ID o cualquier otro campo necesario
    }

    @Test
    public void whenFindByCreditDate_thenReturnCreditApplication() {
        // given
        String creditDate = "2023-10-31";
        CreditApplicationEntity expectedApplication = new CreditApplicationEntity(1L, 1L, "Application 1", creditDate, "PENDING", 5000);
        given(creditApplicationRepository.findByCreditDate(creditDate)).willReturn(expectedApplication);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByCreditDate(creditDate);

        // then
        assertThat(actualApplication).isNotNull();
        assertThat(actualApplication.getCredit_date()).isEqualTo(creditDate);
        assertThat(actualApplication.getCredit_application_id()).isEqualTo(1L); // Verificar ID o cualquier otro campo necesario
    }

    @Test
    public void whenFindByNonExistentCreditDate_thenReturnNull() {
        // given
        String nonExistentCreditDate = "2023-11-01";
        given(creditApplicationRepository.findByCreditDate(nonExistentCreditDate)).willReturn(null);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByCreditDate(nonExistentCreditDate);

        // then
        assertThat(actualApplication).isNull();
    }

    @Test
    public void whenFindByNullCreditDate_thenReturnNull() {
        // given
        given(creditApplicationRepository.findByCreditDate(null)).willReturn(null);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByCreditDate(null);

        // then
        assertThat(actualApplication).isNull();
    }

    @Test
    public void whenFindByEmptyCreditDate_thenReturnNull() {
        // given
        String emptyCreditDate = "";
        given(creditApplicationRepository.findByCreditDate(emptyCreditDate)).willReturn(null);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByCreditDate(emptyCreditDate);

        // then
        assertThat(actualApplication).isNull();
    }

    @Test
    public void whenFindByCreditDateWithMultipleApplications_thenReturnFirstApplication() {
        // given
        String creditDate = "2023-10-31";
        CreditApplicationEntity application1 = new CreditApplicationEntity(1L, 1L, "Application 1", creditDate, "PENDING", 5000);
        CreditApplicationEntity application2 = new CreditApplicationEntity(2L, 2L, "Application 2", creditDate, "APPROVED", 10000);
        given(creditApplicationRepository.findByCreditDate(creditDate)).willReturn(application1);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByCreditDate(creditDate);

        // then
        assertThat(actualApplication).isNotNull();
        assertThat(actualApplication.getCredit_date()).isEqualTo(creditDate);
        assertThat(actualApplication.getCredit_application_id()).isEqualTo(1L); // Verificar ID o cualquier otro campo necesario
    }

    @Test
    public void whenFindByStatus_thenReturnCreditApplication() {
        // given
        String status = "PENDING";
        CreditApplicationEntity expectedApplication = new CreditApplicationEntity(1L, 1L, "Application 1", "2023-10-31", status, 5000);
        given(creditApplicationRepository.findByStatus(status)).willReturn(expectedApplication);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByStatus(status);

        // then
        assertThat(actualApplication).isNotNull();
        assertThat(actualApplication.getStatus()).isEqualTo(status);
        assertThat(actualApplication.getCredit_application_id()).isEqualTo(1L); // Verificar ID o cualquier otro campo necesario
    }

    @Test
    public void whenFindByNonExistentStatus_thenReturnNull() {
        // given
        String nonExistentStatus = "DECLINED";
        given(creditApplicationRepository.findByStatus(nonExistentStatus)).willReturn(null);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByStatus(nonExistentStatus);

        // then
        assertThat(actualApplication).isNull();
    }

    @Test
    public void whenFindByNullStatus_thenReturnNull() {
        // given
        given(creditApplicationRepository.findByStatus(null)).willReturn(null);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByStatus(null);

        // then
        assertThat(actualApplication).isNull();
    }

    @Test
    public void whenFindByEmptyStatus_thenReturnNull() {
        // given
        String emptyStatus = "";
        given(creditApplicationRepository.findByStatus(emptyStatus)).willReturn(null);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByStatus(emptyStatus);

        // then
        assertThat(actualApplication).isNull();
    }

    @Test
    public void whenFindByStatusWithMultipleApplications_thenReturnFirstApplication() {
        // given
        String status = "APPROVED";
        CreditApplicationEntity application1 = new CreditApplicationEntity(1L, 1L, "Application 1", "2023-10-31", status, 5000);
        CreditApplicationEntity application2 = new CreditApplicationEntity(2L, 2L, "Application 2", "2023-10-31", status, 10000);
        given(creditApplicationRepository.findByStatus(status)).willReturn(application1);

        // when
        CreditApplicationEntity actualApplication = creditApplicationService.findByStatus(status);

        // then
        assertThat(actualApplication).isNotNull();
        assertThat(actualApplication.getStatus()).isEqualTo(status);
        assertThat(actualApplication.getCredit_application_id()).isEqualTo(1L); // Verificar ID o cualquier otro campo necesario
    }

    @Test
    public void whenExistsByClientId_thenReturnTrue() {
        // given
        Long clientId = 1L;
        given(creditApplicationRepository.existsByClientId(clientId)).willReturn(true);

        // when
        boolean exists = creditApplicationService.existsByClientId(clientId);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    public void whenExistsByNonExistentClientId_thenReturnFalse() {
        // given
        Long nonExistentClientId = 2L;
        given(creditApplicationRepository.existsByClientId(nonExistentClientId)).willReturn(false);

        // when
        boolean exists = creditApplicationService.existsByClientId(nonExistentClientId);

        // then
        assertThat(exists).isFalse();
    }

    @Test
    public void whenExistsByNullClientId_thenReturnFalse() {
        // given
        Long nullClientId = null;
        given(creditApplicationRepository.existsByClientId(nullClientId)).willReturn(false);

        // when
        boolean exists = creditApplicationService.existsByClientId(nullClientId);

        // then
        assertThat(exists).isFalse();
    }

    @Test
    public void whenExistsByNonExistentClientIdMultipleApplications_thenReturnFalse() {
        // given
        Long nonExistentClientId = 3L;
        given(creditApplicationRepository.existsByClientId(nonExistentClientId)).willReturn(false);

        // when
        boolean exists = creditApplicationService.existsByClientId(nonExistentClientId);

        // then
        assertThat(exists).isFalse();
    }

    @Test
    public void whenExistsByClientIdWithMultipleApplications_thenReturnTrue() {
        // given
        Long clientIdWithMultipleApplications = 4L;
        given(creditApplicationRepository.existsByClientId(clientIdWithMultipleApplications)).willReturn(true);

        // when
        boolean exists = creditApplicationService.existsByClientId(clientIdWithMultipleApplications);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    public void whenCreateCreditApplicationByRut_thenReturnCreditApplication() {
        // given
        String rut = "12345678-9";
        String loanType = "Home Loan";
        ClientEntity client = new ClientEntity(
                1L,              // client_id
                "John Doe",     // name
                rut,            // rut
                "john@example.com", // email
                "123456789",    // phone
                30,             // age
                5000,           // monthly_salary
                null,           // personal_savings (puedes establecerlo como quieras)
                "EMPLOYED",     // job_type
                15000,          // expected_amount
                15,             // time_limit
                5,              // interest_rate
                "Home Loan",    // type_loan
                false,          // independent_activity
                0,              // job_seniority (si no lo necesitas, establece 0)
                null            // actual_job (si no lo necesitas, establece null)
        );

        // Simular el comportamiento del repositorio
        given(clientRepository.findByRut(rut)).willReturn(client);
        given(creditApplicationRepository.save(any(CreditApplicationEntity.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        CreditApplicationEntity createdApplication = creditApplicationService.createCreditApplicationByRut(rut, loanType);

        // then
        assertThat(createdApplication).isNotNull();
        assertThat(createdApplication.getClient_id()).isEqualTo(client.getClient_id());
        assertThat(createdApplication.getName()).isEqualTo(loanType);
        assertThat(createdApplication.getStatus()).isEqualTo("PENDING");
    }
    @Test
    public void whenCreateCreditApplicationWithNonExistentRut_thenThrowEntityNotFoundException() {
        // given
        String rut = "non-existent-rut";
        String loanType = "Home Loan";

        // Simular el comportamiento del repositorio
        given(clientRepository.findByRut(rut)).willReturn(null);

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            creditApplicationService.createCreditApplicationByRut(rut, loanType);
        });
    }








}
