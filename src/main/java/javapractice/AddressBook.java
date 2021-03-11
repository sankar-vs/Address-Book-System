package javapractice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    ArrayList<Contact> contactList;
    Map<String, ArrayList<Contact>> bookMap;

    public AddressBook() {
        contactList = new ArrayList<>();
        bookMap = new HashMap<>();
    }
    //To get input from user
    public String getInput(String detail) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter " + detail + ": ");
            return sc.next();
        } catch (Exception e) {
            return e.toString();
        }
    }
    //Adds contacts to a list and stores into a map
    public void addContacts() {
        Contact contact;
        String bookName = getInput("BookName");
        String firstName = getInput("FirstName");
        if (checkDuplicates(firstName)) {
            System.out.println("Name already exists");
            return;
        }
        contact = new Contact(firstName, getInput("LastName"), getInput("Address"),
                getInput("City"), getInput("State"), getInput("Pin Code"),
                getInput("Phone"), getInput("Email"));
        if (bookMap.containsKey(bookName)) {
            ArrayList<Contact> contacts = bookMap.get(bookName);
            contacts.add(contact);
        }
        else {
            ArrayList<Contact> contacts = new ArrayList<>();
            contacts.add(contact);
            bookMap.put(bookName, contacts);
        }
        contactList.add(contact);
    }
    //Edits a contact
    public void editContacts() {
        if (checkEmpty()) return;
        String bookName = getInput("BookName");
        if (bookMap.containsKey(bookName)){
            ArrayList<Contact> contacts = bookMap.get(bookName);
            boolean flag = true;
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Details to edit a contact");
            String name = getInput("FirstName");
            for(Contact contact : contacts) {
                if(contact.getFirstName().equals(name)) {
                    flag = false;
                    System.out.println("\tPress the respective number you want to edit\n" +
                            "\t1. First Name\n\t2. Last Name\n\t3. Address \n\t4. City\n\t5. State\n" +
                            "\t6. Pin Code\n\t7. phone number\n\t8. email");
                    int choice = sc.nextInt();
                    switch(choice) {
                        case 1:
                            contact.setFirstName(getInput("FirstName"));
                            break;
                        case 2:
                            contact.setLastName(getInput("LastName"));
                            break;
                        case 3:
                            contact.setAddress(getInput("Address"));
                            break;
                        case 4:
                            contact.setCity(getInput("City"));
                            break;
                        case 5:
                            contact.setState(getInput("State"));
                            break;
                        case 6:
                            contact.setZip(getInput("Pin Code"));
                            break;
                        case 7:
                            contact.setPhone(getInput("Phone Number"));
                            break;
                        case 8:
                            contact.setEmail(getInput("Email"));
                        default:
                            System.out.println("Invalid Choice");
                            break;
                    }
                }
            }
            if (flag) System.out.println("Name not present");
        }
        else
            System.out.println("Entered BookName not present");
    }
    //Displays the stored Contacts
    public void displayContacts() {
        if(checkEmpty()) return;
        bookMap.keySet().forEach(System.out::println);
        String bookName = getInput("BookName");
        if (bookMap.containsKey(bookName)){
            ArrayList<Contact> contacts = bookMap.get(bookName);
            contacts.forEach(System.out::println);
        }
        else
            System.out.println("Entered BookName not present");
    }
    //Deletes a Contact
    public void deleteContacts() {
        if (checkEmpty()) return;
        String bookName = getInput("BookName");
        if (bookMap.containsKey(bookName)){
            ArrayList<Contact> contacts = bookMap.get(bookName);
            boolean flag = true;
            System.out.println("Enter Details to delete a contact");
            String name = getInput("FirstName");
            for(Contact contact : contacts) {
                if (contact.getFirstName().equals(name)) {
                    flag = false;
                    contactList.remove(contact);
                    contacts.remove(contact);
                    break;
                }
            }
            if (flag) System.out.println("Name not present");
        }
    }
    //Checks if the current list if empty or not
    public boolean checkEmpty() {
        if(contactList.isEmpty() && bookMap.isEmpty()) {
            System.out.println("Create a contact before you edit");
            return true;
        }
        return false;
    }
    //Check for duplicates
    public boolean checkDuplicates(String firstName){
        int flag = 0;
        for (Contact contact : contactList) {
            if (contact.getFirstName().equalsIgnoreCase(firstName))
                flag++;
        }
        return flag > 0;
    }

    public void searchByCityOrState() {
        if (checkEmpty()) return;
            boolean flag = true;
        System.out.println("Enter Details to search");
        String city = getInput("City");
        String state = getInput("state");
        for(Contact contact : contactList) {
            if (contact.getCity().equalsIgnoreCase(city) || contact.getState().equalsIgnoreCase(state)) {
                flag = false;
                System.out.println(contact);
            }
        }
        if (flag) System.out.println("Name not present");
    }
}