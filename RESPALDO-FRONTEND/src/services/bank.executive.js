import httpClient from '../http-common';

const getDebtsByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/debts`);
}

const getEmploymentHistoryByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/employment-history`);
}

const getBankAccountByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/client-bank-account`);
}

const getDepositInBankAccountByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/deposit`);
}

const getWithdrawalInBankAccountByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/withdrawal`);
}

const getExpectedAmountOfClientByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/amount`);
}

const getInteresRateOfClientByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/interest`);
}

const getTimeLimitOfClientByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/time`);
}

const getMonthlyLoanOfClientByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/monthly-loan`);
}

const getLoanTypeByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/loanType`);
}

const getCreditApplicationsByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/credit-application`);
}

const getCreditApplicationById = (id) => {
    return httpClient.get(`/api/executives/credit-application/${id}`);
}

const getFeeIncomeRatioByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/fee-income`);
}

const updateCreditApplicationStatus = (id, newStatus) => {
    return httpClient.put(`/api/executives/${id}/status`, { status: newStatus });
}

const getPendingDebtsByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/pending-debts`);
}

const getPendingDebtsMonthlySalaryByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/pending-debts-salary-ratio`);
}

const verifyClientAge = (rut) => {
    return httpClient.get(`/api/executives/${rut}/verify-age`);
}

const getClientByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}`);
}

//const isBankAccountBalanceTenPercentageOfMonthlyFeeByRut = (rut) => {
//    return httpClient.get(`http://localhost:8090/api/executives/${rut}/check-balance`);
//}

const validateBankAccountConsistencyByRut = (rut) => {
    return httpClient.get(`/api/executives/${rut}/account-consistency`);
}

const checkPeriodicDeposits = (rut) => {
    return httpClient.get(`/api/executives/clients/${rut}/periodic-deposits`);
}

//const checkJobSeniorityAmountRatio = (rut) => {
 //   return httpClient.get(`http://localhost:8090/api/executives/${rut}/verify-balance-and-age`);
//}

const checkLargeWithdrawals = (rut) => {
    return httpClient.get(`/api/executives/check-large-withdrawals/${rut}`);
}

const insuranceCalculationByRut = (rut) => {
    return httpClient.get(`/api/executives/insurance/${rut}`);
}

const administrationCommissionByRut = (rut) => {
    return httpClient.get(`/api/executives/administration-commission/${rut}`);
}

const monthlyCostByRut = (rut) => {
    return httpClient.get(`/api/executives/monthly-cost/${rut}`);
}

const totalCostOfLoanByRut = (rut) => {
    return httpClient.get(`/api/executives/total-cost/${rut}`);
}




export default { 
    getDebtsByRut, 
    getEmploymentHistoryByRut, 
    getBankAccountByRut, 
    getDepositInBankAccountByRut,
    getWithdrawalInBankAccountByRut, 
    getExpectedAmountOfClientByRut, 
    getInteresRateOfClientByRut, 
    getTimeLimitOfClientByRut, 
    getMonthlyLoanOfClientByRut,
    getLoanTypeByRut,
    getCreditApplicationsByRut,
    getCreditApplicationById,
    getFeeIncomeRatioByRut,
    updateCreditApplicationStatus,
    getPendingDebtsByRut,
    getPendingDebtsMonthlySalaryByRut,
    verifyClientAge,
    getClientByRut,
    //isBankAccountBalanceTenPercentageOfMonthlyFeeByRut,
    validateBankAccountConsistencyByRut,
    checkPeriodicDeposits,
    //checkJobSeniorityAmountRatio,
    checkLargeWithdrawals,
    insuranceCalculationByRut,
    administrationCommissionByRut,
    monthlyCostByRut,
    totalCostOfLoanByRut
};