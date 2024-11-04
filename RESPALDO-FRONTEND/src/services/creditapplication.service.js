import httpClient from '../http-common';

const createCreditApplicationByRut = (rut, loanType) => {
    console.log("Rut:", rut, "Loan Type:", loanType);
    return httpClient.post(`http://prestabanco-app.westus2.cloudapp.azure.com/api/v1/credit_application/create`, { 
        rut: rut, 
        loan_type: loanType 
    });
}



export default { createCreditApplicationByRut };