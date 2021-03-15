package javapractice;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class JSONReaderWriter {
    private static final String CSV_FILE_PATH = "src/main/resources/AddressBook.csv";
    private static final String JSON_FILE_PATH = "src/main/resources/Book.json";


    public void writeJSON() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
            CsvToBeanBuilder<Contact> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(Contact   .class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<Contact> csvToBean = csvToBeanBuilder.build();
            List<Contact> csvUsers =  csvToBean.parse();

            Gson gson = new Gson();
            String json = gson.toJson(csvUsers);
            FileWriter writer = new FileWriter(JSON_FILE_PATH);
            writer.write(json);
            writer.close();
            BufferedReader br = new BufferedReader(new FileReader(JSON_FILE_PATH));
            Contact[] usrObj = gson.fromJson(br,Contact[].class);
            List<Contact> csvUserList = Arrays.asList(usrObj);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
