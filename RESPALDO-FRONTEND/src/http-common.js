import axios from "axios";

const prestaBancoServer = `prestabanco-app.westus2.cloudapp.azure.com`;
const prestaBancoPort = `80`;

console.log(prestaBancoServer);
console.log(prestaBancoPort);


export default axios.create({
    baseURL: `http://${prestaBancoServer}:${prestaBancoPort}`,
    headers: {
        'Content-Type': 'application/json'
    }
});