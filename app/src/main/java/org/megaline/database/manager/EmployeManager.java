package org.megaline.database.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.megaline.database.JDBC;
import org.megaline.database.manager.models.Employe;


public class EmployeManager implements InterfaceManager<Employe> {

  @Override
  public void create(Employe model) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

      db.executeQuery("INSERT INTO Employers (employeId, passwordHash, privileges) VALUES (" +
          "'" + model.getEmployeId() + "', '" + model.getPasswordHash() + "', '" + model.getPrivileges() + "')");
  }

  @Override
  public void update(Employe model, String... args) throws SQLException {
    if (args.length % 2 != 0) {
        throw new IllegalArgumentException(
            "Invalid number of arguments. Each field name should be followed by its new value.");
    }
    JDBC db = new JDBC("demi", "demi");

    StringBuilder queryBuilder = new StringBuilder("UPDATE Employers SET ");

    for (int i = 0; i < args.length; i += 2) {
        queryBuilder.append(args[i]).append(" = '").append(args[i + 1]).append("'");
        if (i < args.length - 2) {
            queryBuilder.append(", ");
        }
    }

    // Используем идентификатор из объекта TariffPlan
    queryBuilder.append(" WHERE employeIdId = ").append(model.getEmployeId());
    String query = queryBuilder.toString();

    db.executeUpdate(query);
  }

  @Override
  public void delete(Employe model) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

    db.executeUpdate("DELETE FROM Employers WHERE employeId = '" + model.getEmployeId() + "'");
  }

  @Override
  public ResultSet get(Employe model) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

    ResultSet rs = db.executeQuery("SELECT * FROM Employers WHERE employeId = '" + model.getEmployeId() + "'");

    return rs;
  }
}
