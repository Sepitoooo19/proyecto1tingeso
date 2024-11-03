import httpClient from '../http-common';

const createCreditApplicationByRut = (rut, loanType) => {
    console.log("Rut:", rut, "Loan Type:", loanType);
    return httpClient.post(`http://localhost:8090/api/v1/credit_application/create`, { 
        rut: rut, 
        loan_type: loanType 
    });
}



export default { createCreditApplicationByRut };