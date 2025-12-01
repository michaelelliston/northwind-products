import org.apache.commons.dbcp2.BasicDataSource;
import utilities.InputGetter;

import javax.sql.DataSource;
import java.sql.*;

public class App {

    private static final String[] USER_INFO = {"root", "yearup"};
    private static final String DB_URL = "jdbc:mysql://localhost:3306/northwind";

    public static void main(String[] args) {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(USER_INFO[0]);
        dataSource.setPassword(USER_INFO[1]);

        int userSelection = 99;
        while (userSelection != 0) {
            userSelection = InputGetter.getInt("""
                    1) Display all products
                    2) Display all customers
                    3) Display all categories
                    
                    0) Exit
                    
                    Please input the number corresponding to your selection:
                    """);

            switch (userSelection) {
                case 1 -> displayProductsFromNorthwind(dataSource);
                case 2 -> displayCustomersOfNorthwind(dataSource);
                case 3 -> displayCategoriesOfNorthwind(dataSource);
            }
        }
    }

    private static void displayCategoriesOfNorthwind(DataSource dataSource) {

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("select CategoryID, CategoryName from categories order by CategoryID;");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.printf("Category ID: %d | Category Name: %15s\n", resultSet.getInt(1), resultSet.getString(2));
                }
            }

            int userInput = InputGetter.getInt("\nPlease input the CategoryID number you wish to see products of: ");

            preparedStatement = connection.prepareStatement("select ProductID, ProductName, UnitPrice, UnitsInStock from products where CategoryID = ?");
            preparedStatement.setInt(1, userInput);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.printf("Product ID: %3d | Product Name: %40s | Unit Price: $%.2f | Units in Stock: %3d\n",
                            resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getInt(4));
                }
            }

            InputGetter.getString("\nPlease input any character to continue: ");
            System.out.println();

        } catch (SQLException e) {
            System.err.println("An error occurred: " + e);
        }
    }

    public static void displayProductsFromNorthwind(DataSource dataSource) {

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("select ProductName, ProductID, UnitPrice, UnitsInStock from products;");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.printf("%30s | ProductID: %3d | Unit Price: $%.2f | Units Stocked: %3d\n", resultSet.getString(1), resultSet.getInt(2),
                            resultSet.getDouble(3), resultSet.getInt(4));
                }
            }

            InputGetter.getString("\nPlease input any character to continue: ");
            System.out.println();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayCustomersOfNorthwind(DataSource dataSource) {

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("select ContactName, CompanyName, City, Country, Phone from customers;");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.printf("Contact Name: %30s | Company Name: %40s | City: %15s | Country: %15s | Phone Number: %15s\n",
                            resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
                }
            }

            InputGetter.getString("\nPlease input any character to continue: ");
            System.out.println();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
