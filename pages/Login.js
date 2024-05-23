import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import "../components/login.css";

const Login = () => {
  const navigate = useNavigate(); 
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [data, setData] = useState('');
  
  const handleLogin = async (e) => {
    e.preventDefault();
  
    try {
      const response = await axios.post("http://192.168.102.131:8282/api/yB/ecommerce/login", {
        email,
        password,
      });
  
      // Assuming the API response has a 'data' field and 'success' field
      const { data, success } = response.data;
  
      if (success) {
        // Save the token in local storage
        localStorage.setItem('token', data);
        console.log("Token:"+data);
  
        // Navigate to the WelcomePage
        navigate('/welcomepage');
      } else {
        // Display error message as an alert
        alert(data);
      }
    } catch (error) {
      // Handle other errors
      console.error('Login failed:', error);
      setError('An error occurred during login.');
    }
  };
  
  return (
    <body className='login'>
      <h2>Login</h2>
      <div className="register-container2">
        <form className="register-form2" onSubmit={handleLogin}>
          <center> 
            <input className="input1" type="text" id="emailInput" value={email} onChange={(e) => setEmail(e.target.value)} /> 
          </center>
          <br />
          <center> 
            <input className="input1" type="password" id="passwordInput" value={password} onChange={(e) => setPassword(e.target.value)} />
          </center>
          <br />
          <button type="submit">Login</button>
          {error && <p style={{ color: 'red' }}>{error}</p>}
        </form>
      </div>
    </body>
  );
};

export default Login;
