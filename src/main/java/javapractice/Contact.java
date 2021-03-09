package javapractice;

public class Contact {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String city;
    private final String state;
    private final String zip;
    private final String phone;
    private final String email;

    public Contact(String firstName, String lastName, String address, String city,
                             String state, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }
    public String toString() {
        return "\tFirst Name: "+firstName+"\n\tLast Name: "+lastName+"\n\tAddress: "+address+"\n\tCity: "+city+
                "\n\tState: "+state+"\n\tZIP: "+zip+"\n\tPhone: "+phone+"\n\tEmail: "+email;
    }
}
