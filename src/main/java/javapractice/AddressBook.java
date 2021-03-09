package javapractice;

import java.util.ArrayList;
import java.util.Scanner;

public class AddressBook {
    ArrayList<Contact> contactList;

    public AddressBook() {
        contactList = new ArrayList<>();
    }

    public String getInput(String detail) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter " + detail + ": ");
            return sc.next();
        } catch (Exception e) {
            return e.toString();
        }
    }

    public void addContacts() {
        contactList.add(new Contact(getInput("FirstName"), getInput("LastName"), getInput("Address"),
                getInput("City"), getInput("State"), getInput("Pin Code"),
                getInput("Phone"), getInput("Email")));
    }

    public void editContacts() {
        if (checkEmpty()) return;
        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Details to edit a contact");
        String name = getInput("FirstName");
        for(Contact contact : contactList) {
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

    public void displayContacts() {
        if(checkEmpty()) return;
        contactList.forEach(System.out::println);
    }

    public void deleteContacts() {
        if (checkEmpty()) return;
        boolean flag = true;
        System.out.println("Enter Details to delete a contact");
        String name = getInput("FirstName");
        for(Contact contact : contactList) {
            if (contact.getFirstName().equals(name)) {
                flag = false;
                contactList.remove(contact);
                break;
            }
        }
        if (flag) System.out.println("Name not present");
    }

    public boolean checkEmpty() {
        if(contactList.isEmpty()) {
            System.out.println("Create a contact before you edit");
            return true;
        }
        return false;
    }
}