package org.beautybox.mapper;

import org.beautybox.entity.User;
import org.beautybox.repository.RoleRepo;
import org.beautybox.request.UserRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected RoleRepo roleRepo;

    @Mapping(target = "role", expression = "java(roleRepo.findByName(\"ROLE_USER\"))")
    public abstract User fromRegisterRequest(UserRegisterRequest registerRequest);
}
