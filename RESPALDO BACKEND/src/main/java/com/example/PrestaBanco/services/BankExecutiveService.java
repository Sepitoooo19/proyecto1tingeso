package com.example.PrestaBanco.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
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


@Service
public class BankExecutiveService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private EmploymentHistoryRepository employmentHistoryRepository;

    @Autowired
    private CreditApplicationRepository creditApplicationRepository;


    int fireInsurance = 20000;

    @Autowired
    private ClientBankAccountRepository clientBankAccountRepository;


    public double getExpectedAmountOfClientByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        return client.getExpected_amount();

    }

    public double getInteresRateOfClientByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        return client.getInterest_rate();
    }

    public int getTimeLimitOfClientByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        return client.getTime_limit();
    }

    public double getMonthlySalaryOfClientByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        return client.getMonthly_salary();
    }

    public int getMonthlyLoanOfClientByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        double interest_rate = client.getInterest_rate() / 12 / 100;
        double expected_amount = client.getExpected_amount();
        int time_limit_in_months = client.getTime_limit() * 12;
        double monthly_fee = expected_amount * ((interest_rate*(Math.pow(1+interest_rate, time_limit_in_months)))/(Math.pow(1+interest_rate, time_limit_in_months)-1));
        return (int) monthly_fee;
        
    }

    public double getFeeIncomeRatioByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);

        // Verifica que el cliente no sea nulo
        if (client == null) {
            throw new RuntimeException("Cliente no encontrado");
        }

        double monthly_salary = client.getMonthly_salary();
        double monthly_fee = getMonthlyLoanOfClientByRut(rut);
        double feeIncomeRatio = (monthly_salary / monthly_fee) * 100;

        if (feeIncomeRatio > 35) {
            System.out.println("El ratio de ingresos supera el 35%: " + feeIncomeRatio + "%");
        }

        return feeIncomeRatio;
    }


    public double getDebtAmountByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);

        // Verifica que el cliente no sea nulo
        if (client == null || client.getClient_id() == null) {
            return 0; // Retorna 0 si el cliente no existe
        }

        List<DebtEntity> debts = debtRepository.findByClientId(client.getClient_id());

        double debt_amount = 0;
        for (DebtEntity debt : debts) {
            debt_amount += debt.getDebt_amount();
        }
        return debt_amount;
    }

    public List<DebtEntity> getDebtsByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        if (client == null) {
            throw new EntityNotFoundException("Cliente no encontrado para el RUT: " + rut);
        }
        // Código para obtener las deudas basándose en el `client_id` del cliente encontrado
        return debtRepository.findByClientId(client.getClient_id());
    }

    public List<EmploymentHistoryEntity> getEmploymentHistoryByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        return (List<EmploymentHistoryEntity>) employmentHistoryRepository.findByClientId(client.getClient_id());
    }

    public List<CreditApplicationEntity> getCreditApplicationsByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        return creditApplicationRepository.findByClientId(client.getClient_id());
    }


    public List<ClientBankAccountEntity> getClientBankAccountsByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        return clientBankAccountRepository.findByClientId(client.getClient_id());
    }



    //obtener deposito total en el banco si transaction es deposit
    public int getDepositInBankAccountByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        List<ClientBankAccountEntity> clientBankAccounts = clientBankAccountRepository.findByClientId(client.getClient_id());
        int deposit = 0;
        for (ClientBankAccountEntity clientBankAccount : clientBankAccounts) {
            if (clientBankAccount.getTransaction().equals("deposit")) {
                deposit += clientBankAccount.getAmount();
            }
        }
        return deposit;
    }

    //obtener retiro total en el banco si transaction es withdrawal
    public int getWithdrawalInBankAccountByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        List<ClientBankAccountEntity> clientBankAccounts = clientBankAccountRepository.findByClientId(client.getClient_id());
        int withdrawal = 0;
        for (ClientBankAccountEntity clientBankAccount : clientBankAccounts) {
            if (clientBankAccount.getTransaction().equals("withdrawal")) {
                withdrawal += clientBankAccount.getAmount();
            }
        }
        return withdrawal;
    }

    public boolean getAgeAndVerifyMaximumAgeByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        int age = client.getAge();
        int loanTerm = client.getTime_limit(); // Suponiendo que el atributo se llama getLoanTerm()
        int finalAge = age + loanTerm; // Edad del cliente al finalizar el préstamo

        // Si la edad actual es menor de 18, no se puede otorgar el préstamo
        if (age < 18) {
            return false;
        }

        // Si la edad final al finalizar el préstamo es mayor o igual a 75 años
        if (finalAge >= 75) {
            return false;
        }

        // Si el cliente tiene 5 años o menos antes de alcanzar los 75 años al final del préstamo, se rechaza
        if (75 - finalAge < 5) {
            return false;
        }

        // Caso contrario, el préstamo es aceptado
        return true;
    }



