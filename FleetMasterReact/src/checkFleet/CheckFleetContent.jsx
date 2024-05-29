import React, { useEffect, useState } from 'react'
import { API_BASE_URL } from "../config";
import { ReturnCar } from '../returnCar/ReturnCar';
import "./CheckFleetContent.css"
export const CheckFleetContent = ({token,userId}) => {
  const[cars,setCars]=useState([]);

  useEffect(()=>{
    const getCars = async()=>{
      const response = await fetch(`${API_BASE_URL}/users/${userId}/cars`,{
        method:"GET",
        headers:{
          "Content-Type": "application/json",
          Authorization: token
        }
      });
      const cars = await response.json();
      setCars(cars);
    }
    getCars();
  },[])
  return (
    <div className="cars-list-container">
      <ul>
        {cars.map((car)=>(          
          <li key={car.id}><span>{car.brand} {car.model} Production year: {car.productionYear} Rent date: {car.rentDate}
          </span>
          <ReturnCar carId={car.id}/>
          </li>
        ))}
      </ul>
    </div>
  )
}
