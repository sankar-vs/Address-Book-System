package javapractice;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderWriter {
    public static final String SAMPLE_CSV_FILE_PATH = "src/main/resources/AddressBook.csv";

    public List<Contact> readCSV() throws IOException {
        List<Contact> contactList = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH))
        ){
            CsvToBean<Contact>csvToBean = new CsvToBeanBuilder(reader)
                .withType(Contact.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

            for (Contact csvUser : csvToBean) {
                Contact contact = new Contact(csvUser.getFirstName(), csvUser.getLastName(), csvUser.getAddress(),
                        csvUser.getCity(), csvUser.getState(), csvUser.getZip(), csvUser.getPhoneNumber(), csvUser.getEmail());
                contactList.add(contact);
                System.out.println(csvUser);
            }
        }
        return contactList;
    }

    public static final String OBJECT_LIST = "src/main/resources/write_csv.csv";

    public void writeCSV(ArrayList<Contact> contactList) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try (Writer writer = Files.newBufferedWriter(Paths.get(OBJECT_LIST));
        ){
            StatefulBeanToCsv<Contact>beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            List<Contact> csvUsers = new ArrayList<>();
            for (Contact contact : contactList) {
                csvUsers.add(new Contact(contact.getFirstName(), contact.getLastName(), contact.getAddress(),
                        contact.getCity(), contact.getState(), contact.getZip(), contact.getPhoneNumber(), contact.getEmail()));
            }
            beanToCsv.write(csvUsers);
        }
    }
}
