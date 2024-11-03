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
import { TableFooter } from '@mui/material';


const BankAccountList = () => {
    const { rut } = useParams();
    const [bankAccount, setBankAccount] = useState([]);
    const [totalDeposit, setTotalDeposit] = useState(0);
    const [totalWithdrawal, setTotalWithdrawal] = useState(0);

    useEffect(() => {
        bankExecutiveService
            .getBankAccountByRut(rut)
            .then((response) => {
                setBankAccount(response.data);
            })
            .catch((error) => {
                console.log("Error al obtener la cuenta bancaria del cliente:", error);
            });
            bankExecutiveService
            .getDepositInBankAccountByRut(rut)
            .then((response) => {
                 
                setTotalDeposit(response.data);
            })
            .catch((error) => {
                console.log("Error al obtener los depósitos del cliente:", error);
            });
            bankExecutiveService
            .getWithdrawalInBankAccountByRut(rut)
            .then((response) => {
            
                setTotalWithdrawal(response.data);
            })
            .catch((error) => {
                console.log("Error al obtener los retiros del cliente:", error);
            });

    }, [rut]);

    return (
        <TableContainer component={Paper}>
            <Table aria-label="bankAccount table">
                <TableHead>
                    <TableRow>
                        <TableCell>ID Transacción</TableCell>
                        <TableCell>Nombre del Banco</TableCell>
                        <TableCell>Numero de Cuenta</TableCell>
                        <TableCell>Monto</TableCell>
                        <TableCell>Tipo de Transacción</TableCell>
                        <TableCell>Fecha de Transacción</TableCell>
                        <TableCell>Fecha de Apertura de la cuenta</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {bankAccount.map(bankAccount => (
                        <TableRow key={bankAccount.client_bank_account_id}>
                            <TableCell>{bankAccount.client_bank_account_id}</TableCell>
                            <TableCell>{bankAccount.bank_name}</TableCell>
                            <TableCell>{bankAccount.account_number}</TableCell>
                            <TableCell>{bankAccount.amount}</TableCell>
                            <TableCell>{bankAccount.transaction}</TableCell>
                            <TableCell>{bankAccount.transaction_date}</TableCell>
                            <TableCell>{bankAccount.account_opening}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
                <TableFooter>
                    <TableRow>
                        <TableCell>Total en la Cuenta Bancaria (Incluyendo Retiros): {totalDeposit}</TableCell>
                        <TableCell>Total Retirado de la Cuenta: {totalWithdrawal} </TableCell>
                        
                    </TableRow>
                    
                </TableFooter>
            </Table>
            
        </TableContainer>
    );
};

export default BankAccountList;