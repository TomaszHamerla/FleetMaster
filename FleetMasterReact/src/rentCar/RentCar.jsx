import React from "react";
import { API_BASE_URL } from "../config";
import "./RentCar.css";

export const RentCar = ({ brandId, modelId, token, onRentSuccess }) => {
  const rentCar = async () => {
    const body = {
      userId: sessionStorage.getItem("userId"),
      brandId: brandId,
      modelId: modelId,
    };
    const json = JSON.stringify(body);
    const response = await fetch(`${API_BASE_URL}/cars/rent`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: token,
      },
      body: json,
    });
    if (response.status !== 200) {
      const message = await response.json();
      alert(message.message);
    }
    alert('Samód został wyporzyczony pomyślnie !')
    onRentSuccess();
  };

  const handleRentCar = () => {
    rentCar();
  };
  return (
    <div className="container-custom">
      <p> Czy chcesz wypożyczyć samochód? </p>
      <button onClick={handleRentCar}> Tak </button>
    </div>
  );
};
