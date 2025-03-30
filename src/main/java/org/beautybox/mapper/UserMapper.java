package org.beautybox.mapper;

import org.beautybox.entity.User;
import org.beautybox.request.UserRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract User fromRegisterRequest(UserRegisterRequest registerRequest);
}
