package javapractice;

import java.util.Scanner;

public class AddressBookSystem {
    //To perform different features in the book according to the input given by the user
    public static void startAddressBook() {
        Scanner sc = new Scanner(System.in);
        AddressBook book = new AddressBook();
        boolean exit = true;
        while (exit) {
            try {
                System.out.println("\t1. Add Contacts \n\t2. Display Contacts \n\t3. Edit Contacts " +
                        "\n\t4. Delete Contacts \n\t5. Search by City or State \n\t6. Sort Contacts \n\t7. Exit");
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
                        book.searchByCityOrState();
                        break;
                    case 6:
                        book.sortContact();
                        break;
                    case 7:
                        exit = false;
                        break;
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
    public static void main(String[] args) {
        startAddressBook();
    }
}
