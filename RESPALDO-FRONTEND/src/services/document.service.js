import httpClient from '../http-common';

const getDocumentsByRut = (rut) => {
    return httpClient.get(`api/v1/document/client/${rut}/documents`);
}

const uploadDocument = (file, name, type, rut) => {
    // Crea un FormData para manejar el archivo y otros datos de carga
    const formData = new FormData();
    formData.append("file", file); // archivo PDF
    formData.append("name", name);
    formData.append("type", type);
    formData.append("rut", rut);

    // Realiza la solicitud POST para subir el documento
    return httpClient.post(`api/v1/document/upload`, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
};

const downloadDocumentById = (rut, documentId) => {
    return httpClient.get(`api/v1/document/client/${rut}/document/${documentId}/download`, {
        responseType: 'arraybuffer',
    });
};

export default { 
    getDocumentsByRut, 
    uploadDocument,
    downloadDocumentById
};