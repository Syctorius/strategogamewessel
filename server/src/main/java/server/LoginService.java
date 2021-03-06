package server;

import dtos.LoginDTO;
import user.User;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

@Path("/users")
public class LoginService {

    public LoginService() {

    }
    public LoginService(boolean test) {
        if(test == true)
        {
            StrategoLogin.getInstance().useMockData();
        }

    }


    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
//        User user = new User();
        boolean response = false;

        if(isNotEmpty(user)) {
            List<User> allUsers = StrategoLogin.getInstance().getAllUsers();
            for (User user1 : allUsers) {
                if (user1.getUsername().equals(user.getUsername()) && user1.getPassword().equals(user.getPassword())) {
                    response = true;
                }
            }
        }
        if (response ) return Response.status(200).entity(ResponseHelper.getSuccessResponse(true)).build();
        return Response.status(400).entity(ResponseHelper.getSuccessResponse(false)).build();



    }
    @Path("") //remove
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {

        // Get all users
        List<User> allUsers = StrategoLogin.getInstance().getAllUsers();

        // Define response
        return Response.status(200).entity(ResponseHelper.getAllUsersResponse(ResponseHelper.getUserDTOList(allUsers))).build();
    }
    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(User user) {
//        User user = new User();
        boolean response = true;

        if(isNotEmpty(user)) {
            List<User> allUsers = StrategoLogin.getInstance().getAllUsers();
            for (User user1 : allUsers) {
                if (user1.getUsername().equals(user.getUsername())) {
                    response = false;

                }
            }
        }
        else {
            response = false;
        }
        if (response ){
            StrategoLogin.getInstance().addUser(user.getUsername(),user.getPassword());
            return Response.status(200).entity(ResponseHelper.getSuccessResponse(true)).build();
        }
        return Response.status(400).entity(ResponseHelper.getSuccessResponse(false)).build();



    }

    private boolean isNotEmpty(User user) {
        return user.getUsername()!= "" && user.getPassword() != "" && user.getPassword() != null && user.getUsername() != null;
    }

    @GET
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserWithUsername(@PathParam("username") String userName) {


        User user = StrategoLogin.getInstance().getUser(userName);

        // Define response
        if(user == new User()) {

            return Response.status(400).entity(ResponseHelper.getSuccessResponse(false)).build();
        }
        else {
            return Response.status(200).entity(ResponseHelper.getSingleUserResponse(user)).build();
        }
    }
}
