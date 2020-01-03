package restclient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.reflect.TypeToken;

import com.google.gson.reflect.TypeToken;
import dtos.LoginDTO;
import interfaces.IUserLogic;
import user.User;


public class RestClient implements IUserLogic {



    private final Gson gson = new Gson();

    private final int NOTDEFINED = -1;

    public RestClient() {
        // Nothing
        //DONT REMOVE
    }

    public  boolean login(String username, String password){
        return PostQuery(username,password, "http://localhost:2222/rest/user/login");
    }
    public boolean register(String username, String password){
        return PostQuery(username,password,"http://localhost:2222/rest/user/register");
    }

    private static boolean PostQuery(String username,String password, String address) {
        boolean correct = false;
        try {
            URL url = new URL(address);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            Gson gson = new Gson();
            String json = gson.toJson(new User(username,password));
            dataOutputStream.writeBytes(json);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("true")) {
                    correct = true;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return correct;
    }
    public static List<LoginDTO> getUsers() throws MalformedURLException {

        List<LoginDTO> users =new ArrayList<LoginDTO>();
        URL url = new URL("http://localhost:2223/rest/user/all");
        try {

            HttpURLConnection urlConnection = getHttpURLConnection(url);


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Type listType = new TypeToken<ArrayList<LoginDTO>>(){}.getType();
                users = new Gson().fromJson(line, listType);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    private static HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("Accept", "application/json");
        return urlConnection;
    }

    public static LoginDTO getUser( String username) {

        LoginDTO user =new LoginDTO();
        try {
            URL url = new URL("http://localhost:2223/rest/user/" +username);
            HttpURLConnection urlConnection = getHttpURLConnection(url);


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                user = new Gson().fromJson(line,LoginDTO.class);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }


}
