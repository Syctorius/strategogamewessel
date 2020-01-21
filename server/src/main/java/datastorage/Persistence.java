package datastorage;


import server.StrategoLogin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Persistence {

    private String saveDataLoc = "C:\\Users\\wesse\\Downloads\\strategogamewessel\\server\\src\\main\\java\\datastorage\\strategologin.txt";

    public void serializeObject(Object o, String loc) throws IOException {

        try (FileOutputStream fos = new FileOutputStream(loc); ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(o);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }


    public void saveData(Map webShop) throws IOException {
        serializeObject(webShop, saveDataLoc);
    }



    public Map getSavedData() throws IOException, ClassNotFoundException {
        return getFromFileAndDeserializeData(Map.class, saveDataLoc);
    }

    private <T> T getFromFileAndDeserializeData(Class<T> cls, String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        T object;
        try (ObjectInputStream ois = new ObjectInputStream(fis)) {
            object = cls.cast(ois.readObject());
            ois.close();
        }
        fis.close();
        return object;
    }


}
