package com.example.PrestaBanco.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PrestaBanco.repositories.EmploymentHistoryRepository;
import com.example.PrestaBanco.entities.EmploymentHistoryEntity;
import java.util.List;

@Service
public class EmploymentHistoryService {

    @Autowired
    private EmploymentHistoryRepository employmentHistoryRepository;

    public List<EmploymentHistoryEntity> findAll() {
        return employmentHistoryRepository.findAll();
    }

    public EmploymentHistoryEntity findByEmploymentHistoryId(Long employmentHistory_id) {
        return employmentHistoryRepository.findByEmploymentHistoryId(employmentHistory_id);
    }

    public EmploymentHistoryEntity findByJobTitle(String job_title) {
        return employmentHistoryRepository.findByJobTitle(job_title);
    }

    public EmploymentHistoryEntity findByCompanyName(String company_name) {
        return employmentHistoryRepository.findByCompanyName(company_name);
    }

    public EmploymentHistoryEntity findBySalary(double salary) {
        return employmentHistoryRepository.findBySalary(salary);
    }

    public EmploymentHistoryEntity findByTimeOfEmployment(int time_of_employment) {
        return employmentHistoryRepository.findByTimeOfEmployment(time_of_employment);
    }

    public boolean existsByEmploymentId(Long employment_id) {
        return employmentHistoryRepository.existsByEmploymentId(employment_id);
    }

    public List<EmploymentHistoryEntity> findByClientId(Long client_id) {
        return employmentHistoryRepository.findByClientId(client_id);
    }



}
