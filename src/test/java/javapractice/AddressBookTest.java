package javapractice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookTest {
    AddressBook addressBook;
    public Map<String, ArrayList<Contact>> book = new HashMap<>();
    ArrayList<Contact> bookList = new ArrayList<>();
    @BeforeEach
    public void testEntries() {
        Contact contact = new Contact("Sachin","Tendulkar","Appartments",
                "Mumbai","MH","123456","1234567890","test1.100@gmail.com");
        bookList.add(contact);
        contact = new Contact("Sharon","John","Samrudhi Appartments",
                "Coimbatore","TN","560038","7894561230","test2.100@gmail.com");
        bookList.add(contact);
        book.put("Test", bookList);
        addressBook = new AddressBook(book);
    }
    @Test
    public void givenContact_shouldBeCreated_checkAssertionWithResult() {
        Contact contact = new Contact("Sachin","Tendulkar","Appartments",
                "Mumbai","MH","123456","1234567890","test1.100@gmail.com");
        Object result = new Contact("Sachin","Tendulkar","Appartments",
                "Mumbai","MH","123456","1234567890","test1.100@gmail.com");
        Assertions.assertEquals(contact, result);
    }

    @Test
    public void givenContactList_shouldBeWrittenToJSONFile() {
        String path = "src/test/resources/testBook.json";
        JSONReaderWriter json = new JSONReaderWriter(path);
        json.writeJSON(bookList);
        File check = new File(path);
        Assertions.assertTrue(check.exists());
    }

    @Test
    public void givenJSONFile_shouldBeReadToTheList() {
        String path = "src/test/resources/testBook.json";
        JSONReaderWriter json = new JSONReaderWriter(path);
        List<Contact> list = json.readJSON();
        Assertions.assertEquals(list, bookList);
    }

    @Test
    public void givenContactsInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        AddressBook addressBook = new AddressBook();
        addressBook.databaseConnectivity();
        Assertions.assertEquals(4, addressBook.getBookMapSizeOfValues("DB"));
    }

}