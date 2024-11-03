import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import clientService from "../services/client.service";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const ClientList = () => {
    const [clients, setClients] = useState([]);
    const navigate = useNavigate();
    
    const init = () => {
        clientService
          .getAll()
          .then((response) => {
            console.log("Mostrando listado de todos los clientes.", response.data);
            setClients(response.data);
          })
          .catch((error) => {
            console.log(
              "Se ha producido un error al intentar mostrar listado de todos los empleados.",
              error
            );
          });
      };

    useEffect(() => {
        init();
    }, []);

    const handleEdit = (id) => {
      navigate(`/client/edit/${id}`); 
    };
  
    const handleDelete = (id) => {
      
      const confirmed = window.confirm("¿Estás seguro de que deseas eliminar este cliente?");
      if (confirmed) {
          clientService
              .deleteById(id) 
              .then((response) => {
                  console.log("Cliente eliminado con éxito.", response.data);
                  init(); 
              })
              .catch((error) => {
                  console.log("Error al intentar eliminar el cliente.", error);
              });
      } else {
          console.log("Eliminación cancelada.");
      }
    };
      const handleDebtList = (rut) => {
        navigate(`/debts/${rut}`);
    };

    const handleEmploymentList = (rut) => {
        navigate(`/employment/${rut}`);
    }

    const handleBankAccountList = (rut) => {
        navigate(`/bankAccount/${rut}`);
    }
      return (
        <TableContainer component={Paper}>
          <br />
          <Link
            to="/client/add"
            style={{ textDecoration: "none", marginBottom: "1rem" }}
          >
            <Button
              variant="contained"
              color="primary"
              startIcon={<PersonAddIcon />}
            >
              Añadir Cliente
            </Button>
          </Link>
          <br /> <br />
          <Table sx={{ minWidth: 1000 }} size="large" aria-label="a dense table">
            <TableHead>
              <TableRow>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Rut
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Nombre
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Correo Electrónico
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Teléfono
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Edad
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Salario Mensual
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Tipo de Trabajo
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Monto Esperado
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Tiempo Limite (en años)
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Porcentaje de Interés (%)
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Ahorro personal
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Tipo de Prestamo
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  ¿Trabajador Independiente?
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                    Empleo Actual
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                    Antigüedad Laboral en su Empleo Actual (en años)
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                    Lista de Deudas
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                    Historial de Empleos
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                    Cuenta Bancaria
                </TableCell>

              </TableRow>
            </TableHead>
            <TableBody>
              {clients.map((client) => (
                <TableRow
                  key={client.id}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell align="left">{client.rut}</TableCell>
                  <TableCell align="left">{client.name}</TableCell>
                  <TableCell align="right">{client.email}</TableCell>
                  <TableCell align="right">{client.phone}</TableCell>
                  <TableCell align="right">{client.age}</TableCell>
                  <TableCell align="left">{client.monthly_salary}</TableCell>
                  <TableCell align="left">{client.job_type}</TableCell>
                  <TableCell align="left">{client.expected_amount}</TableCell>
                  <TableCell align="left">{client.time_limit}</TableCell>
                  <TableCell align="left">{client.interest_rate}</TableCell>
                  <TableCell align="left">{client.personal_savings}</TableCell>
                  <TableCell align="left">{client.type_loan}</TableCell>
                  <TableCell align="left">{client.independent_activity ? "Sí" : "No"}</TableCell>
                  <TableCell align="left">{client.actual_job}</TableCell>
                  <TableCell align="left">{client.job_seniority}</TableCell>
                  <TableCell>
                    <Button
                      variant="contained"
                      color="info"
                      size="small"
                      onClick={() => handleDebtList(client.rut)} // Llama a la función al hacer clic
                      style={{ marginLeft: "0.5rem" }}
                    >
                      Lista de Deudas
                    </Button>
                    </TableCell>
                    <TableCell>
                    <Button
                      variant="contained"
                      color="info"
                      size="small"
                      onClick={() => handleEmploymentList(client.rut)} // Llama a la función al hacer clic
                      style={{ marginLeft: "0.5rem" }}
                    >
                      Historial de Empleos
                    </Button>
                    </TableCell>
                    <TableCell>
                    <Button
                      variant="contained"
                      color="info"
                      size="small"
                      onClick={() => handleBankAccountList(client.rut)} // Llama a la función al hacer clic
                      style={{ marginLeft: "0.5rem" }}
                    >
                      Cuenta Bancaria
                    </Button>
                    </TableCell>
                  <TableCell>
                  <Button
                      variant="contained"
                      color="info"
                      size="small"
                      onClick={() => handleEdit(client.client_id)} // Cambiado a client.rut
                      style={{ marginLeft: "0.5rem" }}
                      startIcon={<EditIcon />}
                  >
                      Editar
                  </Button>
                  <Button
                      variant="contained"
                      color="error"
                      size="small"
                      onClick={() => handleDelete(client.client_id)} // Cambiado a client.rut
                      style={{ marginLeft: "0.5rem" }}
                      startIcon={<DeleteIcon />}
                  >
                      Eliminar
                  </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      );
}

export default ClientList;