package com.axway.academy.mario.JDBCHomework;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App 
{
	private static final String URL = "jdbc:mysql://localhost:3306";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Ultras1914";
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	public static void main(String[] args) {

		Connection connection = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;

		try {

			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			System.out.println("Creating statement...");
			statement = connection.createStatement();

			String dropDbQuery = "DROP DATABASE IF EXISTS bank_db;";
			statement.execute(dropDbQuery);

			String query = "CREATE DATABASE bank_db;";
			statement.executeUpdate(query);

			query = "USE bank_db;";
			statement.execute(query);

			String dropTableEmployees = "DROP TABLE IF EXISTS employees;";
			String createTableEmployees = "CREATE TABLE employees(" + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "name VARCHAR(20)," + "surname VARCHAR(20)," + "birthdate DATE);";
			statement.executeUpdate(createTableEmployees);

			String insertIntoEmployees = "INSERT INTO employees" + "(id, name, surname, birthdate)" + "VALUES"
					+ "(1, 'Martin', 'Dimitrov', '1990/04/03')," + "(2, 'Georgi', 'Ivanov', '1992/01/01'),"
					+ "(3, 'Ivan', 'Simeonov', '1983/03/18')," + "(4, 'Antonya', 'Anastasieva', '1989/12/15'),"
					+ "(5, 'Hristo', 'Manasiev', '1986/08/24');";
			statement.execute(insertIntoEmployees);

			String findYoungestEmployee = "SELECT * FROM employees\n" + "ORDER BY birthdate DESC\n" + "LIMIT 1;";
			ResultSet resultSet = statement.executeQuery(findYoungestEmployee);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				Date birthdate = resultSet.getDate("birthdate");
				System.out.println(id + " " + firstName + " " + surname + " " + birthdate);
			}

			String findOldestEmployee = "SELECT * FROM employees\n" + "ORDER BY birthdate ASC\n" + "LIMIT 1;";
			resultSet = statement.executeQuery(findOldestEmployee);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				Date birthdate = resultSet.getDate("birthdate");
				System.out.println(id + " " + firstName + " " + surname + " " + birthdate);
			}

			resultSet.close();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
