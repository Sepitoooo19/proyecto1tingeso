package com.example.PrestaBanco.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.PrestaBanco.entities.ClientEntity;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    //obtener todos los clientes
    @Query("SELECT c FROM ClientEntity c")
    public List<ClientEntity> findAll();

    public ClientEntity findByRut(String rut);

    @Query("SELECT c FROM ClientEntity c WHERE c.phone = :phone")
    ClientEntity findByPhone(@Param("phone") String phone);

    @Query("SELECT c FROM ClientEntity c WHERE c.client_id = :client_id")
    ClientEntity findByClientId(@Param("client_id") Long client_id);

    @Query("SELECT c FROM ClientEntity c WHERE c.name = :name")
    ClientEntity findByName(@Param("name") String name);

    @Query("SELECT c FROM ClientEntity c WHERE LOWER(c.email) = LOWER(:email)")
    ClientEntity findByEmail(@Param("email") String email);

    @Query("SELECT c FROM ClientEntity c WHERE LOWER(c.job_type) = LOWER(:job_type)")
    ClientEntity findByJobType(@Param("job_type") String job_type);

    @Query("SELECT c FROM ClientEntity c WHERE c.type_loan = :type_loan")
    ClientEntity findByTypeLoan(@Param("type_loan") String type_loan);

    @Query("SELECT c FROM ClientEntity c WHERE c.independent_activity = :independent_activity")
    ClientEntity findByIndependentActivity(@Param("independent_activity") boolean independent_activity);

    @Query("SELECT c FROM ClientEntity c WHERE c.age = :age")
    ClientEntity findByAge(@Param("age") int age);

    @Query("SELECT c FROM ClientEntity c WHERE c.monthly_salary = :monthly_salary")
    ClientEntity findByMonthlySalary(@Param("monthly_salary") double monthly_salary);

    @Query("SELECT c FROM ClientEntity c WHERE c.personal_savings = :personal_savings")
    ClientEntity findByPersonalSavings(@Param("personal_savings") Double personal_savings);

    @Query("SELECT c FROM ClientEntity c WHERE c.expected_amount = :expected_amount")
    ClientEntity findByExpectedAmount(@Param("expected_amount") double expected_amount);

    @Query("SELECT c FROM ClientEntity c WHERE c.time_limit = :time_limit")
    ClientEntity findByTimeLimit(@Param("time_limit") int time_limit);

    @Query("SELECT c FROM ClientEntity c WHERE c.interest_rate = :interest_rate")
    ClientEntity findByInterestRate(@Param("interest_rate") double interest_rate);



    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM ClientEntity c WHERE c.rut = :rut")
    boolean existsByRut(String rut);
}

