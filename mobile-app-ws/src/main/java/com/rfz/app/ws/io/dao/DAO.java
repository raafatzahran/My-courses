package com.rfz.app.ws.io.dao;

import com.rfz.app.ws.shared.dto.UserDTO;

import java.util.List;

public interface DAO {
    void openConnection();
    UserDTO getUserByUserName(String userName);
    UserDTO saveUser(UserDTO user);
    void closeConnection();
}
