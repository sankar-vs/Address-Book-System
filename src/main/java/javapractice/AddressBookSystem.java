package javapractice;

import java.util.Scanner;

public class AddressBookSystem {
    //To employ different features in the book according to the input given by the user
    public static void startAddressBook() {
        Scanner sc = new Scanner(System.in);
        AddressBook book = new AddressBook();
        boolean exit = true;
        while (exit) {
            System.out.println("\t1. Add Contacts \n\t2. Display Contacts \n\t3. Edit Contacts " +
                    "\n\t4. Delete Contacts \n\t5. Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    book.addContacts();
                    break;
                case 2:
                    book.displayContacts();
                    break;
                case 3:
                    book.editContacts();
                    break;
                case 4:
                    book.deleteContacts();
                    break;
                case 5:
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }
    public static void main(String[] args) {
        startAddressBook();
    }
}
