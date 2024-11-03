import './App.css'
import Navbar from './components/Navbar'
import ClientList from './components/clientList'
import DebtList from './components/DebtList'
import AddClient from './components/AddClient'
import EditClient from './components/EditClient'
import EmploymentHistoryList from './components/EmploymentHistoryList'
import BankAccountList from './components/BankAccountList'
import CreditSimulation from './components/CreditSimulation'
import DocumentList from './components/DocumentList'
import Home from './components/Home'
import CreditApplication from './components/CreditApplication'
import CreditApplicationList from './components/CreditApplicationsList'
import EvaluateCredApp from './components/EvaluateCreditApplication'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'

function App() {
  return (
    <Router>
      <div className ="container">
        <Navbar></Navbar>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/client/list" element={<ClientList />} />
          <Route path="/debts/:rut" element={<DebtList />} />
          <Route path="/client/add" element={<AddClient />} />
          <Route path="/client/edit/:clientId" element={<EditClient />} />
          <Route path="/employment/:rut" element={<EmploymentHistoryList />} />
          <Route path="/bankAccount/:rut" element={<BankAccountList />} />
          <Route path="/creditSimulation" element={<CreditSimulation />} />
          <Route path="/document/" element={<DocumentList />} />
          <Route path="/creditApplication" element={<CreditApplication />} />
          <Route path="/credit-applications" element={<CreditApplicationList />} />
          <Route path="/credit-applications/:rut" element={<CreditApplicationList />} />
          <Route path="/evaluate-cred-app" element={<EvaluateCredApp />} />

          
        </Routes>
      </div>
    </Router>
  );
}


export default App;