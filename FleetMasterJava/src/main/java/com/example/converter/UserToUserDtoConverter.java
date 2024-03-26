package com.example.converter;

import com.example.model.user.User;
import com.example.model.user.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        return new UserDto(source.getId(), source.getUsername(), source.getEmail(), source.getCarRentalBalance());
    }
}
