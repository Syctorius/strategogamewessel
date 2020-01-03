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

@Path("/user")
public class LoginService {

    public LoginService() {

    }


    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
//        User user = new User();
        boolean response = false;


        List<User> allUsers = StrategoLogin.getInstance().getAllUsers();
        for (User user1 : allUsers) {
            if (user1.getUsername().equals(user.getUsername()) && user1.getPassword().equals(user.getPassword())) {
                response = true;
            }
        }
        if (response ) return Response.status(200).entity(ResponseHelper.getSuccessResponse(true)).build();
        return Response.status(200).entity(ResponseHelper.getSuccessResponse(false)).build();



    }

    @GET
    @Path("/all")
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


        List<User> allUsers = StrategoLogin.getInstance().getAllUsers();
        for (User user1 : allUsers) {
            if (user1.getUsername().equals(user.getUsername())) {
                response = false;

            }
        }
        if (response ){
            StrategoLogin.getInstance().addUser(user.getUsername(),user.getPassword());
            return Response.status(200).entity(ResponseHelper.getSuccessResponse(true)).build();
        }
        return Response.status(200).entity(ResponseHelper.getSuccessResponse(false)).build();



    }
    @GET
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserWithUsername(@PathParam("username") String userName) {


        // Get all pets from the store with given owner name
        //User user = SeaBattleLogin.getInstance().getUser(userName);

        // Define response
        //return Response.status(200).entity(ResponseHelper.getSingleUserResponse(user)).build();
        return Response.status(200).entity(ResponseHelper.getSuccessResponse(false)).build();
    }
}
