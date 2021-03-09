package javapractice;

import java.util.ArrayList;
import java.util.Scanner;

public class AddressBook {
    ArrayList<Contact> contactList;

    public AddressBook() {
        contactList = new ArrayList<Contact>();
    }

    public String getInput(String detail) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter " + detail + ": ");
        return sc.next();
    }

    public void addContacts() {
        contactList.add(new Contact(getInput("FirstName"), getInput("LastName"), getInput("Address"),
                getInput("City"), getInput("State"), getInput("ZIP"),
                getInput("Phone"), getInput("Email")));
    }

    public void displayContacts() {
        contactList.stream().forEach(System.out::println);
    }
}
