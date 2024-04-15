import React, { useEffect, useState } from "react";
import { API_BASE_URL } from "../config";
import "./BrowseCarsContent.css"
import { Models } from "./Models";

export const BrowseCarsContent = ({ token }) => {
  const [brands, setBrands] = useState([]);
  const [brandId, setBrandId]=useState(false);

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

  const handleBrandClick = (brandId) => {
    setBrandId(brandId);
  };

  return (
    <div className="brands-list-container">
     {!brandId ? <ul className="brands-list">
        {brands.map((brand) => (
          <li key={brand.id} className="brand-item" onClick={()=>handleBrandClick(brand.id)}>
            {brand.name}
          </li>
        ))}
      </ul>:<Models brandId={brandId} token={token}/>}
    </div>
  );
};
