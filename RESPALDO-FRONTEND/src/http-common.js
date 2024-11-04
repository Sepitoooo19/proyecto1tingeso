import axios from "axios";

const prestaBancoServer = import.meta.env.VITE_PRESTA_BANCO_BACKEND_SERVER;
const prestaBancoPort = import.meta.env.VITE_PRESTA_BANCO_BACKEND_PORT;

console.log(prestaBancoServer);
console.log(prestaBancoPort);


export default axios.create({
    baseURL: `http://${prestaBancoServer}:${prestaBancoPort}/`,
    headers: {
        'Content-Type': 'application/json'
    }
});