package javapractice;

import com.opencsv.bean.CsvBindByName;

import java.time.LocalDate;
import java.util.Objects;

public class Contact {

    @CsvBindByName(column = "firstName", required = true)
    private String firstName;
    @CsvBindByName(column = "lastName", required = true)
    private String lastName;
    @CsvBindByName(column = "address")
    private String address;
    @CsvBindByName(column = "city")
    private String city;
    @CsvBindByName(column = "state")
    private String state;
    @CsvBindByName(column = "zip")
    private String zip;
    @CsvBindByName(column = "phone")
    private String phoneNumber;
    @CsvBindByName(column = "email")
    private String email;
    private LocalDate date;

    public Contact() {}

    public Contact(String firstName, String lastName, String address, String city,
                   String state, String zip, String phone, String email, LocalDate date) {
        this(firstName, lastName, address, city, state, zip, phone, email);
        this.date = date;
    }

    public Contact(String firstName, String lastName, String address, String city,
                             String state, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return  "First Name:"+firstName+", Last Name:"+lastName+", Address:"+address+", City:"+city+
                ", State:"+state+", ZIP:"+zip+", Phone:"+phoneNumber+", Email:"+email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPhone(String phone) {
        this.phoneNumber = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(firstName, contact.firstName) && Objects.equals(lastName, contact.lastName) && Objects.equals(address, contact.address) && Objects.equals(city, contact.city) && Objects.equals(state, contact.state) && Objects.equals(zip, contact.zip) && Objects.equals(phoneNumber, contact.phoneNumber) && Objects.equals(email, contact.email);
    }

}
