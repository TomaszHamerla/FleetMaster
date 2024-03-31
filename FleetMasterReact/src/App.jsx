import { useState } from "react";
import "./App.css";
import LoginForm from "./Login/LoginForm";

function App() {
  return (
    <div className="app">
      <div className="left-background"></div>
      <div className="right-background"></div>
      <div className="center-background">
        <LoginForm/>
      </div>
    </div>
  );
}

export default App;
