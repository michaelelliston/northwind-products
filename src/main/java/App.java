import utilities.InputGetter;

import java.sql.*;

public class App {

    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "yearup";

    public static void main(String[] args) {
        int userSelection = 99;
        while (userSelection != 0) {
            userSelection = InputGetter.getInt("""
                    1) Display all products
                    2) Display all customers
                    
                    0) Exit
                    
                    Please input the number corresponding to your selection:
                    """);

            switch (userSelection) {
                case 1 -> displayProductsFromNorthwind();
                case 2 -> displayCustomersOfNorthwind();
            }
        }
    }

    public static void displayProductsFromNorthwind() {

        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", DB_USERNAME, DB_PASSWORD);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from products;");

            while (resultSet.next()) {
                String name = resultSet.getString("ProductName");
                int productID = resultSet.getInt("ProductID");
                double unitPrice = resultSet.getDouble("UnitPrice");
                int unitsStocked = resultSet.getInt("UnitsInStock");
                System.out.printf("%30s | ProductID: %3d | Unit Price: $%.2f | Units Stocked: %3d\n", name, productID, unitPrice, unitsStocked);
            }

            InputGetter.getString("\nPlease input any character to continue: ");
            System.out.println();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("An error occurred: " + e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("An error occurred: " + e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("An error occurred: " + e);
                }
            }
        }
    }

    public static void displayCustomersOfNorthwind() {

        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", DB_USERNAME, DB_PASSWORD);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from customers order by country;");

            while (resultSet.next()) {
                String contactName = resultSet.getString("ContactName");
                String companyName = resultSet.getString("CompanyName");
                String city = resultSet.getString("City");
                String country = resultSet.getString("Country");
                String phoneNumber = resultSet.getString("Phone");
                System.out.printf("Contact Name: %30s | Company Name: %40s | City: %15s | Country: %15s | Phone Number: %15s\n", contactName, companyName, city, country, phoneNumber);
            }

            InputGetter.getString("\nPlease input any character to continue: ");
            System.out.println();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("An error occurred: " + e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("An error occurred: " + e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("An error occurred: " + e);
                }
            }
        }
    }
}
