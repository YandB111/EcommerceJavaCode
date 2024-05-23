import axios from "axios";
import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import "../components/registration.css";

function Register() {
  const navigate = useNavigate(); // Get the navigate function

  const [dob, setDob] = useState("");
  const [name, setName] = useState("");
  const [password, setPass] = useState("");
  const [role, setRole] = useState("");
  const [email, setEmail] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();
    try {
      await axios.post("http://192.168.102.131:8282/api/yB/ecommerce/signup", {
        dob: dob,
        name: name,
        password: password,
        role: role,
        email: email,
      });

      localStorage.setItem('userEmail', email);

      alert("OTP has been sent to your Registered Email");
      setDob("");
      setName("");
      setPass("");
      setRole("");
      setEmail("");

      // Redirect to the OTP verification page after successful registration
      navigate('/verify');
    } catch (err) {
      // Display error message if registration fails
      setError("User Registration Failed");
    }
  }

  return (
    <body className="reg">
      <div className="register-container">
        
        <h1 className="text-register">Registration</h1>
        <p className="text-register">For Ecommerce</p>
        
        <br />
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <form className="register-form" onSubmit={handleSubmit}>
          <div className="well">
            <div className="form-group">
              <br /><br /><br />
              <input className="input2"
                type="text"
                name="name"
                placeholder="Name"
                value={name}
                onChange={(event) => {
                  setName(event.target.value);
                }}
              />
              <input className="input2"
                type="password"
                name="password"
                placeholder="Password"
                value={password}
                onChange={(event) => {
                  setPass(event.target.value);
                }}
              />
              <input className="input2 dob-input" type="date" id="exampleInputDOB1"
                placeholder="Date of Birth"
                value={dob}
                onChange={(event) => {
                  setDob(event.target.value);
                }}
              />
              <input className="input2"
                type="email"
                name="email"
                placeholder="Email"
                value={email}
                onChange={(event) => {
                  setEmail(event.target.value);
                }}
              />
               
            </div>
            <button type="submit">Submit</button>
        
          </div>
        </form>
      </div>
    </body>
  );
}

export default Register;
