import java.sql.*;

public class App {

    public static void main(String[] args) {

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", "root", "yearup");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from products;");

            while (resultSet.next()) {
                String name = resultSet.getString("ProductName");
                int productID = resultSet.getInt("ProductID");
                double unitPrice = resultSet.getDouble("UnitPrice");
                int unitsStocked = resultSet.getInt("UnitsInStock");
                System.out.printf("%30s | ProductID: %3d | Unit Price: $%.2f | Units Stocked: %3d\n", name, productID, unitPrice, unitsStocked);
            }

            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
