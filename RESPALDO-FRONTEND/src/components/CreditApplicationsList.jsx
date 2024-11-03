import { useEffect, useState } from 'react';
import { useParams, useNavigate } from "react-router-dom";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import bankExecutiveService from "../services/bank.executive.js"; // Importa el servicio

const CreditApplicationList = () => {
    const { rut } = useParams(); // Obtiene el RUT de los parámetros de la URL
    const [creditApplications, setCreditApplications] = useState([]);
    const [inputRut, setInputRut] = useState(''); // Estado para manejar el RUT ingresado
    const navigate = useNavigate(); // Para navegar a la nueva ruta

    useEffect(() => {
        if (rut) { // Solo obtener datos si el RUT es válido
            fetchCreditApplications(rut);
        }
    }, [rut]);

    const fetchCreditApplications = (rut) => {
        bankExecutiveService
            .getCreditApplicationsByRut(rut)
            .then((response) => {
                setCreditApplications(response.data);
            })
            .catch((error) => {
                console.log("Error al obtener las solicitudes de crédito del cliente:", error);
            });
    };


    const handleSearch = () => {
        if (inputRut) {
            navigate(`/credit-applications/${inputRut}`); // Navegar a la ruta con el RUT ingresado
            fetchCreditApplications(inputRut); // Obtener las solicitudes de crédito para el nuevo RUT
            setInputRut(''); // Limpiar el campo de entrada
        } else {
            alert("Por favor, ingrese un RUT válido."); // Mensaje de advertencia si no se ingresa un RUT
        }
    };

    return (
        <div>
            <TextField
                label="Ingrese RUT"
                variant="outlined"
                value={inputRut}
                onChange={(e) => setInputRut(e.target.value)}
                style={{ marginBottom: '20px' }} // Margen para separación
            />
            <Button variant="contained" onClick={handleSearch} style={{ marginLeft: '10px' }}>
                Ver Créditos
            </Button>

            <TableContainer component={Paper} style={{ marginTop: '20px' }}>
                <Table aria-label="credit application table">
                    <TableHead>
                        <TableRow>
                            <TableCell>ID Solicitud de Crédito</TableCell>
                            <TableCell>Nombre del Cliente</TableCell>
                            <TableCell>Fecha de Solicitud</TableCell>
                            <TableCell>Estado</TableCell>
                            <TableCell>Monto mensual del Credito</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {creditApplications.length > 0 ? (
                            creditApplications.map(application => (
                                <TableRow key={application.credit_application_id}>
                                    <TableCell>{application.credit_application_id}</TableCell>
                                    <TableCell>{application.name}</TableCell>
                                    <TableCell>{application.credit_date}</TableCell>
                                    <TableCell>{application.status}</TableCell>
                                    <TableCell>{application.amount}</TableCell>
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan={4} align="center">No hay solicitudes de crédito disponibles.</TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
};

export default CreditApplicationList;
