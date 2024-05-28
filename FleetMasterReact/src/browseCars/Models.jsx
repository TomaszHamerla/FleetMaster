import React, { useEffect, useState } from "react";
import { API_BASE_URL } from "../config";
import { RentCar } from "../rentCar/RentCar";

export const Models = ({ brandId, token }) => {
  const [models, setModels] = useState([]);
  const [modelId, setModelId] = useState(false);

  const handleRentSuccess = () => {
    setModelId(null);
  };

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

  const handleModelClick = (modelId) => {
    setModelId(modelId);
  };

  return (
    <div className="brands-list-container">
      {!modelId ? (
        <ul className="brands-list">
          {models.map((model) => (
            <li
              key={model.id}
              className="brand-item"
              onClick={() => handleModelClick(model.id)}
            >
              {model.name}
            </li>
          ))}
        </ul>
      ) : (
        <RentCar brandId={brandId} modelId={modelId} token={token} onRentSuccess={handleRentSuccess}/>
      )}
    </div>
  );
};
