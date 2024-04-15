import React, { useEffect, useState } from "react";
import { API_BASE_URL } from "../config";

export const Models = ({ brandId, token }) => {
  const [models, setModels] = useState([]);

  useEffect(() => {
    const getModels = async () => {
      const response = await fetch(
        `${API_BASE_URL}/cars/brands/models/${brandId}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: token,
          },
        }
      );
      const modelResposne = await response.json();
      setModels(modelResposne);
    };
    getModels();
  }, []);
  return (
    <div className="brands-list-container">
      <ul className="brands-list">
        {models.map((model) => (
          <li key={model.id} className="brand-item">
            {model.name}
          </li>
        ))}
      </ul>
    </div>
  );
};
