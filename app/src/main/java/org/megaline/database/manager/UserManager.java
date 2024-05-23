package org.megaline.database.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.megaline.database.JDBC;
import org.megaline.database.manager.models.User;

public class UserManager implements InterfaceManager<User> {

  @Override
  public void create(User user) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

    if (user.getPassportId() != null) {
      db.executeQuery("INSERT INTO Users (userId, userName, userAddress, userPassportId) VALUES (" +
          "'" + user.getId() + "', '" + user.getName() + "', '" + user.getAddress() + "', '" + user.getPassportId()
          + "')");
    } else {
      db.executeQuery("INSERT INTO Users (userId, userName, userAddress) VALUES (" +
          "'" + user.getId() + "', '" + user.getName() + "', '" + user.getAddress() + "')");
    }
  }

  @Override
  public void update(User user, String... args) throws SQLException {
    if (args.length % 2 != 0) {
      throw new IllegalArgumentException(
          "Invalid number of arguments. Each field name should be followed by its new value.");
    }
    JDBC db = new JDBC("demi", "demi");

    StringBuilder queryBuilder = new StringBuilder("UPDATE Users SET ");

    for (int i = 0; i < args.length; i += 2) {
      queryBuilder.append(args[i]).append(" = '").append(args[i + 1]).append("'");
      if (i < args.length - 2) {
        queryBuilder.append(", ");
      }
    }

    queryBuilder.append(" WHERE userId = '").append(user.getId()).append("'");
    String query = queryBuilder.toString();

    db.executeUpdate(query);
  }

  @Override
  public void delete(User user) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

    db.executeUpdate("DELETE FROM Users WHERE userId = '" + user.getId() + "'");
  }

  @Override
  public ResultSet get(User user) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

    ResultSet rs = db.executeQuery("SELECT * FROM Users WHERE userId = '" + user.getId() + "'");

    return rs;
  }

  public User getUserById(UUID userId) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

    ResultSet rs = db.executeQuery("SELECT * FROM Users WHERE userId = '" + userId + "'");
    if (rs.next()) {
      return new User(
          UUID.fromString(rs.getString("userId")),
          rs.getString("userName"),
          rs.getString("userAddress"),
          rs.getString("userPassportId"));
    }
    return null;
  }
}
