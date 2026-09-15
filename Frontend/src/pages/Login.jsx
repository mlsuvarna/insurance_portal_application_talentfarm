import { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { useEffect } from "react";
import "../styles/Login.css"; 

function Login() {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();
  useEffect(() => {

  const token =
    localStorage.getItem("token");

  if (token) {

    navigate("/dashboard", {replace: true});
  }

}, []);

  const handleLogin = async () => {

  try {

    const response =
      await api.post(
        "/api/auth/login",
        {
          email,
          password
        }
      );

    localStorage.setItem(
  "token",
  response.data.token
);

localStorage.setItem(
  "role",
  response.data.role
);

localStorage.setItem(
  "userId",
  response.data.userId
);

navigate("/dashboard");

  } catch (error) {

    console.error(error);

    alert("Login Failed");
  }
};
return (
  <div className="login-page">
    <div className="login-card">
      <h1>Insurance Portal</h1>

      <p className="login-subtitle">
        Sign in to access your account
      </p>

      <div className="login-field">
        <label htmlFor="email">
          Email
        </label>

        <input
          id="email"
          type="email"
          value={email}
          onChange={(event) =>
            setEmail(event.target.value)
          }
          placeholder="Enter your email"
        />
      </div>

      <div className="login-field">
        <label htmlFor="password">
          Password
        </label>

        <input
          id="password"
          type="password"
          value={password}
          onChange={(event) =>
            setPassword(event.target.value)
          }
          placeholder="Enter your password"
        />
      </div>

      <button
        className="login-button"
        type="button"
        onClick={handleLogin}
      >
        Login
      </button>

      <p className="login-register-link">
        New user?{" "}

        <Link to="/register">
          Register here
        </Link>
      </p>
    </div>
  </div>
);
}

export default Login;