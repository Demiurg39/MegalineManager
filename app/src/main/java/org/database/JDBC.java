package org.database;

import java.sql.*;

public class JDBC {
  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    // Load JDBC driver !!! FOR LINUX !!!
    Class.forName("org.mariadb.jdbc.Driver");
  }

  // Define the connect method outside of the main method
  static Statement setup_db(String user, String password) {
    try {
      // Connect to the database
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/megalinedb", user, password);
      // Create a Statement object for executing SQL queries
      Statement stmt = conn.createStatement();
      return stmt;
    } catch (SQLException e) {
      // Exception handling if there is an error in connection or statement creation
      System.err.println("Error connecting to the database or creating Statement: " + e.getMessage());
      return null;
    }
  }
}
