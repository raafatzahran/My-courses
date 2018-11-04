package com.rfz.app.ws.service.impl;

import com.rfz.app.ws.exceptions.CouldNotCreateRecordException;
import com.rfz.app.ws.exceptions.NoRecordFoundException;
import com.rfz.app.ws.io.dao.DAO;
import com.rfz.app.ws.io.dao.impl.MYSQLDAO;
import com.rfz.app.ws.service.UsersService;
import com.rfz.app.ws.shared.dto.UserDTO;
import com.rfz.app.ws.ui.model.response.ErrorMessages;
import com.rfz.app.ws.utils.UserProfileUtils;

public class UsersServiceImpl implements UsersService {
    DAO database;

    public UsersServiceImpl() {
        this.database = new MYSQLDAO();
    }

    UserProfileUtils userProfileUtils = new UserProfileUtils();

    public UserDTO createUser(UserDTO user) {
        UserDTO returnValue = null;

        // Validate the required fields
        userProfileUtils.validateRequiredFields(user);

        // Check if user already exists
        UserDTO existingUser = this.getUserByUserName(user.getEmail());
        if (existingUser != null) {
            throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name()  + ". Requested object= " + user.toString());
        }

        // Generate secure public user id
        String userId = userProfileUtils.generateUserId(30);
        user.setUserId(userId);

        // Generate salt
        String salt = userProfileUtils.getSalt(30);

        // Generate secure password
        String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setEncryptedPassword(encryptedPassword);

        // Record data into a database
        returnValue = this.saveUser(user);

        return returnValue;
    }

    public UserDTO getUser(String id) {
        UserDTO returnValue = null;
        try {
            this.database.openConnection();
            returnValue = this.database.getUser(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        } finally {
            this.database.closeConnection();
        }
        return returnValue;
    }

    public UserDTO getUserByUserName(String userName) {
        UserDTO userDto = null;

        if (userName == null || userName.isEmpty()) {
            return userDto;
        }

        // Connect to database
        try {
            this.database.openConnection();
            userDto = this.database.getUserByUserName(userName);
        } finally {
            this.database.closeConnection();
        }

        return userDto;
    }

    private UserDTO saveUser(UserDTO user) {
        UserDTO returnValue = null;
        // Connect to database
        try {
            this.database.openConnection();
            returnValue = this.database.saveUser(user);
        } finally {
            this.database.closeConnection();
        }

        return returnValue;
    }
}
