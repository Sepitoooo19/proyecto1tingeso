import httpClient from '../http-common';

const getAll = () => {
    return httpClient.get("/api/v1/client");
}

const create = data => {
    return httpClient.post("/api/v1/client/new", data);
}

const get = id => {
    return httpClient.get(`/api/v1/client/client_id/${id}`);
}

const updateById = (id, data) => {
    return httpClient.put(`/api/v1/client/${id}`, data);
}

const deleteById = id => {
    return httpClient.delete(`/api/v1/client/${id}`);
}

export default { getAll, create, get, updateById, deleteById };