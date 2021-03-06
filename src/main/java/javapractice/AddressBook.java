package javapractice;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBook {
    Scanner sc = new Scanner(System.in);
    private Map<String, ArrayList<Contact>> bookMap;

    public AddressBook() {
        bookMap = new HashMap<>();
    }

    public AddressBook(Map<String, ArrayList<Contact>> book) {
        bookMap = book;
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
        String bookName = getInput("BookName");
        String firstName = getInput("FirstName");
        if (checkDuplicates(bookName, firstName)) {
            System.out.println("Name already exists");
            return;
        }
        Contact contact = new Contact(firstName, getInput("LastName"), getInput("Address"),
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
                if(contact.getFirstName().equalsIgnoreCase(name)) {
                    updateContact(contact);
                    flag = false;
                }
            }
            if (flag) System.out.println("Name not present");
        }
        else
            System.out.println("Entered BookName not present");
    }
    //Updates the contact
    public void updateContact(Contact contact) {
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
            e.printStackTrace();
        }
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
                    contacts.remove(contact);
                    break;
                }
            }
            if (flag) System.out.println("Entered FirstName not present");
        }
    }
    //Checks if the current list if empty or not
    public boolean checkEmpty() {
        if(bookMap.isEmpty() | bookMap.values().isEmpty()) {
            System.out.println("Create a contact before you edit");
            return true;
        }
        return false;
    }
    //Check for duplicates
    public boolean checkDuplicates(String bookName,String firstName){
        int flag = 0;
        if (bookMap.containsKey(bookName)) {
            ArrayList<Contact> contacts = bookMap.get(bookName);
            for (Contact contact : contacts) {
                if (contact.getFirstName().equalsIgnoreCase(firstName))
                    flag++;
            }
            return flag > 0;
        }
        else
            return true;
    }
    //Searches by City or State
    public void searchByCityOrState() {
        if (checkEmpty()) return;
        int count = 0;
        System.out.println("Enter Details to search");
        String city = getInput("City");
        String state = getInput("state");
        for(Contact contact : bookMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList())) {
            if (contact.getCity().equalsIgnoreCase(city) || contact.getState().equalsIgnoreCase(state)) {
                count++;
                System.out.println(contact);
            }
        }
        if (count == 0)
            System.out.println("City or State not present");
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
                    bookMap.values().stream().flatMap(x -> x.stream().sorted(Comparator.comparing(Contact::getFirstName))).forEach(System.out::println);
                    break;
                case 2:
                    bookMap.values().stream().flatMap(x -> x.stream().sorted(Comparator.comparing(Contact::getCity))).forEach(System.out::println);
                    break;
                case 3:
                    bookMap.values().stream().flatMap(x -> x.stream().sorted(Comparator.comparing(Contact::getState))).forEach(System.out::println);
                    break;
                case 4:
                    bookMap.values().stream().flatMap(x -> x.stream().sorted(Comparator.comparing(Contact::getZip))).forEach(System.out::println);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    //Reads and Writes data from a Text File and performs operations chosen by the User
    public void fileIO() {
        try {
            System.out.println("\t1. WriteContactInFile \n\t2. PrintData \n\t3. Count entries \n\t4. Read entries") ;
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    String bookName = getInput("BookName");
                    if (bookMap.containsKey(bookName))
                        new AddressBookFileIO().write(bookMap.get(bookName));
                    break;
                case 2:
                    new AddressBookFileIO().printData();
                    break;
                case 3:
                    System.out.println("Count: " + new AddressBookFileIO().countEntries());
                    break;
                case 4:
                    bookMap.put("File", (ArrayList<Contact>) new AddressBookFileIO().readData());
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    //Reads and Writes data from a CSV File
    public void openCSV() {
        try {
            System.out.println("\t1. Write to CSV \n\t2. Read from CSV ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    String bookName = getInput("BookName");
                    if (bookMap.containsKey(bookName))
                        new CSVReaderWriter().writeCSV(bookMap.get(bookName));
                    break;
                case 2:
                    bookMap.put("CSV", (ArrayList<Contact>) new CSVReaderWriter().readCSV());
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    //Reads and Writes data from a JSON File
    public void openJSON() {
        try {
            System.out.println("\t1. Write to JSON \n\t2. Read from JSON ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    String bookName = getInput("BookName");
                    if (bookMap.containsKey(bookName))
                        new JSONReaderWriter().writeJSON(bookMap.get(bookName));
                    break;
                case 2:
                    bookMap.put("JSON", (ArrayList<Contact>) new JSONReaderWriter().readJSON());
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    //Reads and Writes data from a DATABASE
    public void databaseConnectivity() {
        ArrayList<Contact> contactList = (ArrayList<Contact>) new JDBC().readData();
        bookMap.put("DB", contactList);
    }
    //Reads and Writes data from a DATABASE
    public void updateDatabase(String type, String name, String value) {
        if (new JDBC().updateData(type, name, value) == 1) {
            databaseConnectivity();
        }
    }
    //Returns List<Contact> of the bookMap given a particular bookMap name
    public List<Contact> getBookMapSizeOfValues(String bookName) {
        return bookMap.get(bookName);
    }

    public List<Contact> readDateRangeDBAddressBook(String startDate, String endDate) {
        return new JDBC().getFilteredDateRangeResult(startDate, endDate);
    }

    public List<Contact> filterDBAddressBookBYCityOrState(String city, String state) {
        return new JDBC().getFilterByCityOrStateResult(city, state);
    }

    public boolean checkSyncWithDB(String name) {
        databaseConnectivity();
        ArrayList<Contact> contactList = (ArrayList<Contact>) new JDBC().getDBfirstName(name);
        return contactList.get(0).equals(getContact(name));
    }

    public Contact getContact(String name) {
        return this.bookMap.values().stream().flatMap(Collection::stream)
                .filter(e -> e.getFirstName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public void addContactToDB(String firstName, String lastName, String address, String city, String state, String zip, String phone, String email, LocalDate date) {
        databaseConnectivity();
        ArrayList<Contact> contactList = bookMap.get("DB");
        contactList.add(new JDBC().addContact(firstName, lastName, address, city, state, zip, phone, email, date));
        bookMap.put("DB", contactList);
    }

    public void addContactWithThreads(List<Contact> contactListData) {
        databaseConnectivity();
        ArrayList<Contact> contactList = bookMap.get("DB");
        Map<Integer,Boolean> contactStatus = new HashMap<>();
        for (Contact x : contactListData) {
            Runnable runnable = () ->{
                contactStatus.put(x.hashCode(),false);
                System.out.println("Employee Being Added : " + Thread.currentThread().getName());
                new JDBC().addContact(x.getFirstName(), x.getLastName(), x.getAddress(), x.getCity(), x.getState(),
                        x.getZip(), x.getPhoneNumber(), x.getEmail(), x.getDate());
                contactStatus.put(x.hashCode(),true);
                System.out.println("Employee Being Added : " + Thread.currentThread().getName());
            };
            Thread thread = new Thread(runnable, x.getFirstName());
            thread.start();
            contactList.add(x);
            bookMap.put("DB", contactList);
        }
        while (contactStatus.containsValue(false)){
            try{Thread.sleep(10);
            }catch  (InterruptedException e){ e.printStackTrace();}
        }
    }

    public void addContactWithoutThreads(List<Contact> contactListData) {
        for (Contact x : contactListData) {
            addContactToDB(x.getFirstName(), x.getLastName(), x.getAddress(), x.getCity(), x.getState(),
                    x.getZip(), x.getPhoneNumber(), x.getEmail(), x.getDate());
        }
    }

    public void addContactToJSONServer(Contact contact) {
        ArrayList<Contact> contactArrayList = bookMap.get("API");
        contactArrayList.add(contact);
        bookMap.put("API", contactArrayList);
    }

    public void updateContactJSONServer(String firstName, String city) {
        Contact contact = this.getContact(firstName);
        if (contact != null) contact.setCity(city);
    }

    public void deleteContactFromJSONServer(String firstName) {
        Contact contact = getContact(firstName);
        ArrayList<Contact> contactArrayList = bookMap.get("API");
        contactArrayList.remove(contact);
        bookMap.put("API", contactArrayList);
    }
}