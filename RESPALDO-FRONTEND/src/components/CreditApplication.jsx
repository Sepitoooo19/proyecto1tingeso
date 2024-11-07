import React, { useState } from "react";
import documentService from "../services/document.service";
import bankService from "../services/bank.executive";
import creditApplicationService from "../services/creditapplication.service"; 
import { TextField, Button, Typography, Box, Grid, InputLabel, FormControl, Tabs, Tab } from '@mui/material';

const CreditApplication = () => {
    const [files, setFiles] = useState([null, null, null, null]);
    const [rut, setRut] = useState("");
    const [status, setStatus] = useState("");
    const [tabIndex, setTabIndex] = useState(0);
    const [loanInfo, setLoanInfo] = useState({ loanType: '', timeLimit: '', interestRate: '' });
    const [createStatus, setCreateStatus] = useState(""); 
    const [amount, setAmount] = useState(0); 

    const handleFileChange = (index, e) => {
        const newFiles = [...files];
        newFiles[index] = e.target.files[0];
        setFiles(newFiles);
    };

    const handleRutChange = (e) => {
        setRut(e.target.value);
    };

    const handleUpload = async () => {
        if (files.every(file => !file) || !rut) {
            setStatus("Por favor selecciona al menos un archivo y proporciona el RUT del cliente.");
            return;
        }
    
        try {
            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                if (file) {
                    console.log(`Subiendo archivo: ${file.name}`);
                    await documentService.uploadDocument(file, file.name, file.type, rut);
                }
            }
            setStatus("Documentos subidos con éxito");
        } catch (error) {
            console.error("Error al subir el documento:", error);
            setStatus("Error al subir el documento");
        }
    };

    const calculateMonthlyLoan = async (rut) => {
        try {
            const response = await bankService.getMonthlyLoanOfClientByRut(rut);
            return response.data;
        } catch (error) {
            console.error("Error al calcular el monto mensual del préstamo:", error);
            throw error;
        }
    };

    

    const fetchLoanInfo = async () => {
        if (!rut) {
            setStatus("Por favor, proporciona el RUT del cliente.");
            return;
        }
    
        try {
            const monthlyLoan = await calculateMonthlyLoan(rut);
            setAmount(monthlyLoan); // Actualizar el estado con el monto calculado
            console.log("Monto mensual del préstamo:", monthlyLoan);
            const [loanTypeResponse, timeLimitResponse, interestRateResponse, monthlyLoanResponse] = await Promise.all([
                bankService.getLoanTypeByRut(rut),
                bankService.getTimeLimitOfClientByRut(rut),
                bankService.getInteresRateOfClientByRut(rut),
                bankService.getMonthlyLoanOfClientByRut(rut), // Asegúrate de que este método devuelva el monto correcto
            ]);
    
            setLoanInfo({
                loanType: loanTypeResponse.data,
                timeLimit: timeLimitResponse.data,
                interestRate: interestRateResponse.data,
                amount: monthlyLoanResponse.data,
            });
    
            // Almacena el monto mensual en el estado
            setAmount(monthlyLoanResponse.data); // Aquí se obtiene el monto mensual
            
    
            setStatus("Información de préstamo obtenida con éxito");
        } catch (error) {
            console.error("Error al obtener información de préstamo:", error);
            const errorMessage = error.response ? error.response.data : "Error desconocido";
            setStatus(`Error al obtener información de préstamo: ${errorMessage}`);
        }
    };

    const verifyCreditApplication = async (rut) => {
    try {
        const response = await creditApplicationService.getCreditApplicationByRut(rut);
        console.log("Datos de la solicitud de crédito guardada:", response.data); // Verifica los datos guardados
        return response.data;
    } catch (error) {
        console.error("Error al verificar la solicitud de crédito:", error);
        throw error;
    }
};
    
    const createCreditApplication = async () => {
        if (!rut || !loanInfo.loanType) {
            setCreateStatus("Por favor, proporciona el RUT del cliente y el tipo de préstamo.");
            return;
        }
        
        try {
            const monthlyLoan = await calculateMonthlyLoan(rut);
            console.log("Monto mensual antes de crear la solicitud:", monthlyLoan); // Verifica el monto antes de crear la solicitud
            const creditApplicationData = {
                client_id: rut, // Asegúrate de que esto sea correcto
                loan_type: loanInfo.loanType,
                amount: monthlyLoan, // Usa el estado amount que contiene el monto mensual
                status: 'Pendiente', // O el estado que consideres necesario
                credit_date: new Date().toISOString().split('T')[0], // Formato de fecha (ajusta si es necesario)
            };

        
            setAmount(monthlyLoan); // Actualiza el estado con el monto calculado
            const response = await creditApplicationService.createCreditApplicationByRut(rut, loanInfo.loanType); // Cambia esto si es necesario
            console.log("Solicitud de crédito creada con éxito:", response.data); // Verifica la respuesta
            if (response.status === 201) {
                setCreateStatus("Solicitud de crédito creada con éxito.");
            } else {
                setCreateStatus("Error al crear la solicitud de crédito.");
            }
        } catch (error) {
            console.error("Error al crear la solicitud de crédito:", error);
            const errorMessage = error.response ? error.response.data : "Error desconocido";
            setCreateStatus(`Error al crear la solicitud de crédito: ${errorMessage}`);
        }
    };

    

    const handleTabChange = (event, newValue) => {
        setTabIndex(newValue);
        setStatus(""); 
        setLoanInfo({ loanType: '', timeLimit: '', interestRate: '' }); 
        setCreateStatus(""); 
    };

    return (
        <Box sx={{ p: 2 }}>
            <Typography variant="h4" gutterBottom>
                Subir Documentos PDF
            </Typography>
            <Tabs value={tabIndex} onChange={handleTabChange}>
                <Tab label="Subir Documentos" />
                <Tab label="Obtener Información de Préstamo" />
            </Tabs>
            {tabIndex === 0 && (
                <Grid container spacing={2}>
                    {files.map((file, index) => (
                        <Grid item xs={12} key={index}>
                            <FormControl fullWidth>
                                <InputLabel htmlFor={`file-input-${index}`}>Seleccionar archivo {index + 1}</InputLabel>
                                <input
                                    id={`file-input-${index}`}
                                    type="file"
                                    accept="application/pdf"
                                    onChange={(e) => handleFileChange(index, e)}
                                    style={{ display: 'none' }}
                                />
                                <Button
                                    variant="outlined"
                                    component="span"
                                    onClick={() => document.getElementById(`file-input-${index}`).click()}
                                >
                                    {file ? file.name : `Seleccionar archivo ${index + 1}`}
                                </Button>
                            </FormControl>
                        </Grid>
                    ))}
                    <TextField
                        label="Ingrese el RUT del cliente"
                        variant="outlined"
                        value={rut}
                        onChange={handleRutChange}
                        fullWidth
                        margin="normal"
                    />
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={handleUpload}
                        sx={{ mt: 2 }}
                    >
                        Subir Documentos (Chile)
                    </Button>
                </Grid>
            )}
            {tabIndex === 1 && (
                <Box sx={{ mt: 2 }}>
                    <TextField
                        label="Ingrese el RUT del cliente"
                        variant="outlined"
                        value={rut}
                        onChange={handleRutChange}
                        fullWidth
                        margin="normal"
                    />
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={fetchLoanInfo}
                        sx={{ mt: 2 }}
                    >
                        Obtener Información de Préstamo
                    </Button>
                    <Button
                        variant="contained"
                        color="secondary"
                        onClick={createCreditApplication}
                        sx={{ mt: 2, ml: 2 }} 
                    >
                        Crear Solicitud de Crédito
                    </Button>
                    <Typography variant="body1" sx={{ mt: 2 }}>
                        {status}
                    </Typography>
                    {loanInfo.loanType && (
                        <Box sx={{ mt: 2 }}>
                            <Typography variant="h6">Tipo de Préstamo: {loanInfo.loanType}</Typography>
                            <Typography variant="h6">Límite de Tiempo: {loanInfo.timeLimit}</Typography>
                            <Typography variant="h6">Tasa de Interés: {loanInfo.interestRate}</Typography>
                            <Typography variant="h6">Monto Calculado: {amount}</Typography>
                        </Box>
                    )}
                    {createStatus && <Typography variant="body1" sx={{ mt: 2 }}>{createStatus}</Typography>}
                </Box>
            )}
            <Typography variant="body1" sx={{ mt: 2 }}>
                {status}
            </Typography>
        </Box>
    );
};

export default CreditApplication;
