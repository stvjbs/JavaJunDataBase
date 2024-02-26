package org.example.task1;

import org.example.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Program {
    private final static Random random = new Random();

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "Password";

        Connection connection = DriverManager.getConnection(url, user, password);

        createDatabase(connection);
        System.out.println("Database created successfully");

        useDatabase(connection);
        System.out.println("Use database successfully");

        createTable(connection);
        System.out.println("Table created successfully");

        int count = random.nextInt(5, 11);
        for (int i = 0; i < count; i++) {
            insertData(connection, Student.create());
        }
        System.out.println("Inserting data successfully");

        Collection<Student> studentCollection = readData(connection);
        for (var student : studentCollection) {
            System.out.println(student);
        }
        System.out.println("Reading data successfully");

        for (var student : studentCollection) {
            student.updateName();
            student.updateAge();
            updateData(connection, student);
        }
        System.out.println("Updating data successfully");
//        for (var student : studentCollection) {
//            deleteData(connection,student.getId());
//        }
//        System.out.println("Deleting data successfully");
        connection.close();
        System.out.println("Database connection closed successfully");
    }


    public static void createDatabase(Connection connection) throws SQLException {
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS `student-db`;";
        try (PreparedStatement statement = connection.prepareStatement(createDatabaseSQL)) {
            statement.execute();
        }
    }

    public static void useDatabase(Connection connection) throws SQLException {
        String useDatabaseSQL = "USE `student-db`";
        try (PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)) {
            statement.execute();
        }
    }

    public static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS `students`(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), age INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }

    public static void insertData(Connection connection, Student student) throws SQLException {
        String insertDataSQL = "INSERT INTO `students` (name,age) VALUES (?,?);";
        try (PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.executeUpdate();
        }
    }

    private static Collection<Student> readData(Connection connection) throws SQLException {
        ArrayList<Student> studentsList = new ArrayList<>();
        String readDataSQL = "SELECT * FROM `students`;";
        try (PreparedStatement statement = connection.prepareStatement(readDataSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                studentsList.add(new Student(id, name, age));
            }
            return studentsList;
        }
    }
    public static void updateData(Connection connection, Student student) throws SQLException {
        String updateDataSQL = "UPDATE `students` SET name=?, age =? WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(updateDataSQL)){
            statement.setString(1,student.getName());
            statement.setInt(2,student.getAge());
            statement.setInt(3,student.getId());
            statement.executeUpdate();
        }
    }

    public static void deleteData(Connection connection, int id) throws SQLException{
        String deleteDataSQL = "DELETE FROM `students` WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteDataSQL)){
            statement.setLong(1,id);
            statement.executeUpdate();
        }
    }

}