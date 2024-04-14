import React, { useEffect, useState } from "react";
import { API_BASE_URL } from "../config";
import "./BrowseCarsContent.css"

export const BrowseCarsContent = ({ token }) => {
  const [brands, setBrands] = useState([]);

  useEffect(() => {
    const getBrands = async () => {
      const response = await fetch(`${API_BASE_URL}/cars/brands`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: token,
        },
      });
      const brandsResponse = await response.json();
      setBrands(brandsResponse);
    };
    getBrands();
  }, []);
  return (
    <div className="brands-list-container">
      <ul className="brands-list">
        {brands.map((brand) => (
          <li key={brand.id} className="brand-item">
            {brand.name}
          </li>
        ))}
      </ul>
    </div>
  );
};
