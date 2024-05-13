package org.megaline.database.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.megaline.database.JDBC;
import org.megaline.database.manager.models.Connection;


public class ConnectionManager implements InterfaceManager<Connection> {

  @Override
  public void create(Connection conn) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

    if (conn.getIp() != null) {
      db.executeQuery("INSERT INTO Connections (ipAddress, userId, connectionStatus, connectionSpeed, tariffId) VALUES (" +
          "'" + conn.getIp() + "', '" + conn.getId() + "', '" + conn.getStatus() + "', '" + conn.getSpeed() + "', '" + conn.getTariffId() +"')");
    } else {
      db.executeQuery("INSERT INTO Connections (NULL, userId, connectionStatus, connectionSpeed, tariffId) VALUES (" +
          "'" + conn.getId() + "', '" + conn.getStatus() + "', '" + conn.getSpeed() + "', '" + conn.getTariffId() + "')");
    }
  }

  @Override
  public void update(Connection conn, String... args) throws SQLException {
    if (args.length % 2 != 0) {
      throw new IllegalArgumentException(
          "Invalid number of arguments. Each field name should be followed by its new value.");
    }
    JDBC db = new JDBC("demi", "demi");

    StringBuilder queryBuilder = new StringBuilder("UPDATE Connections SET ");

    for (int i = 0; i < args.length; i += 2) {
      queryBuilder.append(args[i]).append(" = '").append(args[i + 1]).append("'");
      if (i < args.length - 2) {
        queryBuilder.append(", ");
      }
    }

    queryBuilder.append(" WHERE userId = '").append(conn.getId()).append("'");
    String query = queryBuilder.toString();

    db.executeUpdate(query);
  }

  @Override
  public void delete(Connection conn) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

    db.executeUpdate("DELETE FROM Connections WHERE userId = '" + conn.getId() + "'");
  }

  @Override
  public ResultSet get(Connection conn) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

    ResultSet rs = db.executeQuery("SELECT * FROM Connections WHERE userId = '" + conn.getId() + "'");

    return rs;
  }
}
