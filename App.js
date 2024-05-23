import Register from "./pages/Registration";
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from "./pages/Login";
import VerifyOtp from "./pages/VerifyOtp";
import Welcome from "./pages/Welcome";
// import Nav from "./pages/Nav";
import WelcomePage from "./pages/WelcomePage";
function App() {
  return (
    <div className="App">
    <Router>
      <Routes>
        <Route path="/" element={<Register />} />
        {/* Add more routes as needed */}
        <Route path="/login" element={<Login />} />
        <Route path="/verify" element={<VerifyOtp />} />
        <Route path="/welcome" element={<Welcome />} />
        {/* <Route path="/nav" element={<Nav />} /> */}
        <Route path="/welcomepage" element={<WelcomePage />} />
      </Routes>
    </Router>
  </div>
  );
}

export default App;