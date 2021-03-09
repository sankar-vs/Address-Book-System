package javapractice;

import org.junit.jupiter.api.Test;

public class BookTest {
    @Test
    public void startProgram() {
        Contact contact = new Contact("Sankar","V S","Samrudhi Appartments",
                "Coimbatore","TN","560038","7894561230","test.100@gmail.com");
        System.out.println(contact);
    }
}
