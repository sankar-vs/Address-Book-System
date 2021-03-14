package javapractice;

import com.opencsv.bean.CsvBindByName;

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

    public Contact() {}

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
        return  "First Name: "+firstName+", Last Name: "+lastName+", Address: "+address+", City: "+city+
                ", State: "+state+", ZIP: "+zip+", Phone: "+phoneNumber+", Email: "+email;
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
}
