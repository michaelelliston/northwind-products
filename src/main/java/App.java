import java.sql.*;

public class App {

    public static void main(String[] args) {

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", "root", "yearup");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from northwind.products;");

            while (resultSet.next()) {
                String name = resultSet.getString("ProductName");
                System.out.println(name);
            }

            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
