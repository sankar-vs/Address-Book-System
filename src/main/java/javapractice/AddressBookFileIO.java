package javapractice;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddressBookFileIO {
    public static String ADDRESS_BOOK_FILE_NAME = "src/main/resources/addressBook-file.txt";

    public void write(List<Contact> contacts) {
        StringBuffer contactBuffer = new StringBuffer();
        contacts.forEach(contact ->{
            String empDataString = contact.toString().concat("\n");
            contactBuffer.append(empDataString);
        });
        try {
            Files.write(Paths.get(ADDRESS_BOOK_FILE_NAME),contactBuffer.toString().getBytes());
        }catch (IOException x){
            x.printStackTrace();
        }
    }

    public void printData() {
        try {
            Files.lines(new File(ADDRESS_BOOK_FILE_NAME).toPath())
                    .forEach(System.out::println);
        }catch (IOException x){
            x.printStackTrace();
        }
    }

    public long countEntries() {
        long entries = 0;
        try {
            entries = Files.lines(new File(ADDRESS_BOOK_FILE_NAME).toPath())
                    .count();
        }catch (IOException x){
            x.printStackTrace();
        }
        return entries;
    }

    public List<Contact> readData() {
        List<Contact> contactList = new ArrayList<>();
        try {
            List<String> lines = Files.lines(new File(ADDRESS_BOOK_FILE_NAME).toPath())
                    .map(String::trim)
                    .collect(Collectors.toList());
            for (String line : lines) {
                String[] value = new String[8];
                String[] details = line.split(",");
                for (int i = 0 ; i < details.length ; i++ ) {
                    String[] obj = details[i].split(":");
                    value[i] = obj[1];
                }
                Contact contact = new Contact(value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7]);
                contactList.add(contact);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contactList;
    }
}
