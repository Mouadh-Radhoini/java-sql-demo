
package com.example;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        // Database configuration
        String host = System.getenv().getOrDefault("DB_HOST", "localhost");
        String port = System.getenv().getOrDefault("DB_PORT", "3306");
        String database = System.getenv().getOrDefault("DB_NAME", "company_db");

        String user = System.getenv().getOrDefault("DB_USER", "appuser");
        String password = System.getenv().getOrDefault("DB_PASSWORD", "app123");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        try (Connection connection =
                     DriverManager.getConnection(url, user, password)) {

            System.out.println("Connected to MySQL!");

            // 1. Create employee table
            String createTable = """
                    CREATE TABLE IF NOT EXISTS employees (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100),
                        city VARCHAR(100)
                    )
                    """;

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTable);
            }

            System.out.println("Table created successfully!");

            // 2. Employee information
            String name = "Ali";
            String city = "Mahdia";

            if (!EmployeeValidator.isValid(name, city)) {
                throw new IllegalArgumentException("Invalid employee information");
            }

            // 3. Insert employee
            String insertSQL =
                    "INSERT INTO employees (name, city) VALUES (?, ?)";

            try (PreparedStatement statement =
                         connection.prepareStatement(insertSQL)) {

                statement.setString(1, name);
                statement.setString(2, city);

                statement.executeUpdate();
            }

            System.out.println("Employee inserted successfully!");

            // 4. Display employees
            String selectSQL = "SELECT * FROM employees";

            try (Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(selectSQL)) {

                System.out.println("\n--- Employees ---");

                while (result.next()) {

                    int id = result.getInt("id");
                    String employeeName = result.getString("name");
                    String employeeCity = result.getString("city");

                    System.out.println(
                            id + " | " + employeeName + " | " + employeeCity
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
