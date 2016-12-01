package com.axway.academy.mario.JDBCHomework;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * Application using JDBC. Trying to make a connection with a database and
 * executing some queries.
 * 
 * @author MarioKrastev
 * 
 */

public class App {
	/**
	 * @param URL
	 *            - constant with URL to connect with.
	 * @param USERNAME
	 *            - constant with username to login.
	 * @param PASSWORD
	 *            - constant with password to login.
	 * @param JDBC_DRIVER
	 *            - a JDBC Driver to open a database connection.
	 */
	private static final String URL = "jdbc:mysql://localhost:3306";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Ultras1914";
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	public static void main(String[] args) {

		// initialization of connection and statement - all
		// we need in this exercise.
		Connection connection = null;
		Statement statement = null;

		try {

			// using JDBC driver, URL, username and password we try to connect
			// to the database.
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			// creating a statement.
			System.out.println("Creating statement...");
			statement = connection.createStatement();

			// using drop database if exists for our convenience to invoke
			// exceptions every time we test our application.
			String dropDbQuery = "DROP DATABASE IF EXISTS bank_db;";
			statement.execute(dropDbQuery);

			// creating database.
			String query = "CREATE DATABASE bank_db;";
			statement.executeUpdate(query);

			// using our new database to start working there.
			query = "USE bank_db;";
			statement.execute(query);

			// creating table employees
			String createTableEmployees = "CREATE TABLE employees(" + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "name VARCHAR(20)," + "surname VARCHAR(20)," + "birthdate DATE);";
			statement.executeUpdate(createTableEmployees);

			// adding some records in our new table
			String insertIntoEmployees = "INSERT INTO employees" + "(id, name, surname, birthdate)" + "VALUES"
					+ "(1, 'Martin', 'Dimitrov', '1990/04/03')," + "(2, 'Georgi', 'Ivanov', '1992/01/01'),"
					+ "(3, 'Ivan', 'Simeonov', '1983/03/18')," + "(4, 'Antonya', 'Anastasieva', '1989/12/15'),"
					+ "(5, 'Hristo', 'Manasiev', '1986/08/24');";
			statement.execute(insertIntoEmployees);

			// finding youngest employee
			String findYoungestEmployee = "SELECT * FROM employees HAVING birthdate = (SELECT MAX(birthdate) FROM employees);";
			ResultSet resultSet = statement.executeQuery(findYoungestEmployee);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				Date birthdate = resultSet.getDate("birthdate");
				System.out.println(id + " " + firstName + " " + surname + " " + birthdate);
			}

			// finding oldest employee
			String findOldestEmployee = "SELECT * FROM employees HAVING birthdate = (SELECT MIN(birthdate) FROM employees);";
			resultSet = statement.executeQuery(findOldestEmployee);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				Date birthdate = resultSet.getDate("birthdate");
				System.out.println(id + " " + firstName + " " + surname + " " + birthdate);
			}

			// checking the statement and connection and closing them.
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
