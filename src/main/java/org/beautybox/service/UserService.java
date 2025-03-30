package org.beautybox.service;

import org.beautybox.request.UserRegisterRequest;

public interface UserService {
    boolean register(UserRegisterRequest registerRequest);
}
