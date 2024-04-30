package org.database;

import java.sql.*;

public class JDBC {
  private Connection connection;

  public JDBC(String user, String password) throws SQLException {
    connection = DriverManager.getConnection("jdbc:mysql://localhost/megalinedb", user, password);
  }

  public ResultSet executeQuery(String sql) throws SQLException {
    Statement statement = connection.createStatement();
    return statement.executeQuery(sql);
  }

  public int executeUpdate(String sql) throws SQLException {
    Statement statement = connection.createStatement();
    return statement.executeUpdate(sql);
  }

  public void close() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }
}
