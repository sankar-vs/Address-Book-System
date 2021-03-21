package javapractice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONReaderWriter {
    private static String JSON_FILE_PATH = "src/main/resources/Book.json";

    public JSONReaderWriter() {
    }

    public JSONReaderWriter(String path) {
        JSON_FILE_PATH = path;
    }

    public void writeJSON(ArrayList<Contact> contacts) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(contacts);
            FileWriter writer = new FileWriter(JSON_FILE_PATH);
            writer.write(json);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<Contact> readJSON() {
        Type REVIEW_TYPE = new TypeToken<List<Contact>>() {}.getType();
        List<Contact> contactList = new ArrayList<>();
        try {
            Gson gson = new Gson();
            BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE_PATH));
            contactList = gson.fromJson(reader, REVIEW_TYPE);
            contactList.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contactList;
    }
}
