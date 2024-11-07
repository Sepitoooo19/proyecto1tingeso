import { useState } from "react";
import { useNavigate } from "react-router-dom";
import clientService from "../services/client.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";

const AddClient = () => {
  const [rut, setRut] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [age, setAge] = useState("");
  const [monthly_salary, setMonthlySalary] = useState("");
  const [personal_savings, setPersonalSavings] = useState("");
  const [job_type, setJobType] = useState("");
  const [expected_amount, setExpectedAmount] = useState("");
  const [time_limit, setTimeLimit] = useState("");
  const [interest_rate, setInterestRate] = useState("");
  const [type_loan, setTypeLoan] = useState("");
  const [independent_activity, setIndependentActivity] = useState(false);
  const [job_seniority, setJobSeniority] = useState("");
  const [actual_job, setActualJob] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();

    
    if (
      !rut ||
      !name ||
      !email ||
      !phone ||
      !age ||
      !monthly_salary ||
      !personal_savings ||
      !job_type ||
      !expected_amount ||
      !time_limit ||
      !interest_rate ||
      !type_loan ||
      job_seniority === "" ||
      actual_job === ""
    ) {
      alert("Por favor, complete todos los campos obligatorios.");
      return;
    }

    const newClient = {
      rut,
      name,
      email,
      phone,
      age,
      monthly_salary,
      personal_savings,
      job_type,
      expected_amount,
      time_limit,
      interest_rate,
      type_loan,
      independent_activity,
      job_seniority,
      actual_job,
    };

    try {
      await clientService.create(newClient);
      // Redirigir a la lista de clientes después de crear uno nuevo
      navigate("/client/list");
    } catch (error) {
      console.error("Error adding client:", error);
      
    }
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 3 }}>
      <TextField
        label="RUT"
        value={rut}
        onChange={(e) => setRut(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <TextField
        label="Nombre"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <TextField
        label="Email"
        type="email"  // Cambié el tipo a 'email' para validación de formato
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <TextField
        label="Teléfono"
        type="tel"  // Cambié el tipo a 'tel' para validación de formato
        value={phone}
        onChange={(e) => setPhone(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <TextField
        label="Edad"
        type="number"
        value={age}
        onChange={(e) => setAge(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <TextField
        label="Salario Mensual"
        type="number"
        value={monthly_salary}
        onChange={(e) => setMonthlySalary(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <TextField
        label="Ahorros Personales"
        type="number"
        value={personal_savings}
        onChange={(e) => setPersonalSavings(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <TextField
        label="Tipo de Trabajo"
        value={job_type}
        onChange={(e) => setJobType(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <TextField
        label="Monto Esperado"
        type="number"
        value={expected_amount}
        onChange={(e) => setExpectedAmount(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <TextField
        label="Plazo"
        type="number"
        value={time_limit}
        onChange={(e) => setTimeLimit(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <TextField
        label="Tasa de Interés"
        type="number"
        value={interest_rate}
        onChange={(e) => setInterestRate(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <TextField
        label="Tipo de Préstamo"
        value={type_loan}
        onChange={(e) => setTypeLoan(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <FormControl fullWidth margin="normal">
        <InputLabel>Actividad Independiente</InputLabel>
        <Select
          value={independent_activity}
          onChange={(e) => setIndependentActivity(e.target.value)}
          required // Esto asegura que la selección sea obligatoria
        >
          <MenuItem value={true}>Sí</MenuItem>
          <MenuItem value={false}>No</MenuItem>
        </Select>
      </FormControl>
      <TextField
        label="Antigüedad Laboral"
        value={job_seniority}
        onChange={(e) => setJobSeniority(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <TextField
        label="Trabajo Actual"
        value={actual_job}
        onChange={(e) => setActualJob(e.target.value)}
        required
        fullWidth
        margin="normal"
      />
      <Button
        type="submit"
        variant="contained"
        color="primary"
        startIcon={<SaveIcon />}
      >
        Guardar Cliente
      </Button>
    </Box>
  );
};

export default AddClient;
