package com.example.service.interfaces;

import com.example.model.user.User;

public interface RentCarService {
    User rentCar(int userId, int brandId, int modelId);
}
