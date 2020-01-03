package server;

import com.google.gson.Gson;

import dtos.LoginDTO;
import user.User;


import java.util.ArrayList;
import java.util.List;


public class ResponseHelper {

    private static final Gson gson = new Gson();


    public static String getErrorResponseString()
    {
        LoginResponse response = new LoginResponse();
        response.setSuccess(false);
        String output = gson.toJson(response);
        return output;
    }



    public static String getSingleUserResponse(User userFromSBA)
    {
        LoginResponse response = new LoginResponse();
        response.setSuccess(true);
        List<LoginDTO> users = new ArrayList<>();
        LoginDTO user = userFromSBA.createDTO();
        users.add(user);
        response.setUsers(users);
        String output = gson.toJson(response);
        return output;
    }

    public static String getSuccessResponse(boolean success)
    {
        LoginResponse response = new LoginResponse();
        response.setSuccess(success);
        String output = gson.toJson(response);
        return output;
    }

    public static String getAllUsersResponse(List<LoginDTO> allUsers)
    {
        LoginResponse response = new LoginResponse();
        response.setSuccess(true);
        response.setUsers(allUsers);
        String output = gson.toJson(response);
        return output;
    }

    public static List<LoginDTO> getUserDTOList(List<User> allUsers)
    {
        List<LoginDTO> allUser = new ArrayList<>();
        for (User u : allUsers) {
            LoginDTO user = u.createDTO();
            allUser.add(user);
        }
        return allUser;
    }





}
