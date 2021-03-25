package javapractice;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBC {

    private Connection getConnection() throws SQLException {
        String jdbcULR = "jdbc:mysql://localhost:3306/addressbook_service?useSSL=false";
        String userName = "root";
        String password = "root7433";
        Connection connection;
        System.out.println("Connecting To DB: " + jdbcULR);
        connection = DriverManager.getConnection(jdbcULR,userName,password);
        System.out.println("Connection is successful..! " + connection);
        return connection;
    }

    public List<Contact> readData() {
        String sql = "SELECT * FROM contacts;";
        List<Contact> contactList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            contactList = getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public int updateData(String type, String name, String value) {
        String sql = String.format("UPDATE contacts SET %s = '%s' WHERE firstName = '%s';",type,value,name);
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private List<Contact> getAddressBookData(ResultSet resultSet) {
        List<Contact> contactList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String zip = resultSet.getString("zip");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                contactList.add(new Contact(firstName, lastName, address, city, state, zip, phone, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public List<Contact> getFilteredDateRangeResult(String startDate, String endDate) {
        String sql = String.format("SELECT * FROM contacts WHERE date BETWEEN '%s' AND '%s';", startDate, endDate);
        List<Contact> filteredList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            filteredList = this.getAddressBookData(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return filteredList;
    }

    public List<Contact> getFilterByCityOrStateResult(String city, String state) {
        String sql = String.format("SELECT * FROM contacts WHERE city = '%s' OR state = '%s';", city, state);
        List<Contact> filteredList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            filteredList = this.getAddressBookData(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return filteredList;
    }

    public List<Contact> getDBfirstName(String name) {
        String sql = String.format("SELECT * FROM contacts WHERE firstName = '%s';", name);
        List<Contact> contactList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            contactList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public Contact addContact(String firstName, String lastName, String address, String city, String state, String zip, String phone, String email, LocalDate date) {
        Connection connection = null;
        Contact contact = null;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = String.format("INSERT INTO contacts (firstName, lastName, address, city, state, zip, phone, email, date) value \n" +
                "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", firstName, lastName, address, city, state, zip, phone, email, date);
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        try (Statement statement = connection.createStatement()) {
            sql = String.format("INSERT INTO address_book (book_name, contact_firstName) value ('%s', '%s');", "DB", firstName);
            int rowAffected = statement.executeUpdate(sql);
            if (rowAffected == 1)
                contact = new Contact(firstName, lastName, address, city, state, zip, phone, email, date);

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return contact;
    }
}
