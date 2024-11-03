import { useState } from "react";
import { TextField, Button, Box, Paper, Typography } from "@mui/material";
import bankExecutiveService from "../services/bank.executive";

const CreditSimulation = () => {
    const [rut, setRut] = useState("");
    const [interestRate, setInterestRate] = useState("");
    const [timeLimit, setTimeLimit] = useState("");
    const [expectedAmount, setExpectedAmount] = useState("");
    const [monthlyLoan, setMonthlyLoan] = useState("");
    const [showResults, setShowResults] = useState(false);

    const handleRutChange = (e) => {
        setRut(e.target.value);
    };

    const fetchClientData = () => {
        if (rut) {
            bankExecutiveService.getInteresRateOfClientByRut(rut)
                .then(response => setInterestRate(response.data))
                .catch(error => console.error("Error al obtener el interest rate", error));

            bankExecutiveService.getTimeLimitOfClientByRut(rut)
                .then(response => setTimeLimit(response.data))
                .catch(error => console.error("Error al obtener el time limit", error));

            bankExecutiveService.getExpectedAmountOfClientByRut(rut)
                .then(response => setExpectedAmount(response.data))
                .catch(error => console.error("Error al obtener el expected amount", error));

            setShowResults(true);
        }
    };

    const calculateMonthlyLoan = () => {
        if (rut) {
            bankExecutiveService.getMonthlyLoanOfClientByRut(rut)
                .then(response => setMonthlyLoan(response.data))
                .catch(error => console.error("Error al calcular el monthly loan", error));
        }
    };

    return (
        <Paper elevation={3} sx={{ padding: 3, maxWidth: 400, margin: "auto" }}>
            <Typography variant="h5" component="h2" gutterBottom>
                Solicitud de Crédito
            </Typography>
            <Box component="form" sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
                <TextField
                    label="RUT"
                    variant="outlined"
                    value={rut}
                    onChange={handleRutChange}
                />
                <Button variant="contained" color="primary" onClick={fetchClientData}>
                    Obtener Datos
                </Button>
                <Button variant="contained" color="secondary" onClick={calculateMonthlyLoan}>
                    Calcular Costo Mensual
                </Button>
                {showResults && (
                    <>
                        <TextField
                            label="Tasa de Interés"
                            variant="outlined"
                            value={interestRate}
                            InputProps={{ readOnly: true }}
                        />
                        <TextField
                            label="Plazo Máximo"
                            variant="outlined"
                            value={timeLimit}
                            InputProps={{ readOnly: true }}
                        />
                        <TextField
                            label="Monto Esperado"
                            variant="outlined"
                            value={expectedAmount}
                            InputProps={{ readOnly: true }}
                        />
                        <TextField
                            label="Prestamo Mensual"
                            variant="outlined"
                            value={monthlyLoan}
                            InputProps={{ readOnly: true }}
                        />
                    </>
                )}
            </Box>
        </Paper>
    );
};

export default CreditSimulation;