package com.rfz.app.ws.ui.entrypoint;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.rfz.app.ws.ui.model.request.CreateUserRequestModel;
import com.rfz.app.ws.ui.model.response.UserProfileRest;
import com.rfz.app.ws.service.UsersService;
import com.rfz.app.ws.service.impl.UsersServiceImpl;
import com.rfz.app.ws.shared.dto.UserDTO;
import org.springframework.beans.BeanUtils;

@Path("/users")
public class UsersEntryPoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON,  MediaType.APPLICATION_XML} )
    public UserProfileRest createUser(CreateUserRequestModel requestObject) {
        UserProfileRest returnValue = new UserProfileRest();

        // Prepare UserDTO
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(requestObject, userDto);

        // Create new user
        UsersService userService = new UsersServiceImpl();
        UserDTO createdUserProfile = userService.createUser(userDto);

        //Prepare response
        BeanUtils.copyProperties(createdUserProfile, returnValue);

        return returnValue;
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON,  MediaType.APPLICATION_XML} )
    public UserProfileRest getUserProfile(@PathParam("id") String id)
    {
        UserProfileRest returnValue = null;

        UsersService userService = new UsersServiceImpl();
        UserDTO userProfile = userService.getUser(id);

        returnValue = new UserProfileRest();
        BeanUtils.copyProperties(userProfile, returnValue);

        return returnValue;
    }

}
