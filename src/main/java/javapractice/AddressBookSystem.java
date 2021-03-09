package javapractice;

import java.util.Scanner;

public class AddressBookSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AddressBook book = new AddressBook();
        boolean exit = true;
        while (exit) {
            System.out.println("\t1. Add Contacts \n\t2. Display Contacts \n\t3. Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    book.addContacts();
                    break;
                case 2:
                    book.displayContacts();
                    break;
                case 3:
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }
}
