import React, { useEffect, useState } from "react";
import { API_BASE_URL } from "../config";
import "./Homepage.css";
import { BrowseCarsContent } from "../browseCars/BrowseCarsContent";
import { CheckFleetContent } from "../checkFleet/CheckFleetContent";
import { PayRentContent } from "../payRent/PayRentContent";

export const Homepage = () => {
  const [user, setUser] = useState(null);
  const [activeComponent, setActiveComponent] = useState(null);

  const userToken = "Bearer " + sessionStorage.getItem("Token");
  const userId = sessionStorage.getItem("userId");

  useEffect(() => {
    const getUser = async () => {
      const response = await fetch(`${API_BASE_URL}/users/${userId}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: userToken,
        },
      });
      const user = await response.json();
      setUser(user);
    };
    getUser();
  }, []);

  const handleCheckFleetClick = () => {
    setActiveComponent(<CheckFleetContent />);
  };

  const handleBrowseCarsClick = () => {
    setActiveComponent(<BrowseCarsContent token={userToken}/>);
  };

  const handlePayRentClick = () => {
    setActiveComponent(<PayRentContent />);
  };

  const handleReturnClick = () => {
    setActiveComponent(null);
  }

  return (
    <>
      {user && <div className="user-greeting">Cześć {user.name}</div>}
      <div className="user-info">
        {user && (
          <>
            <p>
              <strong>Email:</strong> {user.email}
            </p>
            <p>
              <strong>Saldo wypożyczeń:</strong> {user.carRentalBalance}
            </p>
            <p>
              <strong>Blokada użytkownika:</strong>{" "}
              {user.userBlocked ? "Tak" : "Nie"}
            </p>
          </>
        )}
       </div>
      {activeComponent ? (
        <div className="content-container">
        <button onClick={handleReturnClick}>Wróć</button>
        <div className="content-wrapper">
          {activeComponent}
        </div>
      </div>
      ) : (
        <div className="button-container">
          <button onClick={handleCheckFleetClick}>Sprawdź swoją flotę</button>
          <button onClick={handleBrowseCarsClick}>Przeglądaj samochody</button>
          <button onClick={handlePayRentClick}>Zapłać za wypożyczenie</button>
        </div>
      )}
    </>
  );
};
