package main;

import DAO.EmployeeDAO;
import DAO.EmployeeDAOImpl;
import model.City;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws SQLException {
        // Создаем переменные с данными для подключения к базе
        final String user = "postgres";
        final String password = "kirill58";
        final String url = "jdbc:postgresql://localhost:5432/skypro";

        // Создаем соединение с базой с помощью Connection
        // Формируем запрос к базе с помощью PreparedStatement

        try (final Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("SELECT*FROM employee WHERE city_id = (?)")) {
            statement.setInt(1, 1);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String employeeName = "Name: " + resultSet.getString("first_name");
                String employeeLastName = "Last name: " + resultSet.getString("last_name");
                String employeeGender = "Gender: " + resultSet.getString("gender");
                String employeeCity = "City: " + resultSet.getString("city_name");
                System.out.println(employeeName);
                System.out.println(employeeLastName);
                System.out.println(employeeGender);
                System.out.println(employeeCity);
                System.out.println(resultSet);
            }
        }
        try (final Connection connection = DriverManager.getConnection(url, user, password)) {
            EmployeeDAO employeeDAO = new EmployeeDAOImpl(connection);

            employeeDAO.create(new Employee("Petr", "Petrov", "male", 20, new City(2, "Moscow")));

            System.out.println(employeeDAO.readById(52));


            List<Employee> employeeList = new ArrayList<>(employeeDAO.readAll());
            employeeList.forEach(System.out::println);

            employeeDAO.updateEmployeeById(8, "Ksenya", "Ivanova", "woman", 32, 1);

            employeeDAO.deleteEmployeeById(8);
        }
    }
}