import { useEffect, useState } from 'react';
import { useParams } from "react-router-dom";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import bankExecutiveService from "../services/bank.executive.js";

const EmploymentHistoryList = () => {
    const { rut } = useParams();
    const [employments, setEmployments] = useState([]);

    useEffect(() => {
        bankExecutiveService
            .getEmploymentHistoryByRut(rut)
            .then((response) => {
                setEmployments(response.data);
            })
            .catch((error) => {
                console.log("Error al obtener el historial de empleos del cliente:", error);
            });
    }, [rut]);

    return (
        <TableContainer component={Paper}>
            <Table aria-label="employment table">
                <TableHead>
                    <TableRow>
                        <TableCell>ID Empleo</TableCell>
                        <TableCell>Puesto</TableCell>
                        <TableCell>Nombre de la Compañía</TableCell>
                        <TableCell>Sueldo</TableCell>
                        <TableCell>Tiempo en el Empleo (en años)</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {employments.map(employment => (
                        <TableRow key={employment.employment_id}>
                            <TableCell>{employment.employment_id}</TableCell>
                            <TableCell>{employment.job_title}</TableCell>
                            <TableCell>{employment.company_name}</TableCell>
                            <TableCell>{employment.salary}</TableCell>
                            <TableCell>{employment.time_of_employment}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default EmploymentHistoryList;
