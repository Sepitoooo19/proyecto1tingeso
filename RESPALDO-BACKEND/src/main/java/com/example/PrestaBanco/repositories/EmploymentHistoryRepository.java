package com.example.PrestaBanco.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.PrestaBanco.entities.EmploymentHistoryEntity;

import java.util.List;

@Repository
public interface EmploymentHistoryRepository extends JpaRepository<EmploymentHistoryEntity, Long> {


    public
    @Query("SELECT e FROM EmploymentHistoryEntity e WHERE e.employment_id = :employment_id")
    EmploymentHistoryEntity findByEmploymentHistoryId(@Param("employment_id") Long employment_id);

    @Query("SELECT e FROM EmploymentHistoryEntity e WHERE e.client_id = :client_id")
    List<EmploymentHistoryEntity> findByClientId(@Param("client_id") Long client_id);

    @Query("SELECT e FROM EmploymentHistoryEntity e WHERE LOWER(e.job_title) = LOWER(:job_title)")
    EmploymentHistoryEntity findByJobTitle(@Param("job_title") String job_title);

    @Query("SELECT e FROM EmploymentHistoryEntity e WHERE LOWER(e.company_name) = LOWER(:company_name)")
    EmploymentHistoryEntity findByCompanyName(@Param("company_name") String company_name);

    @Query("SELECT e FROM EmploymentHistoryEntity e WHERE e.salary = :salary")
    EmploymentHistoryEntity findBySalary(@Param("salary") double salary);

    @Query("SELECT e FROM EmploymentHistoryEntity e WHERE e.time_of_employment = :time_of_employment")
    EmploymentHistoryEntity findByTimeOfEmployment(@Param("time_of_employment") int time_of_employment);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM EmploymentHistoryEntity e WHERE e.employment_id = :employment_id")
    boolean existsByEmploymentId(Long employment_id);





}
