import React, { useEffect, useState } from 'react'
import { API_BASE_URL } from "../config";
import "./Homepage.css"


export const Homepage = () => {

  const[user,setUser]=useState(null);

 const userToken='Bearer '+ sessionStorage.getItem('Token');
 const userId=sessionStorage.getItem('userId');
 
 useEffect(()=>{
  const getUser = async()=>{
    const response = await fetch(`${API_BASE_URL}/users/${userId}`,{
      method:"GET",
      headers:{
        "Content-Type": "application/json",
        "Authorization": userToken
      }
    })
    const user = await response.json();
    setUser(user);
  }
  getUser();
 },[]);

 return (
  <div>
    {user && (
      <div className="user-greeting">Cześć {user.name}</div>
    )}
    <div className="user-info">
      {user && (
        <>
          <p><strong>Email:</strong> {user.email}</p>
          <p><strong>Saldo wypożyczeń:</strong> {user.carRentalBalance}</p>
          <p><strong>Blokada użytkownika:</strong> {user.userBlocked ? 'Tak' : 'Nie'}</p>
        </>
      )}
    </div>
    <div className="button-container">
      <button>Sprawdź swoją flotę</button>
      <button>Przeglądaj samochody</button>
      <button>Zapłać za wypożyczenie</button>
    </div>
  </div>
);
}
