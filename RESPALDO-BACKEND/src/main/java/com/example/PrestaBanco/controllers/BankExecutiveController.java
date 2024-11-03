package com.example.PrestaBanco.controllers;

import com.example.PrestaBanco.entities.*;
import com.example.PrestaBanco.services.BankExecutiveService;
import com.example.PrestaBanco.services.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/executives")
@CrossOrigin("*")
public class BankExecutiveController {

    @Autowired
    private BankExecutiveService bankExecutiveService;

    @Autowired
    private ClientService clientService;



    @GetMapping
    public List<ClientEntity> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/{rut}")
    public ResponseEntity<ClientEntity> getClientByRut(@PathVariable String rut) {
        ClientEntity client = clientService.findByRut(rut);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/{rut}/amount")
    public ResponseEntity<Double> getExpectedAmountOfClientByRut(@PathVariable String rut) {
        double expectedAmount = bankExecutiveService.getExpectedAmountOfClientByRut(rut);
        return new ResponseEntity<>(expectedAmount, HttpStatus.OK);
    }

    @GetMapping("/{rut}/interest")
    public ResponseEntity<Double> getInteresRateOfClientByRut(@PathVariable String rut) {
        double interestRate = bankExecutiveService.getInteresRateOfClientByRut(rut);
        return new ResponseEntity<>(interestRate, HttpStatus.OK);
    }

    @GetMapping("/{rut}/time")
    public ResponseEntity<Integer> getTimeLimitOfClientByRut(@PathVariable String rut) {
        int timeLimit = bankExecutiveService.getTimeLimitOfClientByRut(rut);
        return new ResponseEntity<>(timeLimit, HttpStatus.OK);
    }

    @GetMapping("/{rut}/monthly-loan")
    public ResponseEntity<?> getMonthlyLoanOfClientByRut(@PathVariable String rut) {
        try {
            double monthlyLoan = bankExecutiveService.getMonthlyLoanOfClientByRut(rut);
            return ResponseEntity.ok(monthlyLoan);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Client with RUT " + rut + " not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while calculating the monthly loan.");
        }
    }

    @GetMapping("/{rut}/monthly-salary")
    public ResponseEntity<Double> getMonthlySalaryOfClientByRut(@PathVariable String rut) {
        double monthlySalary = bankExecutiveService.getMonthlySalaryOfClientByRut(rut);
        return new ResponseEntity<>(monthlySalary, HttpStatus.OK);
    }

    @GetMapping("/{rut}/fee-income")
    public ResponseEntity<String> getFeeIncomeOfClientByRut(@PathVariable String rut) {
        double feeIncomeRatio = bankExecutiveService.getFeeIncomeRatioByRut(rut);

        String message;
        if (feeIncomeRatio > 35) {
            message = "La solicitud ha sido rechazada: la relación cuota/ingreso es " + feeIncomeRatio + "%, que supera el límite permitido del 35%.";
        } else {
            message = "La solicitud está dentro del límite y será considerada para aprobación. Relación cuota/ingreso: " + feeIncomeRatio + "%.";
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{rut}/debt-amount")
    public ResponseEntity<Double> getDebtAmountByRut(@PathVariable String rut) {
        double debtAmount = bankExecutiveService.getDebtAmountByRut(rut);
        return new ResponseEntity<>(debtAmount, HttpStatus.OK);
    }

    @GetMapping("/{rut}/debts")
    public ResponseEntity<List<DebtEntity>> getDebtsByRut(@PathVariable String rut) {
        List<DebtEntity> debts = bankExecutiveService.getDebtsByRut(rut);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/{rut}/employment-history")
    public ResponseEntity<List<EmploymentHistoryEntity>> getEmploymentHistoryByRut(@PathVariable String rut) {
        List<EmploymentHistoryEntity> employmentHistory = bankExecutiveService.getEmploymentHistoryByRut(rut);
        return new ResponseEntity<>(employmentHistory, HttpStatus.OK);

    }

    @GetMapping("/{rut}/credit-application")
    public ResponseEntity<List<CreditApplicationEntity>> getCreditApplicationByRut(@PathVariable String rut) {

        List<CreditApplicationEntity> creditApplication = bankExecutiveService.getCreditApplicationsByRut(rut);
        return new ResponseEntity<>(creditApplication, HttpStatus.OK);

    }


    @GetMapping("/{rut}/client-bank-account")
    public ResponseEntity<List<ClientBankAccountEntity>> getClientBankAccountByRut(@PathVariable String rut) {
        List<ClientBankAccountEntity> clientBankAccount = bankExecutiveService.getClientBankAccountsByRut(rut);
        return new ResponseEntity<>(clientBankAccount, HttpStatus.OK);
    }

    @GetMapping("/{rut}/deposit")
    public ResponseEntity<Integer> getDepositInBankAccountByRut(@PathVariable String rut) {
        int deposit = bankExecutiveService.getDepositInBankAccountByRut(rut);
        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }

    @GetMapping("/{rut}/withdrawal")
    public ResponseEntity<Integer> getWithdrawalInBankAccountByRut(@PathVariable String rut) {
        int withdrawal = bankExecutiveService.getWithdrawalInBankAccountByRut(rut);
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/{rut}/verify-age")
    public ResponseEntity<Boolean> verifyClientAge(@PathVariable String rut) {
        boolean isEligible = bankExecutiveService.getAgeAndVerifyMaximumAgeByRut(rut);

        if (isEligible) {
            return ResponseEntity.ok(true); // El cliente es elegible para el préstamo
        } else {
            return ResponseEntity.ok(false); // El cliente no es elegible para el préstamo
        }
    }

//    @GetMapping("/{rut}/check-balance")
//    public ResponseEntity<String> checkAccountBalance(@PathVariable String rut) {
//        try {
//            boolean isBalanceSufficient = bankExecutiveService.isBankAccountBalanceTenPercentageOfMonthlyFeeByRut(rut);
//
//            if (isBalanceSufficient) {
//                return ResponseEntity.ok("El saldo es suficiente.");
//            } else {
//                return ResponseEntity.ok("El saldo es inferior al 10% del monto del préstamo solicitado.");
//            }
//        } catch (Exception e) {
//            // Manejar la excepción y retornar un mensaje genérico
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo calcular el saldo. Intente nuevamente más tarde.");
//        }
//    }

    @GetMapping("/{rut}/account-consistency")
    public ResponseEntity<String> checkBankAccountConsistency(@PathVariable String rut) {
        try {
            boolean isConsistent = bankExecutiveService.isBankAccountConsistentByRut(rut);

            // Devuelve un mensaje de éxito o inconsistencia
            if (isConsistent) {
                return ResponseEntity.ok("La cuenta bancaria es consistente.");
            } else {
                return ResponseEntity.ok("La cuenta bancaria no es consistente.");
            }
        } catch (Exception e) {
            // Manejo de errores genérico, puedes personalizarlo según tus necesidades
            return ResponseEntity.badRequest().body("No se pudo determinar la consistencia de la cuenta bancaria.");
        }
    }

    @GetMapping("/clients/{rut}/periodic-deposits")
    public ResponseEntity<String> checkPeriodicDeposits(@PathVariable String rut) {
        try {
            boolean hasPeriodicDeposits = bankExecutiveService.containsBankAccountPeriodicDepositsByRut(rut);

            if (hasPeriodicDeposits) {
                return ResponseEntity.ok("El cliente cumple con los requisitos de depósitos periódicos.");
            } else {
                return ResponseEntity.ok("El cliente no cumple con los requisitos de depósitos periódicos.");
            }
        } catch (Exception e) {
            // Si ocurre una excepción (como cliente no encontrado), retorna un BadRequest
            return ResponseEntity.badRequest().body("No se pudo verificar la información del cliente: " + e.getMessage());
        }
    }

//    @GetMapping("/{rut}/verify-balance-and-age")
//    public ResponseEntity<String> verifyBalanceAndAccountAge(@PathVariable String rut) {
//        try {
//            boolean isEligible = bankExecutiveService.verifyBalanceAndAccountAge(rut);
//
//            if (isEligible) {
//                return ResponseEntity.ok("El cliente es elegible para el préstamo.");
//            } else {
//                return ResponseEntity.ok("El cliente no es elegible para el préstamo.");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("No se pudo verificar la información del cliente: " + e.getMessage());
//        }
//    }

    @GetMapping("/check-large-withdrawals/{rut}")
    public ResponseEntity<String> checkLargeWithdrawals(@PathVariable String rut) {
        boolean hasLargeWithdrawals = bankExecutiveService.checkRecentWithdrawalsByRut(rut);

        if (hasLargeWithdrawals) {
            return ResponseEntity.ok("El cliente ha realizado un retiro superior al 30% del saldo en los últimos 6 meses.");
        } else {
            return ResponseEntity.ok("El cliente no ha realizado retiros significativos en los últimos 6 meses.");
        }
    }

    @GetMapping("/insurance/{rut}")
    public ResponseEntity<Integer> calculateInsurance(@PathVariable String rut) {
        int insurance = bankExecutiveService.insuranceCalculationByRut(rut);
        return ResponseEntity.ok(insurance);
    }

    @GetMapping("/total-cost/{rut}")
    public ResponseEntity<String> calculateTotalCost(@PathVariable String rut) {

        // Obtener el costo total del préstamo
        int totalCost = bankExecutiveService.totalCostOfLoanByRut(rut);

        // Crear el mensaje que incluye el total y una explicación
        String message = String.format("El costo total del préstamo, considerando todas las cuotas, seguros y la comisión inicial, es de $%,d.", totalCost);

        // Devolver el mensaje como respuesta
        return ResponseEntity.ok(message);
    }

    @GetMapping("/administration-commission/{rut}")
    public ResponseEntity<Integer> calculateAdministrationCommission(@PathVariable String rut) {
        int commission = bankExecutiveService.administrationCommissionByRut(rut);
        return ResponseEntity.ok(commission);
    }

    @GetMapping("/monthly-cost/{rut}")
    public ResponseEntity<Integer> calculateMonthlyCost(@PathVariable String rut) {
        int monthlyCost = bankExecutiveService.monthlyCostByRut(rut);
        return ResponseEntity.ok(monthlyCost);
    }

    @GetMapping("/{rut}/loanType")
    public String getLoanTypeClientByRut(@PathVariable String rut) {
        return bankExecutiveService.getLoanTypeClientByRut(rut);
    }

    @GetMapping("/credit-application/{id}")
    public ResponseEntity<CreditApplicationEntity> getCreditApplicationById(@PathVariable("id") Long creditApplicationId) {
        try {
            CreditApplicationEntity creditApplication = bankExecutiveService.getCreditApplicationById(creditApplicationId);
            return ResponseEntity.ok(creditApplication);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{credit_application_id}/status")
    public ResponseEntity<CreditApplicationEntity> updateCreditApplicationStatus(
            @PathVariable Long credit_application_id,
            @RequestBody Map<String, String> requestBody) {
        // Verificar que el cuerpo de la solicitud contenga el campo "status"
        String status = requestBody.get("status");
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Llamar al servicio para actualizar el estado
        try {
            CreditApplicationEntity updatedCreditApplication = bankExecutiveService.updateStatusOfCreditApplication(credit_application_id, status);
            return ResponseEntity.ok(updatedCreditApplication);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{rut}/pending-debts")
    public ResponseEntity<Integer> getPendingDebtsByRut(@PathVariable String rut) {
        int pendingDebts = bankExecutiveService.getPendingDebtsByRut(rut);
        return ResponseEntity.ok(pendingDebts);
    }

    @GetMapping("/{rut}/pending-debts-salary-ratio")
    public ResponseEntity<Double> getPendingDebtsMonthlySalaryByRut(@PathVariable String rut) {
        double ratio = bankExecutiveService.getPendingDebtsMonthlySalaryByRut(rut);
        return ResponseEntity.ok(ratio);
    }


}

