import React, { useState } from "react";
import { API_BASE_URL } from "../config";
import './PayRentContent.css';

export const PayRentContent = () => {
  const [amount, setAmount] = useState('');
  const token = "Bearer " + sessionStorage.getItem("Token");
  const userId = sessionStorage.getItem("userId");

  const handleChange = (e) => {
    setAmount(e.target.value);
  };

  const pay = async () => {
    const response = await fetch(`${API_BASE_URL}/users/${userId}/payment`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: token,
      },
      body: JSON.stringify(parseFloat(amount)),
    });
    if (response.status !== 200) {
      const message = await response.json();
      alert(message.message);
    } else {
      alert("Środki zostały przelane");
      window.location.reload();
    }
  };

  return (
    <div>
    <p className="pay-label">Wprowadź kwotę do zapłaty:</p>
    <input type="text" value={amount} onChange={handleChange} className="pay-input" />
    <button onClick={pay} className="pay-button">Zapłać</button>
  </div>
  );
};
