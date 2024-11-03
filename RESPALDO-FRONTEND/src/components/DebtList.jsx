import { useEffect, useState } from 'react';
import { useParams } from "react-router-dom";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import bankExecutiveService from "../services/bank.executive.js";  ;


const DebtList = () => {
    const { rut } = useParams();
    const [debts, setDebts] = useState([]);

    useEffect(() => {
        bankExecutiveService
            .getDebtsByRut(rut)
            .then((response) => {
                setDebts(response.data);
            })
            .catch((error) => {
                console.log("Error al obtener deudas del cliente:", error);
            });
    }, [rut]);

    return (
        <TableContainer component={Paper}>
            <Table aria-label="debt table">
                <TableHead>
                    <TableRow>
                        <TableCell>ID Deuda</TableCell>
                        <TableCell>Monto</TableCell>
                        <TableCell>Tipo</TableCell>
                        <TableCell>Fecha</TableCell>
                        <TableCell>Estado</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {debts.map(debt => (
                        <TableRow key={debt.debt_id}>
                            <TableCell>{debt.debt_id}</TableCell>
                            <TableCell>{debt.debt_amount}</TableCell>
                            <TableCell>{debt.debt_type}</TableCell>
                            <TableCell>{debt.debt_date}</TableCell>
                            <TableCell>{debt.debt_status}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default DebtList;