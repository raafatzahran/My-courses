package com.rfz.app.ws.service;

import com.rfz.app.ws.shared.dto.UserDTO;

public interface UsersService {
    UserDTO createUser(UserDTO user);
    UserDTO getUser(String id);
}
