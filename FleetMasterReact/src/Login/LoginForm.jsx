import React, { useState } from "react";
import "./LoginForm.css";
import { API_BASE_URL } from "../config";

const LoginForm = ({ onLogin }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [action, setAction] = useState("Login");

  const handleAction = (act) => {
    if (act !== action) {
      setAction(act);
      setUsername("");
      setEmail("");
      setPassword("");
    }
  };

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const loginUser = async (username, password) => {
    const encodedCredentials = btoa(`${username}:${password}`);
    const basicAuthHeader = `Basic ${encodedCredentials}`;
    const response = await fetch(`${API_BASE_URL}/users/login`, {
      method: "POST",
      headers: {
        Authorization: basicAuthHeader,
      },
    });
    const data = await response.json();
    if (!response.ok) {
      alert(`${data.message}`)
      throw new Error("Login failed");
    }
    const token = data.token;
    sessionStorage.setItem("Token", token);

    const parts = token.split(".");
    const payload = parts[1];
    const userId = JSON.parse(window.atob(payload)).userId;
    sessionStorage.setItem("userId", userId);
    onLogin();
  };

  const registerUser = async (username, email, password) => {
    const user = { username: username, email: email, password: password };
    const json = JSON.stringify(user);
    const response = await fetch(`${API_BASE_URL}/users`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: json,
    });

    if (response.status !== 201) {
      throw new Error("Failed to register user.");
    }
    alert(`User ${username}, created successful !`);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (action === "Login") {
      loginUser(username, password);
    } else if (action === "Sign Up") {
      registerUser(username, email, password);
    }
  };

  return (
    <div className="login-form-container">
      <form onSubmit={handleSubmit} className="login-form">
        <h2>{action}</h2>
        <div className="form-group">
          <label htmlFor="username" className="form-label">
            Username:
          </label>
          <input
            placeholder="Username"
            type="text"
            id="username"
            value={username}
            onChange={handleUsernameChange}
            className="form-input"
            required
          />
        </div>
        {action === "Sign Up" ? (
          <div className="form-group">
            <label htmlFor="username" className="form-label">
              Email:
            </label>
            <input
              placeholder="Email"
              type="email"
              id="username"
              value={email}
              onChange={handleEmailChange}
              className="form-input"
              required
            />
          </div>
        ) : (
          <></>
        )}
        <div className="form-group">
          <label htmlFor="password" className="form-label">
            Password:
          </label>
          <input
            placeholder="Password"
            type="password"
            id="password"
            value={password}
            onChange={handlePasswordChange}
            className="form-input"
            required
          />
        </div>
        <div className="buttons">
          <button
            type="submit"
            className={action === "Login" ? "form-button-gray" : "form-button"}
            onClick={() => handleAction("Login")}
          >
            Login
          </button>
          <button
            type="submit"
            className={
              action === "Sign Up" ? "form-button-gray" : "form-button"
            }
            onClick={() => handleAction("Sign Up")}
          >
            Sign up
          </button>
        </div>
      </form>
    </div>
  );
};

export default LoginForm;