//    public boolean isBankAccountBalanceTenPercentageOfMonthlyFeeByRut(String rut) {
//        ClientEntity client = clientRepository.findByRut(rut);
//
//        // Manejo del caso en que el cliente no existe
//        if (client == null) {
//            System.out.println("Cliente no encontrado.");
//            return false; // No existe cliente
//        }
//
//        List<ClientBankAccountEntity> clientBankAccounts = clientBankAccountRepository.findByClientId(client.getClient_id());
//
//
//        int monthly_fee = getMonthlyLoanOfClientByRut(rut);
//
//        // Inicializa el total de depósitos
//        double totalDeposit = 0.0;
//
//        // Calcula el total de depósitos
//        for (ClientBankAccountEntity clientBankAccount : clientBankAccounts) {
//            if ("deposit".equals(clientBankAccount.getTransaction())) {
//                totalDeposit += clientBankAccount.getAmount();
//            }
//        }
//
//        // Calcula el saldo mínimo requerido
//        double requiredMinimumBalance = monthly_fee * 0.1;
//
//        // Evaluar si el saldo cumple con el mínimo requerido
//        return totalDeposit >= requiredMinimumBalance;
//    }

    private boolean isWithinLast12Months(LocalDate transactionDate) {
        LocalDate twelveMonthsAgo = LocalDate.now().minusMonths(12);
        return !transactionDate.isBefore(twelveMonthsAgo);
    }

    public boolean isBankAccountConsistentByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        List<ClientBankAccountEntity> clientBankAccounts = clientBankAccountRepository.findByClientId(client.getClient_id());

        // Variables para el cálculo del saldo y retiros
        int totalDeposits = 0;
        int totalWithdrawals = 0;
        int totalBalance = 0;
        int monthCount = 0;

        // Revisar el historial de cuentas de los últimos 12 meses
        for (ClientBankAccountEntity clientBankAccount : clientBankAccounts) {
            if (clientBankAccount.getTransaction().equals("deposit")) {
                totalDeposits += clientBankAccount.getAmount();
            } else {
                totalWithdrawals += clientBankAccount.getAmount();
            }
            // Suponiendo que tienes una forma de verificar la fecha de las transacciones
            if (isWithinLast12Months(clientBankAccount.getTransaction_date())) {
                totalBalance += clientBankAccount.getAmount();
                monthCount++;
            }
        }

        // Calcular el saldo promedio de los últimos 12 meses
        if (monthCount == 0 || totalBalance <= 0) {
            return false; // No hay transacciones o saldo no positivo
        }

        int averageBalance = totalBalance / monthCount;

        // Verificar si hay retiros significativos
        for (ClientBankAccountEntity clientBankAccount : clientBankAccounts) {
            if (clientBankAccount.getTransaction().equals("withdrawal")) {
                if (clientBankAccount.getAmount() > averageBalance * 0.5) {
                    return false; // Retiro significativo encontrado
                }
            }
        }

        // Verificar el total de retiros
        if (totalWithdrawals > averageBalance * 0.5) {
            return false; // Total de retiros significativo
        }

        return true; // Consistente si pasa todas las validaciones
    }

    public boolean containsBankAccountPeriodicDepositsByRut(String rut) {
        // Obtener el cliente por su RUT
        ClientEntity client = clientRepository.findByRut(rut);
        List<ClientBankAccountEntity> clientBankAccounts = clientBankAccountRepository.findByClientId(client.getClient_id());

        // Fecha actual y hace 12 meses
        LocalDate now = LocalDate.now();
        LocalDate twelveMonthsAgo = now.minusMonths(12);

        // Ingresos mensuales del cliente
        double monthlyIncome = client.getMonthly_salary();
        double minimumRequiredDeposit = monthlyIncome * 0.05;

        // Variables para verificar depósitos regulares
        boolean hasRegularDeposits = false;
        double totalDeposits = 0;
        // Arreglo para marcar si se realizó un depósito en cada mes
        boolean[] monthsWithDeposits = new boolean[12]; // 12 meses
        Arrays.fill(monthsWithDeposits, false);

        // Recorremos todas las cuentas del cliente
        for (ClientBankAccountEntity account : clientBankAccounts) {
            // Verificamos si es un depósito
            if (account.getTransaction().equalsIgnoreCase("deposit")) {
                // Verificamos si la fecha está dentro de los últimos 12 meses
                LocalDate transactionDate = account.getTransaction_date();
                if ((transactionDate.isAfter(twelveMonthsAgo) || transactionDate.isEqual(twelveMonthsAgo))
                        && transactionDate.isBefore(now.plusDays(1))) {
                    // Calcular la diferencia de meses entre la fecha actual y la transacción
                    int monthsDifference = now.getMonthValue() - transactionDate.getMonthValue() +
                            (now.getYear() - transactionDate.getYear()) * 12;

                    // Si está dentro de los 12 meses, marcamos el depósito para ese mes
                    if (monthsDifference >= 0 && monthsDifference < 12) {
                        monthsWithDeposits[monthsDifference] = true;
                    }
                    // Sumar el monto del depósito
                    totalDeposits += account.getAmount();
                }
            }
        }

        // Contar los meses en los que hubo depósitos
        int monthsWithDepositsCount = 0;
        for (boolean hasDeposit : monthsWithDeposits) {
            if (hasDeposit) {
                monthsWithDepositsCount++;
            }
        }

        // Verificar si hubo depósitos al menos en 4 meses diferentes (frecuencia trimestral)
        if (monthsWithDepositsCount >= 4 || (monthsWithDepositsCount >= 1 && totalDeposits >= minimumRequiredDeposit)) {
            hasRegularDeposits = true;
        }

        // Verificar si cumple con el monto mínimo de depósitos
        return hasRegularDeposits && totalDeposits >= minimumRequiredDeposit; // Retorna true si cumple, false si no
    }



