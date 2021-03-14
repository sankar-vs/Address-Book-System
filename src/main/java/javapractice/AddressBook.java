package javapractice;

import java.util.*;

public class AddressBook {
    Scanner sc = new Scanner(System.in);
    public enum IOService {CONSOLE_IO, FILE_IO}
    private ArrayList<Contact> contactList;
    private Map<String, ArrayList<Contact>> bookMap;

    public AddressBook() {
        contactList = new ArrayList<>();
        bookMap = new HashMap<>();
    }
    //To get input from user
    public String getInput(String detail) {
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
            System.out.println("Enter Details to edit a contact");
            String name = getInput("FirstName");
            for(Contact contact : contacts) {
                if(contact.getFirstName().equals(name)) {
                    flag = false;
                    try {
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
                    } catch (Exception e) {
                        System.out.println(e.toString());
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
        else {
            System.out.println("Entered BookName not present");
            if (!contactList.isEmpty()) {
                contactList.forEach(System.out::println);
            }
        }
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
    //Searches by City or State
    public void searchByCityOrState() {
        if (checkEmpty()) return;
        int count = 0;
        System.out.println("Enter Details to search");
        String city = getInput("City");
        String state = getInput("state");
        for(Contact contact : contactList) {
            if (contact.getCity().equalsIgnoreCase(city) || contact.getState().equalsIgnoreCase(state)) {
                count++;
                System.out.println(contact);
            }
        }
        if (count == 0)
            System.out.println("Name not present");
        else
            System.out.println("Total number count: " + count);
    }
    //Sort the Contacts according to User's Choice
    public void sortContact() {
        if (checkEmpty()) return;
        try {
            System.out.println("\t1. Sort by FirstName \n\t2. Sort by City \n\t3. Edit Sort by State \n\t4. Sort by Pin Code");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    contactList.stream().sorted(Comparator.comparing(Contact::getFirstName)).forEach(System.out::println);
                    break;
                case 2:
                    contactList.stream().sorted(Comparator.comparing(Contact::getCity)).forEach(System.out::println);
                    break;
                case 3:
                    contactList.stream().sorted(Comparator.comparing(Contact::getState)).forEach(System.out::println);
                    break;
                case 4:
                    contactList.stream().sorted(Comparator.comparing(Contact::getZip)).forEach(System.out::println);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void fileIO() {
        try {
            System.out.println("\t1. WriteContactInFile \n\t2. PrintData " +
                    "\n\t3. Count entries \n\t4. Read entries") ;
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    new AddressBookFileIO().write(contactList);
                    break;
                case 2:
                    new AddressBookFileIO().printData();
                    break;
                case 3:
                    System.out.println("Count: " + new AddressBookFileIO().countEntries());
                    break;
                case 4:
                    this.contactList = (ArrayList<Contact>) new AddressBookFileIO().readData();
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void openCSV() {
        try {
            System.out.println("\t1. Write to CSV \n\t2. Read from CSV ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    new CSVReaderWriter().writeCSV(contactList);
                    break;
                case 2:
                    this.contactList = (ArrayList<Contact>) new CSVReaderWriter().readCSV();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}