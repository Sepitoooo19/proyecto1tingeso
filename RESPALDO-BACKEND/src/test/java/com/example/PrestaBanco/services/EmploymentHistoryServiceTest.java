package com.example.PrestaBanco.services;

import com.example.PrestaBanco.entities.EmploymentHistoryEntity;
import com.example.PrestaBanco.repositories.EmploymentHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class EmploymentHistoryServiceTest {

    @Mock
    private EmploymentHistoryRepository employmentHistoryRepository;

    @InjectMocks
    private EmploymentHistoryService employmentHistoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenFindAllAndNoEmploymentHistories_thenReturnEmptyList() {
        // given
        given(employmentHistoryRepository.findAll()).willReturn(new ArrayList<>());

        // when
        List<EmploymentHistoryEntity> result = employmentHistoryService.findAll();

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }
    @Test
    public void whenFindAllAndThereAreEmploymentHistories_thenReturnList() {
        // given
        List<EmploymentHistoryEntity> employmentHistories = new ArrayList<>();
        employmentHistories.add(new EmploymentHistoryEntity(1L, 1L, "Software Engineer", "Tech Co", 60000, 24));
        employmentHistories.add(new EmploymentHistoryEntity(2L, 1L, "Senior Developer", "Another Tech Co", 80000, 36));
        given(employmentHistoryRepository.findAll()).willReturn(employmentHistories);

        // when
        List<EmploymentHistoryEntity> result = employmentHistoryService.findAll();

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getJob_title()).isEqualTo("Software Engineer");
        assertThat(result.get(1).getJob_title()).isEqualTo("Senior Developer");
    }

    @Test
    public void whenFindAll_thenCallRepositoryFindAll() {
        // given
        given(employmentHistoryRepository.findAll()).willReturn(new ArrayList<>());

        // when
        employmentHistoryService.findAll();

        // then
        verify(employmentHistoryRepository, times(1)).findAll();
    }

    @Test
    public void whenFindAllThrowsException_thenHandleException() {
        // given
        given(employmentHistoryRepository.findAll()).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            employmentHistoryService.findAll();
        });
    }

    @Test
    public void whenFindByEmploymentHistoryIdAndExists_thenReturnEmploymentHistory() {
        // given
        Long employmentHistoryId = 1L;
        EmploymentHistoryEntity employmentHistory = new EmploymentHistoryEntity(employmentHistoryId, 1L, "Software Engineer", "Tech Co", 60000, 24);
        given(employmentHistoryRepository.findByEmploymentHistoryId(employmentHistoryId)).willReturn(employmentHistory);

        // when
        EmploymentHistoryEntity result = employmentHistoryService.findByEmploymentHistoryId(employmentHistoryId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getEmployment_id()).isEqualTo(employmentHistoryId);
        assertThat(result.getJob_title()).isEqualTo("Software Engineer");
    }

    @Test
    public void whenFindByEmploymentHistoryIdAndDoesNotExist_thenReturnNull() {
        // given
        Long employmentHistoryId = 999L; // ID que no existe
        given(employmentHistoryRepository.findByEmploymentHistoryId(employmentHistoryId)).willReturn(null);

        // when
        EmploymentHistoryEntity result = employmentHistoryService.findByEmploymentHistoryId(employmentHistoryId);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void whenFindByEmploymentHistoryId_thenCallRepositoryFindById() {
        // given
        Long employmentHistoryId = 1L;
        given(employmentHistoryRepository.findByEmploymentHistoryId(employmentHistoryId)).willReturn(new EmploymentHistoryEntity());

        // when
        employmentHistoryService.findByEmploymentHistoryId(employmentHistoryId);

        // then
        verify(employmentHistoryRepository, times(1)).findByEmploymentHistoryId(employmentHistoryId);
    }

    @Test
    public void whenFindByEmploymentHistoryIdThrowsException_thenHandleException() {
        // given
        Long employmentHistoryId = 1L;
        given(employmentHistoryRepository.findByEmploymentHistoryId(employmentHistoryId)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            employmentHistoryService.findByEmploymentHistoryId(employmentHistoryId);
        });
    }

    @Test
    public void whenFindByJobTitleAndExists_thenReturnEmploymentHistory() {
        // given
        String jobTitle = "Software Engineer";
        EmploymentHistoryEntity employmentHistory = new EmploymentHistoryEntity(1L, 1L, jobTitle, "Tech Co", 60000, 24);
        given(employmentHistoryRepository.findByJobTitle(jobTitle)).willReturn(employmentHistory);

        // when
        EmploymentHistoryEntity result = employmentHistoryService.findByJobTitle(jobTitle);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getJob_title()).isEqualTo(jobTitle);
    }

    @Test
    public void whenFindByJobTitleAndDoesNotExist_thenReturnNull() {
        // given
        String jobTitle = "Non-Existent Job Title";
        given(employmentHistoryRepository.findByJobTitle(jobTitle)).willReturn(null);

        // when
        EmploymentHistoryEntity result = employmentHistoryService.findByJobTitle(jobTitle);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void whenFindByJobTitle_thenCallRepositoryFindByJobTitle() {
        // given
        String jobTitle = "Software Engineer";
        given(employmentHistoryRepository.findByJobTitle(jobTitle)).willReturn(new EmploymentHistoryEntity());

        // when
        employmentHistoryService.findByJobTitle(jobTitle);

        // then
        verify(employmentHistoryRepository, times(1)).findByJobTitle(jobTitle);
    }

    @Test
    public void whenFindByJobTitleThrowsException_thenHandleException() {
        // given
        String jobTitle = "Software Engineer";
        given(employmentHistoryRepository.findByJobTitle(jobTitle)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            employmentHistoryService.findByJobTitle(jobTitle);
        });
    }

    @Test
    public void whenFindByCompanyNameAndExists_thenReturnEmploymentHistory() {
        // given
        String companyName = "Tech Co";
        EmploymentHistoryEntity employmentHistory = new EmploymentHistoryEntity(1L, 1L, "Software Engineer", companyName, 60000, 24);
        given(employmentHistoryRepository.findByCompanyName(companyName)).willReturn(employmentHistory);

        // when
        EmploymentHistoryEntity result = employmentHistoryService.findByCompanyName(companyName);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getCompany_name()).isEqualTo(companyName);
    }

    @Test
    public void whenFindByCompanyNameAndDoesNotExist_thenReturnNull() {
        // given
        String companyName = "Non-Existent Company";
        given(employmentHistoryRepository.findByCompanyName(companyName)).willReturn(null);

        // when
        EmploymentHistoryEntity result = employmentHistoryService.findByCompanyName(companyName);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void whenFindByCompanyName_thenCallRepositoryFindByCompanyName() {
        // given
        String companyName = "Tech Co";
        given(employmentHistoryRepository.findByCompanyName(companyName)).willReturn(new EmploymentHistoryEntity());

        // when
        employmentHistoryService.findByCompanyName(companyName);

        // then
        verify(employmentHistoryRepository, times(1)).findByCompanyName(companyName);
    }

    @Test
    public void whenFindByCompanyNameThrowsException_thenHandleException() {
        // given
        String companyName = "Tech Co";
        given(employmentHistoryRepository.findByCompanyName(companyName)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            employmentHistoryService.findByCompanyName(companyName);
        });
    }

    @Test
    public void whenFindBySalaryAndExists_thenReturnEmploymentHistory() {
        // given
        double salary = 60000;
        EmploymentHistoryEntity employmentHistory = new EmploymentHistoryEntity(1L, 1L, "Software Engineer", "Tech Co", salary, 24);
        given(employmentHistoryRepository.findBySalary(salary)).willReturn(employmentHistory);

        // when
        EmploymentHistoryEntity result = employmentHistoryService.findBySalary(salary);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getSalary()).isEqualTo(salary);
    }

    @Test
    public void whenFindBySalaryAndDoesNotExist_thenReturnNull() {
        // given
        double salary = 50000;
        given(employmentHistoryRepository.findBySalary(salary)).willReturn(null);

        // when
        EmploymentHistoryEntity result = employmentHistoryService.findBySalary(salary);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void whenFindBySalary_thenCallRepositoryFindBySalary() {
        // given
        double salary = 60000;
        given(employmentHistoryRepository.findBySalary(salary)).willReturn(new EmploymentHistoryEntity());

        // when
        employmentHistoryService.findBySalary(salary);

        // then
        verify(employmentHistoryRepository, times(1)).findBySalary(salary);
    }

    @Test
    public void whenFindBySalaryThrowsException_thenHandleException() {
        // given
        double salary = 60000;
        given(employmentHistoryRepository.findBySalary(salary)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            employmentHistoryService.findBySalary(salary);
        });
    }

    @Test
    public void whenFindByTimeOfEmploymentAndExists_thenReturnEmploymentHistory() {
        // given
        int timeOfEmployment = 24;
        EmploymentHistoryEntity employmentHistory = new EmploymentHistoryEntity(1L, 1L, "Software Engineer", "Tech Co", 60000, timeOfEmployment);
        given(employmentHistoryRepository.findByTimeOfEmployment(timeOfEmployment)).willReturn(employmentHistory);

        // when
        EmploymentHistoryEntity result = employmentHistoryService.findByTimeOfEmployment(timeOfEmployment);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTime_of_employment()).isEqualTo(timeOfEmployment);
    }

    @Test
    public void whenFindByTimeOfEmploymentAndDoesNotExist_thenReturnNull() {
        // given
        int timeOfEmployment = 12;
        given(employmentHistoryRepository.findByTimeOfEmployment(timeOfEmployment)).willReturn(null);

        // when
        EmploymentHistoryEntity result = employmentHistoryService.findByTimeOfEmployment(timeOfEmployment);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void whenFindByTimeOfEmployment_thenCallRepositoryFindByTimeOfEmployment() {
        // given
        int timeOfEmployment = 24;
        given(employmentHistoryRepository.findByTimeOfEmployment(timeOfEmployment)).willReturn(new EmploymentHistoryEntity());

        // when
        employmentHistoryService.findByTimeOfEmployment(timeOfEmployment);

        // then
        verify(employmentHistoryRepository, times(1)).findByTimeOfEmployment(timeOfEmployment);
    }

    @Test
    public void whenFindByTimeOfEmploymentThrowsException_thenHandleException() {
        // given
        int timeOfEmployment = 24;
        given(employmentHistoryRepository.findByTimeOfEmployment(timeOfEmployment)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            employmentHistoryService.findByTimeOfEmployment(timeOfEmployment);
        });
    }

    @Test
    public void whenExistsByEmploymentIdAndExists_thenReturnTrue() {
        // given
        Long employmentId = 1L;
        given(employmentHistoryRepository.existsByEmploymentId(employmentId)).willReturn(true);

        // when
        boolean result = employmentHistoryService.existsByEmploymentId(employmentId);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void whenExistsByEmploymentIdAndDoesNotExist_thenReturnFalse() {
        // given
        Long employmentId = 2L;
        given(employmentHistoryRepository.existsByEmploymentId(employmentId)).willReturn(false);

        // when
        boolean result = employmentHistoryService.existsByEmploymentId(employmentId);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void whenExistsByEmploymentId_thenCallRepositoryExistsByEmploymentId() {
        // given
        Long employmentId = 1L;
        given(employmentHistoryRepository.existsByEmploymentId(employmentId)).willReturn(true);

        // when
        employmentHistoryService.existsByEmploymentId(employmentId);

        // then
        verify(employmentHistoryRepository, times(1)).existsByEmploymentId(employmentId);
    }

    @Test
    public void whenExistsByEmploymentIdThrowsException_thenHandleException() {
        // given
        Long employmentId = 1L;
        given(employmentHistoryRepository.existsByEmploymentId(employmentId)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            employmentHistoryService.existsByEmploymentId(employmentId);
        });
    }

    @Test
    public void whenFindByClientIdAndExists_thenReturnEmploymentHistoryList() {
        // given
        Long clientId = 1L;
        EmploymentHistoryEntity employmentHistory1 = new EmploymentHistoryEntity(1L, clientId, "Software Engineer", "Tech Co", 60000, 24);
        EmploymentHistoryEntity employmentHistory2 = new EmploymentHistoryEntity(2L, clientId, "Data Analyst", "Data Co", 50000, 12);
        List<EmploymentHistoryEntity> employmentHistories = Arrays.asList(employmentHistory1, employmentHistory2);
        given(employmentHistoryRepository.findByClientId(clientId)).willReturn(employmentHistories);

        // when
        List<EmploymentHistoryEntity> result = employmentHistoryService.findByClientId(clientId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void whenFindByClientIdAndDoesNotExist_thenReturnEmptyList() {
        // given
        Long clientId = 1L;
        given(employmentHistoryRepository.findByClientId(clientId)).willReturn(Collections.emptyList());

        // when
        List<EmploymentHistoryEntity> result = employmentHistoryService.findByClientId(clientId);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    public void whenFindByClientId_thenCallRepositoryFindByClientId() {
        // given
        Long clientId = 1L;
        given(employmentHistoryRepository.findByClientId(clientId)).willReturn(new ArrayList<>());

        // when
        employmentHistoryService.findByClientId(clientId);

        // then
        verify(employmentHistoryRepository, times(1)).findByClientId(clientId);
    }

    @Test
    public void whenFindByClientIdThrowsException_thenHandleException() {
        // given
        Long clientId = 1L;
        given(employmentHistoryRepository.findByClientId(clientId)).willThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            employmentHistoryService.findByClientId(clientId);
        });
    }


}
