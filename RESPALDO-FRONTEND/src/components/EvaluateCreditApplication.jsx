import React, { useState, useEffect } from "react";
import bankExecutiveService from "../services/bank.executive";
import { TextField, Button, Typography, Box, Grid, Tabs, Tab } from '@mui/material';

const CreditApplicationById = () => {
    const [tabValue, setTabValue] = useState(0);
    const [creditApplicationId, setCreditApplicationId] = useState("");
    const [creditApplication, setCreditApplication] = useState(null);
    const [status, setStatus] = useState("");
    const [rut, setRut] = useState("");
    const [feeIncomeRatio, setFeeIncomeRatio] = useState(null);
    const [pendingDebtsRatio, setPendingDebtsRatio] = useState(null);
    const [ageVerification, setAgeVerification] = useState(null);
    const [finalAge, setFinalAge] = useState(null); // Estado para almacenar la edad final
    const [balanceVerification, setBalanceVerification] = useState(null);
    const [consistencyVerification, setConsistencyVerification] = useState(null);
    const [periodicDeposits, setPeriodicDeposits] = useState(null);
    const [jobSeniorityAmountRatio, setJobSeniorityAmountRatio] = useState(null);
    const [largeWithdrawals, setLargeWithdrawals] = useState(null);
    const [accountCapacity, setAccountCapacity] = useState('Esperando Evaluación');
    const [loanDetails, setLoanDetails] = useState(null);
    const [monthlyCost, setMonthlyCost] = useState(null);
    const [insurance, setInsurance] = useState(null);
    const [adminCommission, setAdminCommission] = useState(null);
    const [totalCost, setTotalCost] = useState(null);


    const handleTabChange = (event, newValue) => {
        setTabValue(newValue);
    };

    const handleIdChange = (e) => {
        setCreditApplicationId(e.target.value);
    };

    const fetchCreditApplication = async () => {
        if (!creditApplicationId) {
            setStatus("Por favor, proporciona el ID de la solicitud de crédito.");
            return;
        }

        try {
            const response = await bankExecutiveService.getCreditApplicationById(creditApplicationId);
            setCreditApplication(response.data);
            setStatus("Solicitud de crédito obtenida con éxito.");
        } catch (error) {
            console.error("Error al obtener la solicitud de crédito:", error);
            const errorMessage = error.response ? error.response.data : "Error desconocido";
            setStatus(`Error al obtener la solicitud de crédito: ${errorMessage}`);
            setCreditApplication(null);
        }
    };

    const updateCreditApplicationStatus = async (newStatus) => {
        if (!creditApplicationId) {
            setStatus("Por favor, proporciona el ID de la solicitud de crédito.");
            return;
        }

        try {
            console.log("Updating status to", newStatus);
            await bankExecutiveService.updateCreditApplicationStatus(creditApplicationId, newStatus);
            setStatus(`Estado actualizado a ${newStatus}.`);
            fetchCreditApplication();
        } catch (error) {
            console.error("Error al actualizar el estado de la solicitud de crédito:", error);
            const errorMessage = error.response ? error.response.data : "Error desconocido";
            setStatus(`Error al actualizar el estado: ${errorMessage}`);
        }
    };

    const handleRutChange = (e) => {
        setRut(e.target.value);
    };

    const calculateFeeIncomeRatio = async () => {
        if (!rut) {
            setStatus("Por favor, proporciona el RUT del cliente.");
            return;
        }

        try {
            const response = await bankExecutiveService.getFeeIncomeRatioByRut(rut);
            setFeeIncomeRatio(response.data);
            setStatus("Relación ingreso/préstamo calculada con éxito.");
        } catch (error) {
            console.error("Error al calcular la relación ingreso/préstamo:", error);
            const errorMessage = error.response ? error.response.data : "Error desconocido";
            setStatus(`Error al calcular la relación ingreso/préstamo: ${errorMessage}`);
            setFeeIncomeRatio(null);
        }
    };

    const calculatePendingDebtsRatio = async () => {
        if (!rut) {
            setStatus("Por favor, proporciona el RUT del cliente.");
            return;
        }

        try {
            const response = await bankExecutiveService.getPendingDebtsMonthlySalaryByRut(rut);
            setPendingDebtsRatio(response.data);
            setStatus("Relación de deudas pendientes calculada con éxito.");
        } catch (error) {
            console.error("Error al calcular la relación de deudas pendientes:", error);
            const errorMessage = error.response ? error.response.data : "Error desconocido";
            setStatus(`Error al calcular la relación de deudas pendientes: ${errorMessage}`);
            setPendingDebtsRatio(null);
        }
    };

    const verifyClientAge = async () => {
        if (!rut) {
            setStatus("Por favor, proporciona el RUT del cliente.");
            return;
        }

        try {
            const response = await bankExecutiveService.verifyClientAge(rut);
            setAgeVerification(response.data);
            
            // Obtener el cliente para calcular la edad final
            const clientResponse = await bankExecutiveService.getClientByRut(rut); 
            const client = clientResponse.data;
            const age = client.age;
            const loanTerm = client.time_limit; 
            const finalAge = age + loanTerm; 
            setFinalAge(finalAge);
            
            setStatus("Verificación de edad realizada con éxito.");
        } catch (error) {
            console.error("Error al verificar la edad del cliente:", error);
            const errorMessage = error.response ? error.response.data : "Error desconocido";
            setStatus(`Error al verificar la edad del cliente: ${errorMessage}`);
            setAgeVerification(null);
            setFinalAge(null); // Restablece la edad final en caso de error
        }
    };

/*     const verifyBankAccountBalance = async () => {
        if (!rut) {
            setStatus("Por favor, proporciona el RUT del cliente.");
            return;
        }
    
        try {
            const response = await bankExecutiveService.isBankAccountBalanceTenPercentageOfMonthlyFeeByRut(rut);
    
            // Almacena el mensaje exacto del backend en `balanceVerification`
            const message = response.data;
            setBalanceVerification(message); // Guarda el mensaje devuelto
            setStatus("Verificación de saldo de cuenta realizada con éxito.");
            
        } catch (error) {
            console.error("Error al verificar el saldo de la cuenta:", error);
            const errorMessage = error.response && error.response.data 
                ? error.response.data 
                : "No se pudo calcular el saldo. Intente nuevamente más tarde.";
            setStatus(`Error al verificar el saldo de la cuenta: ${errorMessage}`);
            setBalanceVerification(null);
        }
    }; 
    
    */


    const validateBankAccountConsistency = async () => {
        if (!rut) {
            setStatus("Por favor, proporciona el RUT del cliente.");
            return;
        }
    
        try {
            const response = await bankExecutiveService.validateBankAccountConsistencyByRut(rut);
    
            
            const message = response.data;
            setConsistencyVerification(message); // Guarda el mensaje devuelto
            setStatus("Verificación de consistencia de cuenta realizada con éxito.");
            
        } catch (error) {
            console.error("Error al verificar la consistencia de la cuenta:", error);
            const errorMessage = error.response && error.response.data 
                ? error.response.data 
                : "No se pudo determinar la consistencia de la cuenta bancaria.";
            setStatus(`Error al verificar la consistencia de la cuenta: ${errorMessage}`);
            setConsistencyVerification(null);
        }
    };
    const checkPeriodicDeposits = async () => {
        if (!rut) {
            setStatus("Por favor, proporciona el RUT del cliente.");
            return;
        }
    
        try {
            const response = await bankExecutiveService.checkPeriodicDeposits(rut);
    
            
            const message = response.data;
            setPeriodicDeposits(message); // Guarda el mensaje devuelto
            setStatus("Verificación de depósitos periódicos realizada con éxito.");
            
        } catch (error) {
            console.error("Error al verificar los depósitos periódicos:", error);
            const errorMessage = error.response && error.response.data
                ? error.response.data
                : "No se pudo verificar la información del cliente";
            setStatus(`Error al verificar los depósitos periódicos: ${errorMessage}`);
            setPeriodicDeposits(null);
        }
    };

/*     const checkJobSeniorityAmountRatio = async () => {
        if (!rut) {
            setStatus("Por favor, proporciona el RUT del cliente.");
            return;
        }
    
        try {
            const response = await bankExecutiveService.checkJobSeniorityAmountRatio(rut);
    
            // Almacena el mensaje completo del backend en `jobSeniorityAmountRatio`
            const message = response.data;  // Obtiene el mensaje desde la respuesta
            setJobSeniorityAmountRatio(message); // Guarda el mensaje devuelto
            setStatus(message); // Muestra el mensaje del backend
    
        } catch (error) {
            console.error("Error al verificar la relación de antigüedad en el trabajo y monto:", error);
            const errorMessage = error.response && error.response.data
                ? error.response.data
                : "No se pudo verificar la información del cliente";
            setStatus(`Error al verificar la relación de antigüedad en el trabajo y monto: ${errorMessage}`);
            setJobSeniorityAmountRatio(null);
        }
    }; */


    const checkLargeWithdrawals = async () => {
        if (!rut) {
            setStatus("Por favor, proporciona el RUT del cliente.");
            return;
        }
    
        try {
            const response = await bankExecutiveService.checkLargeWithdrawals(rut);
    
            // Guarda el mensaje del backend directamente en `largeWithdrawals`
            const message = response.data;
            setLargeWithdrawals(message); // Guarda el mensaje completo
            setStatus("Verificación de retiros grandes realizada con éxito.");
            
        } catch (error) {
            console.error("Error al verificar los retiros grandes:", error);
            const errorMessage = error.response && error.response.data
                ? error.response.data
                : "Error desconocido";
            setStatus(`Error al verificar los retiros grandes: ${errorMessage}`);
            setLargeWithdrawals(null);
        }
    };

    const fetchLoanDetails = async () => {
        try {
            const expectedAmountResponse = await bankExecutiveService.getExpectedAmountOfClientByRut(rut);
            const timeLimitResponse = await bankExecutiveService.getTimeLimitOfClientByRut(rut);
            const interestRateResponse = await bankExecutiveService.getInteresRateOfClientByRut(rut);

            const loanDetailsData = {
                expectedAmount: expectedAmountResponse.data,
                timeLimit: timeLimitResponse.data,
                interestRate: interestRateResponse.data
            };

            setLoanDetails(loanDetailsData);
        } catch (error) {
            console.error("Error fetching loan details:", error);
        }
    };

    const calculateInsurance = async () => {
        try {
            const response = await bankExecutiveService.insuranceCalculationByRut(rut);
            setInsurance(response.data);
        } catch (error) {
            console.error("Error calculating insurance:", error);
        }
    };

    const calculateAdminCommission = async () => {
        try {
            const response = await bankExecutiveService.administrationCommissionByRut(rut);
            setAdminCommission(response.data);
        } catch (error) {
            console.error("Error calculating administration commission:", error);
        }
    };

    const calculateMonthlyCost = async () => {
        try {
            const response = await bankExecutiveService.monthlyCostByRut(rut);
            setMonthlyCost(response.data);
        } catch (error) {
            console.error("Error calculating monthly cost:", error);
        }

    };

    const calculateTotalCost = async () => {
        try {
            const response = await bankExecutiveService.totalCostOfLoanByRut(rut);
            setTotalCost(response.data);
        } catch (error) {
            console.error("Error calculating total cost of loan:", error);
        }
    };



    



    useEffect(() => {
        console.log("Nuevo estado:", status);
    }, [status]);

    return (
        <Box sx={{ p: 2 }}>
            <Tabs
                value={tabValue}
                onChange={handleTabChange}
                variant="scrollable"
                scrollButtons="auto"
            >
                <Tab label="Solicitud de Crédito por ID" />
                <Tab label="Calcular Relación Ingreso/Préstamo" />
                <Tab label="Calcular Deudas Pendientes" />
                <Tab label="Verificar Edad del Cliente" />
                <Tab label="Verificar Estabilidad Cuenta de Ahorro" />
                <Tab label="Obtener Costo Total del Prestamo" />
            </Tabs>

            {tabValue === 0 && (
                <Box sx={{ mt: 2 }}>
                    <Typography variant="h4" gutterBottom>
                        Buscar Solicitud de Crédito por ID
                    </Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                label="Ingrese el ID de la solicitud de crédito"
                                variant="outlined"
                                value={creditApplicationId}
                                onChange={handleIdChange}
                                fullWidth
                                margin="normal"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={fetchCreditApplication}
                                sx={{ mt: 2 }}
                            >
                                Obtener Solicitud de Crédito
                            </Button>
                        </Grid>
                    </Grid>
                    <Typography variant="body1" sx={{ mt: 2 }}>
                        {status}
                    </Typography>
                    {creditApplication && (
                        <Box sx={{ mt: 2 }}>
                            <Typography variant="h6">Detalles de la Solicitud de Crédito:</Typography>
                            <Typography variant="body1">ID: {creditApplication.credit_application_id}</Typography>
                            <Typography variant="body1">Tipo de Préstamo: {creditApplication.name}</Typography>
                            <Typography variant="body1">Monto: {creditApplication.amount}</Typography>
                            <Typography variant="body1">Fecha de Crédito: {creditApplication.credit_date}</Typography>
                            <Typography variant="body1">Estado: {creditApplication.status}</Typography>
                            <Box sx={{ mt: 2 }}>
                                <Button
                                    variant="contained"
                                    color="secondary"
                                    onClick={() => updateCreditApplicationStatus("Pendiente")}
                                    sx={{ mr: 2 }}
                                >
                                    Marcar como PENDING
                                </Button>
                                <Button
                                    variant="contained"
                                    color="error"
                                    onClick={() => updateCreditApplicationStatus("Rechazada")}
                                >
                                    Marcar como DECLINED
                                </Button>
                                <Button
                                    variant="contained"
                                    color="secondary"
                                    onClick={() => updateCreditApplicationStatus("En Evaluación Inicial")}
                                    sx={{ mr: 2 }}
                                >
                                    Marcar como " En Revisión Inicial"
                                </Button>
                                <Button
                                    variant="contained"
                                    color="secondary"
                                    onClick={() => updateCreditApplicationStatus("En Evaluación")}
                                    sx={{ mr: 2 }}
                                >
                                    Marcar como "En Evaluación"
                                </Button>
                                <Button
                                    variant="contained"
                                    color="secondary"
                                    onClick={() => updateCreditApplicationStatus("Pre-Aprobada")}
                                    sx={{ mr: 2 }}
                                >
                                    Marcar como "Pre-Aprobada"
                                </Button>
                                <Button
                                    variant="contained"
                                    color="secondary"
                                    onClick={() => updateCreditApplicationStatus(" En Aprobación Final")}
                                    sx={{ mr: 2 }}
                                >
                                    Marcar como " En Aprobación Final"
                                </Button>
                                <Button
                                    variant="contained"
                                    color="secondary"
                                    onClick={() => updateCreditApplicationStatus("Aprobada")}
                                    sx={{ mr: 2 }}
                                >
                                    Marcar como "Aprobada"
                                </Button>
                                <Button
                                    variant="contained"
                                    color="secondary"
                                    onClick={() => updateCreditApplicationStatus("Cancelada por el Cliente")}
                                    sx={{ mr: 2 }}
                                >
                                    Marcar como "Cancelada por el Cliente"
                                </Button>
                                <Button
                                    variant="contained"
                                    color="secondary"
                                    onClick={() => updateCreditApplicationStatus("En Desembolso")}
                                    sx={{ mr: 2 }}
                                >
                                    Marcar como "En Desembolso"
                                </Button>
                            </Box>
                        </Box>
                    )}
                </Box>
            )}

            {tabValue === 1 && (
                <Box sx={{ mt: 2 }}>
                    <Typography variant="h4" gutterBottom>
                        Calcular Relación Ingreso/Préstamo
                    </Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                label="Ingrese el RUT del cliente"
                                variant="outlined"
                                value={rut}
                                onChange={handleRutChange}
                                fullWidth
                                margin="normal"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={calculateFeeIncomeRatio}
                                sx={{ mt: 2 }}
                            >
                                Calcular Relación Ingreso/Préstamo
                            </Button>
                        </Grid>
                    </Grid>
                    <Typography variant="body1" sx={{ mt: 2 }}>
                        {status}
                    </Typography>
                    {feeIncomeRatio && (
                        <Box sx={{ mt: 2 }}>
                            <Typography variant="h6">Relación Ingreso/Préstamo:</Typography>
                            <Typography variant="body1">Relación: {feeIncomeRatio}</Typography>
                        </Box>
                    )}
                </Box>
            )}

            {tabValue === 2 && (
                <Box sx={{ mt: 2 }}>
                    <Typography variant="h4" gutterBottom>
                        Calcular Deudas Pendientes
                    </Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                label="Ingrese el RUT del cliente"
                                variant="outlined"
                                value={rut}
                                onChange={handleRutChange}
                                fullWidth
                                margin="normal"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={calculatePendingDebtsRatio}
                                sx={{ mt: 2 }}
                            >
                                Calcular Deudas Pendientes
                            </Button>
                        </Grid>
                    </Grid>
                    <Typography variant="body1" sx={{ mt: 2 }}>
                        {status}
                    </Typography>
                    {pendingDebtsRatio && (
                        <Box sx={{ mt: 2 }}>
                            <Typography variant="h6">Relación de Deudas Pendientes:</Typography>
                            <Typography variant="body1">Relación: {pendingDebtsRatio}% del salario mensual</Typography>
                        </Box>
                    )}
                </Box>
            )}

            {tabValue === 3 && (
                <Box sx={{ mt: 2 }}>
                    <Typography variant="h4" gutterBottom>
                        Verificar Edad del Cliente
                    </Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                label="Ingrese el RUT del cliente"
                                variant="outlined"
                                value={rut}
                                onChange={handleRutChange}
                                fullWidth
                                margin="normal"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={verifyClientAge}
                                sx={{ mt: 2 }}
                            >
                                Verificar Edad
                            </Button>
                        </Grid>
                    </Grid>
                    <Typography variant="body1" sx={{ mt: 2 }}>
                        {status}
                    </Typography>
                    {ageVerification !== null && (
                        <Box sx={{ mt: 2 }}>
                            <Typography variant="h6">Resultado de Verificación de Edad:</Typography>
                            <Typography variant="body1">
                                {ageVerification ? "El cliente está en la edad válida para un préstamo." : "El cliente NO está en la edad válida para un préstamo."}
                            </Typography>
                            {finalAge !== null && (
                                <Typography variant="body1">
                                    Edad al finalizar el préstamo: {finalAge} años
                                </Typography>
                            )}
                        </Box>
                    )}
                </Box>
            )}

            {tabValue === 4 && ( // Nueva pestaña
                <Box sx={{ mt: 2 }}>
                    <Typography variant="h4" gutterBottom>
                        Verificar Saldo de Cuenta
                    </Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                label="Ingrese el RUT del cliente"
                                variant="outlined"
                                value={rut}
                                onChange={handleRutChange}
                                fullWidth
                                margin="normal"
                            />
                        </Grid>
                        {/* <Grid item xs={12}>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={verifyBankAccountBalance}
                                sx={{ mt: 2 }}
                            >
                                Verificar Saldo
                            </Button>
                        </Grid> */}

                        <Grid>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={validateBankAccountConsistency}
                                sx={{ mt: 2 }}
                            >
                                Verificar Consistencia de la Cuenta
                            </Button>
                        </Grid>
                        <Grid>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={checkPeriodicDeposits}
                                sx={{ mt: 2 }}
                            >
                                Verificar Depósitos Periódicos
                            </Button>
                        </Grid>
                        {/* <Grid>
                            <Button

                                variant="contained"
                                color="primary"
                                onClick={checkJobSeniorityAmountRatio}
                                sx={{ mt: 2 }}
                            >
                                Verificar Relación de Antigüedad en el Trabajo y Monto
                            </Button>
                        </Grid> */}
                        <Grid>
                            <Button

                                variant="contained"
                                color="primary"
                                onClick={checkLargeWithdrawals}
                                sx={{ mt: 2 }}
                            >
                                Verificar Retiros Grandes
                            </Button>

                        </Grid>
                    </Grid>

                    <Grid item xs={12}>
                            <Typography variant="h6" sx={{ mt: 2 }}>
                                Capacidad de Cuenta: {accountCapacity}
                            </Typography>
                            <Button variant="contained" onClick={() => setAccountCapacity('Sólida')} sx={{ mt: 2, mr: 2 }}>
                                Sólida
                            </Button>
                            <Button variant="contained" onClick={() => setAccountCapacity('Moderada')} sx={{ mt: 2, mr: 2 }}>
                                Moderada
                            </Button>
                            <Button variant="contained" onClick={() => setAccountCapacity('Insuficiente')} sx={{ mt: 2 }}>
                                Insuficiente
                            </Button>
                        </Grid>
                    {consistencyVerification !== null && (
                        <Box sx={{ mt: 2 }}>
                            <Typography variant="h6">Resultado de Verificación de Consistencia:</Typography>
                            <Typography variant="body1">
                                {consistencyVerification} {/* Muestra el mensaje completo del backend */}
                            </Typography>
                        </Box>
                    )}
                    {periodicDeposits !== null && (
                        <Box sx={{ mt: 2 }}>
                            <Typography variant="h6">Resultado de Verificación de Depósitos Periódicos:</Typography>
                            <Typography variant="body1">
                                {periodicDeposits} {/* Muestra el mensaje completo del backend */}
                            </Typography>
                        </Box>
                    )}
                    

                    {largeWithdrawals !== null && (
                        <Box sx={{ mt: 2 }}>
                            <Typography variant="h6">Resultado de Verificación de Retiros Grandes:</Typography>
                            <Typography variant="body1">
                                {largeWithdrawals} {/* Muestra el mensaje completo del backend */}
                            </Typography>
                        </Box>
                    )}
                </Box>
            )}
            {tabValue === 5 && (
                <Box sx={{ p: 2 }}>
                <Typography variant="h4">Detalles del Préstamo</Typography>
                <TextField
                    label="Ingrese el RUT"
                    variant="outlined"
                    value={rut}
                    onChange={(e) => setRut(e.target.value)}
                    sx={{ mb: 2 }}
                />
                <Button variant="contained" onClick={fetchLoanDetails} sx={{ mb: 2 }}>Obtener Detalles del Préstamo</Button>
    
                {loanDetails && (
                    <Box sx={{ mt: 2 }}>
                        <Typography variant="h6">Monto del préstamo: ${loanDetails.expectedAmount}</Typography>
                        <Typography variant="body1">Plazo: {loanDetails.timeLimit} años</Typography>
                        <Typography variant="body1">Tasa de interés anual: {loanDetails.interestRate}%</Typography>
                        <Typography variant="body1">Tasa de interés mensual: {loanDetails.interestRate / 12}%</Typography>
                        <Typography variant="body1">Seguro de desgravamen: 0.03% del monto del préstamo por mes.</Typography>
                        <Typography variant="body1">Seguro de incendio: $20,000 por mes.</Typography>
                        <Typography variant="body1">Comisión por administración: 1% del monto del préstamo.</Typography>
                    </Box>
                )}
    
                <Button variant="contained" onClick={calculateInsurance}>Calcular Seguro</Button>
                <Button variant="contained" onClick={calculateAdminCommission}>Calcular Comisión</Button>
                <Button variant="contained" onClick={calculateMonthlyCost}>Calcular Costo Mensual</Button>
                <Button variant="contained" onClick={calculateTotalCost}>Calcular Costo Total</Button>
    
                {insurance && <Typography>Seguro: ${insurance}</Typography>}
                {adminCommission && <Typography>Comisión: ${adminCommission}</Typography>}
                {monthlyCost && <Typography>Costo Mensual: ${monthlyCost}</Typography>}
                {totalCost && <Typography>Costo Total: {totalCost}</Typography>}
            </Box>
            )}

        </Box>
    );
};

export default CreditApplicationById;
