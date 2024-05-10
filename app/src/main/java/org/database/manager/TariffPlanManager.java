package org.database.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.database.JDBC;
import org.database.manager.models.TariffPlan;

public class TariffPlanManager implements InterfaceManager<TariffPlan> {

  @Override
  public void create(TariffPlan tariff) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

    if (tariff.getDescription() != null) {
      db.executeQuery("INSERT INTO TariffPlans (tariffId, tariffPrice, internetSpeed, tariffDescription) VALUES (" +
          "'" + tariff.getId() + "', '" + tariff.getPrice() + "', '" + tariff.getSpeed() + "', '"
          + tariff.getDescription() + "')");
    } else {
      db.executeQuery("INSERT INTO TariffPlans (tariffId, tariffPrice, internetSpeed) VALUES (" +
          "'" + tariff.getId() + "', '" + tariff.getPrice() + "', '" + tariff.getSpeed() + "')");
    }
  }

@Override
public void update(TariffPlan tariff, String... args) throws SQLException {
    if (args.length % 2 != 0) {
        throw new IllegalArgumentException(
            "Invalid number of arguments. Each field name should be followed by its new value.");
    }
    JDBC db = new JDBC("demi", "demi");

    StringBuilder queryBuilder = new StringBuilder("UPDATE TariffPlans SET ");

    for (int i = 0; i < args.length; i += 2) {
        queryBuilder.append(args[i]).append(" = '").append(args[i + 1]).append("'");
        if (i < args.length - 2) {
            queryBuilder.append(", ");
        }
    }

    // Используем идентификатор из объекта TariffPlan
    queryBuilder.append(" WHERE tariffId = ").append(tariff.getId());
    String query = queryBuilder.toString();

    db.executeUpdate(query);
}

  @Override
  public void delete(TariffPlan tariff) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

    db.executeUpdate("DELETE FROM TariffPlans WHERE tariffId = '" + tariff.getId() + "'");
  }

  @Override
  public ResultSet get(TariffPlan tariff) throws SQLException {
    JDBC db = new JDBC("demi", "demi");

    ResultSet rs = db.executeQuery("SELECT * FROM TariffPlans WHERE tariffId = '" + tariff.getId() + "'");

    return rs;
  }
}
