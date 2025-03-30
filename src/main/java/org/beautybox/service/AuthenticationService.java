package org.beautybox.service;

import org.beautybox.response.ApiResponse;

public interface AuthenticationService {
    ApiResponse login(String username, String password);
    ApiResponse logout();
    String encryptPassword(String password);
}