//    public boolean verifyBalanceAndAccountAge(String rut) {
//        // Obtener todas las cuentas bancarias del cliente
//        ClientEntity client = clientRepository.findByRut(rut);
//        long clientId = client.getClient_id();
//        List<ClientBankAccountEntity> clientBankAccounts = clientBankAccountRepository.findByClientId(clientId);
//        double loanAmount = getMonthlyLoanOfClientByRut(rut);
//
//        // Verificar si hay cuentas bancarias
//        if (clientBankAccounts.isEmpty()) {
//            return false; // No hay cuentas bancarias para este cliente
//        }
//
//        // Obtener la fecha actual
//        LocalDate now = LocalDate.now();
//
//        // Variables para almacenar el saldo total y la antigüedad mínima de las cuentas
//        double totalBalance = 0;
//        boolean isOlderThanTwoYears = false;
//
//        // Recorrer todas las cuentas bancarias del cliente
//        for (ClientBankAccountEntity account : clientBankAccounts) {
//            // Sumar el saldo de la cuenta
//            totalBalance += account.getAmount();
//
//            // Calcular la antigüedad de la cuenta
//            LocalDate accountOpeningDate = account.getAccount_opening();
//            long yearsWithAccount = ChronoUnit.YEARS.between(accountOpeningDate, now);
//
//            // Verificar si alguna cuenta tiene 2 años o más
//            if (yearsWithAccount >= 2) {
//                isOlderThanTwoYears = true;
//            }
//        }
//
//        // Determinar el porcentaje requerido según la antigüedad de la cuenta
//        double requiredBalance;
//        if (isOlderThanTwoYears) {
//            // Si la cuenta tiene 2 años o más, se requiere el 10% del monto del préstamo
//            requiredBalance = loanAmount * 0.10;
//        } else {
//            // Si la cuenta tiene menos de 2 años, se requiere el 20% del monto del préstamo
//            requiredBalance = loanAmount * 0.20;
//        }
//
//        // Verificar si el saldo total cumple con el saldo requerido
//        return totalBalance >= requiredBalance;
//    }
    public boolean checkRecentWithdrawalsByRut(String rut) {
        // Obtener el cliente por su rut
        ClientEntity client = clientRepository.findByRut(rut);
        List<ClientBankAccountEntity> clientBankAccounts = clientBankAccountRepository.findByClientId(client.getClient_id());

        // Fecha actual y hace 6 meses
        LocalDate now = LocalDate.now();
        LocalDate sixMonthsAgo = now.minusMonths(6);

        // Bandera para determinar si se ha hecho un retiro grande
        boolean hasLargeWithdrawals = false;

        // Recorremos todas las transacciones de las cuentas del cliente
        for (ClientBankAccountEntity account : clientBankAccounts) {
            // Verificamos si es un retiro
            if (account.getTransaction().equalsIgnoreCase("withdrawal")) {
                // Verificamos si la transacción está dentro de los últimos 6 meses
                LocalDate transactionDate = account.getTransaction_date();
                if ((transactionDate.isAfter(sixMonthsAgo) || transactionDate.isEqual(sixMonthsAgo))
                        && transactionDate.isBefore(now.plusDays(1))) {
                    // Si el retiro es mayor al 30% del saldo en la cuenta
                    if (account.getAmount() > account.getAmount() * 0.30) {
                        hasLargeWithdrawals = true;
                        break;  // No necesitamos revisar más, ya cumplió la condición
                    }
                }
            }
        }

        return hasLargeWithdrawals; // Retorna true si hubo un retiro mayor al 30% del saldo
    }


    public int insuranceCalculationByRut(String rut) {

        ClientEntity client = clientRepository.findByRut(rut);
        double expected_amount = client.getExpected_amount();
        double credit_life_insurance = 0.0003;

        // Redondear el resultado de la multiplicación
        int insurance = (int) Math.round(expected_amount * credit_life_insurance);
        return insurance;
    }

    public int administrationCommissionByRut(String rut){

        ClientEntity client = clientRepository.findByRut(rut);
        double expected_amount = client.getExpected_amount();
        double administration_commission = 0.01;

        int commission = (int) (expected_amount * administration_commission);
        return commission;

    }

    public int monthlyCostByRut(String rut){

        ClientEntity client = clientRepository.findByRut(rut);
        int monthly_fee = getMonthlyLoanOfClientByRut(rut);
        int insurance = insuranceCalculationByRut(rut);
        int monthlyCostofClient = monthly_fee + insurance + fireInsurance;

        return monthlyCostofClient;

    }

    public int totalCostOfLoanByRut(String rut) {

        ClientEntity client = clientRepository.findByRut(rut);
        int monthlyCostofClient = monthlyCostByRut(rut);
        int commission = administrationCommissionByRut(rut);
        int totalCost = (monthlyCostofClient * (client.getTime_limit() * 12)) + commission;

        return totalCost;
    }

    public String  getLoanTypeClientByRut(String rut){
        ClientEntity client = clientRepository.findByRut(rut);
        return client.getType_loan();
    }

    public CreditApplicationEntity getCreditApplicationById(Long credit_application_id) {
        Optional<CreditApplicationEntity> creditApplication = creditApplicationRepository.findById(credit_application_id);
        if (creditApplication.isPresent()) {
            return creditApplication.get();
        } else {
            throw new EntityNotFoundException("Solicitud de crédito no encontrada con el ID: " + credit_application_id);
        }
    }

    public CreditApplicationEntity updateStatusOfCreditApplication(Long credit_application_id, String status) {
        // Validaciones
        if (credit_application_id == null || credit_application_id <= 0) {
            throw new IllegalArgumentException("Invalid credit application ID");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        // Obtener la solicitud de crédito
        CreditApplicationEntity creditApplication = getCreditApplicationById(credit_application_id);

        // Actualizar el estado
        creditApplication.setStatus(status);
        return creditApplicationRepository.save(creditApplication);
    }

    public int getPendingDebtsByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);
        List<DebtEntity> debts = debtRepository.findByClientId(client.getClient_id());
        int pendingDebts = 0;
        for (DebtEntity debt : debts) {
            if (debt.getDebt_status().equals("pending")) {
                pendingDebts = (int)debt.getDebt_amount() + pendingDebts;
            }
        }
        return pendingDebts;
    }

    public double getPendingDebtsMonthlySalaryByRut(String rut) {
        ClientEntity client = clientRepository.findByRut(rut);

        // Verificar si el cliente existe
        if (client == null) {
            throw new EntityNotFoundException("Client not found with RUT: " + rut);
        }

        List<DebtEntity> debts = debtRepository.findByClientId(client.getClient_id());
        int monthly_fee = getMonthlyLoanOfClientByRut(rut);

        int pendingDebts = 0;
        for (DebtEntity debt : debts) {
            if (debt.getDebt_status().equals("pending")) {
                pendingDebts = (int)debt.getDebt_amount() + pendingDebts;
            }
        }
        pendingDebts += monthly_fee;
        double ratio = (pendingDebts / client.getMonthly_salary()) * 100;
        return ratio;
    }





}
