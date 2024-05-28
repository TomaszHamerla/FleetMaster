import { useEffect, useState } from "react";
import "./App.css";
import LoginForm from "./login/LoginForm";
import Homepage from "./homepage/Homepage";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  useEffect(() => {
    const token = sessionStorage.getItem("Token");
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);
  const handleLogin = () => {
    setIsLoggedIn(true);
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    sessionStorage.removeItem("Token");
  };

  return (
    <div className="app">
      <div className="left-background"></div>
      <div className="right-background"></div>
      <div className="center-background">
        {isLoggedIn ? (
          <>
            <div className="logout">
              <button onClick={handleLogout}>Logout</button>
            </div>
            <Homepage />
          </>
        ) : (
          <LoginForm onLogin={handleLogin} />
        )}
      </div>
    </div>
  );
}

export default App;
