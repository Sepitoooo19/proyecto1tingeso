import httpClient from '../http-common';

const getAll = () => {
    return httpClient.get("http://prestabanco-app.westus2.cloudapp.azure.com/api/v1/client");
}

const create = data => {
    return httpClient.post("http://prestabanco-app.westus2.cloudapp.azure.com/api/v1/client/new", data);
}

const get = id => {
    return httpClient.get(`http://prestabanco-app.westus2.cloudapp.azure.com/api/v1/client/client_id/${id}`);
}

const updateById = (id, data) => {
    return httpClient.put(`http://prestabanco-app.westus2.cloudapp.azure.com/api/v1/client/${id}`, data);
}

const deleteById = id => {
    return httpClient.delete(`http://prestabanco-app.westus2.cloudapp.azure.com/api/v1/client/${id}`);
}

export default { getAll, create, get, updateById, deleteById };