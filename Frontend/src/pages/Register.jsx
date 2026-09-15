import { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import "../styles/Register.css";

function Register() {

  const navigate = useNavigate();
  const [firstName, setFirstName] =
    useState("");

  const [lastName, setLastName] =
    useState("");

  const [email, setEmail] =
    useState("");

  const [password, setPassword] =
    useState("");

  const [phoneNumber, setPhoneNumber] =
    useState("");

  const [message, setMessage] = useState("");

  const handleRegister = async () => {

  try {

    const response =
      await api.post(
        "/api/auth/register",
        {
          firstName,
          lastName,
          email,
          password,
          phoneNumber
        }
      );

    alert("Registration Successful");

    navigate("/login");

  } catch (error) {

    console.error(error);

    alert("Registration Failed");

    setMessage(
  error.response?.data?.message ||
  "Registration Failed"
);
  }
};


return (
  <div className="register-page">
    <div className="register-card">

      <h1>Create Account</h1>

      <p className="register-subtitle">
        Register for the Insurance Portal
      </p>

      {message && (
        <div className="register-message">
          {message}
        </div>
      )}

      <div className="register-field">
        <label>First Name</label>

        <input
          type="text"
          value={firstName}
          onChange={(e) =>
            setFirstName(e.target.value)
          }
        />
      </div>

      <div className="register-field">
        <label>Last Name</label>

        <input
          type="text"
          value={lastName}
          onChange={(e) =>
            setLastName(e.target.value)
          }
        />
      </div>

      <div className="register-field">
        <label>Email</label>

        <input
          type="email"
          value={email}
          onChange={(e) =>
            setEmail(e.target.value)
          }
        />
      </div>

      <div className="register-field">
        <label>Password</label>

        <input
          type="password"
          value={password}
          onChange={(e) =>
            setPassword(e.target.value)
          }
        />
      </div>

      <div className="register-field">
        <label>Phone Number</label>

        <input
          type="text"
          value={phoneNumber}
          onChange={(e) =>
            setPhoneNumber(e.target.value)
          }
        />
      </div>

      <button
        className="register-button"
        onClick={handleRegister}
      >
        Register
      </button>

      <p className="register-login-link">
        Already registered?{" "}
        <Link to="/login">
          Login here
        </Link>
      </p>

    </div>
  </div>
);

}

export default Register;