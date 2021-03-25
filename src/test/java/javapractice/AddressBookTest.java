package javapractice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

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

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    public Map<String, ArrayList<Contact>> getContactList() {
        Type REVIEW_TYPE = new TypeToken<List<Contact>>() {}.getType();
        Response response = RestAssured.get("/Contacts");
        System.out.println("Contact Entries In JSONServer:\n" + response.asString());
        List<Contact> listOfContacts = new Gson().fromJson(response.asString(), REVIEW_TYPE);
        Map<String, ArrayList<Contact>> bookAPI = new HashMap<>();
        bookAPI.put("API", (ArrayList<Contact>) listOfContacts);
        return bookAPI;
    }

    public Response addContactToJsonServer(Contact contact) {
        String empJson = new Gson().toJson(contact);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(empJson);
        return request.post("/Contacts");
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
        Assertions.assertEquals(5, addressBook.getBookMapSizeOfValues("DB").size());
    }

    @Test
    public void givenNewStateForContactWithFirstName_whenUpdated_shouldSyncWithDB() {
        AddressBook addressBook = new AddressBook();
        addressBook.updateDatabase("city", "Ana", "Blr");
        Contact contact = addressBook.getBookMapSizeOfValues("DB").stream().filter(e -> e.getCity().equals("Blr")).findFirst().orElse(null);
        Assertions.assertEquals("Blr", contact.getCity());
    }

    @Test
    void givenDateRangeToAddressBookInDB_WhenRetrieved_ShouldMatchFilteredContactsCount() {
        AddressBook addressBook = new AddressBook();
        List<Contact> filteredResult = addressBook.readDateRangeDBAddressBook("2018-01-01", "2020-12-22");
        Assertions.assertEquals(3, filteredResult.size());
    }

    @Test
    void givenCityOrStateToAddressBookInDB_WhenRetrieved_ShouldMatchFilteredContactsCount() {
        AddressBook addressBook = new AddressBook();
        List<Contact> filteredResult = addressBook.filterDBAddressBookBYCityOrState("Brisbane", "NSW");
        Assertions.assertEquals(3, filteredResult.size());
    }

    @Test
    void givenNewContactToDB_whenAdded_shouldSyncWithDB () {
        AddressBook addressBook = new AddressBook();
        addressBook.addContactToDB("Sachin","Tendulkar","Appartments",
                "Mumbai","MH","123456","1234567890","test1.100@gmail.com", LocalDate.now());
        boolean result = addressBook.checkSyncWithDB("Sachin");
        Assertions.assertTrue(result);
    }

    @Test
    void givenNewContactToAddressBookDB_whenAdded_shouldMatchWithEntriesWithoutUsingThread () {
        Contact[] contactArray = {
                new Contact("Sachin", "Tendulkar", "Appartments",
                        "Mumbai", "MH", "123456", "1234567890", "test1.100@gmail.com", LocalDate.now()),
                new Contact("Sharon", "John", "Samrudhi Appartments",
                        "Coimbatore", "TN", "560038", "7894561230", "test2.100@gmail.com", LocalDate.now())
        };
        AddressBook addressBook = new AddressBook();
        Instant start = Instant.now();
        addressBook.addContactWithoutThreads(Arrays.asList(contactArray));
        Instant end = Instant.now();
        System.out.println("Duration with thread  " + Duration.between(start, end));
        Assertions.assertEquals(6, addressBook.getBookMapSizeOfValues("DB").size());
    }

    @Test
    void givenNewContactToAddressBookDB_whenAdded_shouldMatchWithEntriesUsingThread () {
        Contact[] contactArray = {
                new Contact("Sachin", "Tendulkar", "Appartments",
                        "Mumbai", "MH", "123456", "1234567890", "test1.100@gmail.com", LocalDate.now()),
                new Contact("Sharon", "John", "Samrudhi Appartments",
                        "Coimbatore", "TN", "560038", "7894561230", "test2.100@gmail.com", LocalDate.now())
        };
        AddressBook addressBook = new AddressBook();
        Instant start = Instant.now();
        addressBook.addContactWithThreads(Arrays.asList(contactArray));
        Instant end = Instant.now();
        System.out.println("Duration with thread  " + Duration.between(start, end));
        Assertions.assertEquals(6, addressBook.getBookMapSizeOfValues("DB").size());
    }

    @Test
    public void givenContactsInJSONServer_WhenRetrieved_ShouldMatchTheCount() {
        AddressBook book = new AddressBook(getContactList());
        Assertions.assertEquals(5, book.getBookMapSizeOfValues("API").size());
    }

    @Test
    void givenListOfNewContact_whenAdded_shouldMatch201Response() {
        AddressBook book = new AddressBook(getContactList());
        Contact[] contacts = {
                new Contact(0,"Sachin", "Tendulkar", "Appartments",
                        "Mumbai", "MH", "123456", "1234567890", "test1.100@gmail.com", LocalDate.now()),
                new Contact(0,"Sharon", "John", "Samrudhi Appartments",
                        "Coimbatore", "TN", "560038", "7894561230", "test2.100@gmail.com", LocalDate.now())
        };
        for (Contact contact : contacts){
            Response response = addContactToJsonServer(contact);
            int statusCode = response.getStatusCode();
            Assertions.assertEquals(201,statusCode);
            Contact obj = new Gson().fromJson(response.asString(), Contact.class);
            book.addContactToJSONServer(obj);
        }
        Assertions.assertEquals(7, book.getBookMapSizeOfValues("API").size());
    }

    @Test
    void givenNewCityForContact_whenUpdateShouldMatch200Response() {
        AddressBook book = new AddressBook(getContactList());
        book.updateContactJSONServer("Sachin","Pune");
        Contact contact = book.getContact("Sachin");

        String empJson = new Gson().toJson(contact);
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Content-Type","application/json");
        requestSpecification.body(empJson);
        Response response = requestSpecification.put("/Contacts/"+6);
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(200,statusCode);
    }

    @Test
    void givenContactToDelete_whenDelete_ShouldMatch200Response() {
        AddressBook book = new AddressBook(getContactList());

        Contact contact = book.getContact("Sharon");
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Content-Type","application/json");
        Response response = requestSpecification.delete("/Contacts/"+7);
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(200,statusCode);

        book.deleteContactFromJSONServer(contact.getFirstName());
        Assertions.assertEquals(5, book.getBookMapSizeOfValues("API").size());
    }
}
