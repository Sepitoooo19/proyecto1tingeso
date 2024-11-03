import { useEffect, useState } from "react";
import documentService from "../services/document.service";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import DownloadIcon from "@mui/icons-material/Download";
import SearchIcon from "@mui/icons-material/Search";
import { styled } from "@mui/material/styles";
import { Box, Stack } from "@mui/material";
import { tableCellClasses } from "@mui/material/TableCell";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.primary.dark,
    color: theme.palette.common.white,
    fontWeight: "bold",
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const DocumentList = () => {
    const [clientRut, setClientRut] = useState("");
    const [documents, setDocuments] = useState([]);
  
    const handleSearch = () => {
      if (clientRut) {
        documentService
          .getDocumentsByRut(clientRut)
          .then((response) => {
            setDocuments(response.data);
          })
          .catch((error) => {
            console.error("Error fetching documents:", error);
          });
      }
    };
  
    const downloadFile = (base64Data, fileName) => {
      try {
        // Verificar si base64Data es una cadena válida
        if (!base64Data || typeof base64Data !== 'string') {
          throw new Error("El archivo no es una cadena Base64 válida.");
        }
        
        const byteCharacters = atob(base64Data);
        const byteNumbers = Array.from(byteCharacters, char => char.charCodeAt(0));
        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray], { type: "application/pdf" });
        
        const link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = fileName;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      } catch (error) {
        console.error("Error al descargar el archivo:", error);
        alert("Hubo un error al intentar descargar el archivo.");
      }
    };
  
    return (
    <Box sx={{ mt: 4, mx: "auto", width: "95%" }}>
        <Stack direction="row" spacing={2} alignItems="center" justifyContent="center" mb={2}>
          <TextField
            label="Buscar por RUT"
            variant="outlined"
            value={clientRut}
            onChange={(e) => setClientRut(e.target.value)}
          />
          <Button
            variant="contained"
            color="primary"
            startIcon={<SearchIcon />}
            onClick={handleSearch}
          >
            Buscar
          </Button>
        </Stack>
  
        <TableContainer component={Paper} sx={{ mt: 2 }}>
          <Table sx={{ minWidth: 650 }} aria-label="document table">
            <TableHead>
              <TableRow>
                <StyledTableCell>Nombre del Documento</StyledTableCell>
                <StyledTableCell>Tamaño (KB)</StyledTableCell>
                <StyledTableCell>Fecha de Envío</StyledTableCell>
                <StyledTableCell align="center">Acciones</StyledTableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {documents.length > 0 ? (
                documents.map((doc) => (
                  <TableRow key={doc.document_id}>
                    <TableCell>{doc.name}</TableCell>
                    <TableCell>{(doc.size / 1024).toFixed(2)}</TableCell>
                    <TableCell>{new Date(doc.sumbit_date).toLocaleDateString()}</TableCell>
                    <TableCell align="center">
                      <Button
                        variant="contained"
                        color="primary"
                        startIcon={<DownloadIcon />}
                        onClick={() => downloadFile(doc.file, `${doc.name}`)}
                      >
                        Descargar
                      </Button>
                    </TableCell>
                  </TableRow>
                ))
              ) : (
                <TableRow>
                  <TableCell colSpan={5} align="center">
                    No se encontraron documentos para el RUT ingresado.
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
      </Box>
    );
  };
  
export default DocumentList;

