package org.database.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.database.manager.models.TariffPlan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TariffPlanManagerTest {

  private TariffPlanManager tariffPlanManager;
  private TariffPlan testTariffPlan;

  @BeforeEach
  public void setUp() {
    tariffPlanManager = new TariffPlanManager();
    testTariffPlan = new TariffPlan(50.0, 100.0, "Test Tariff");
  }

  @AfterEach
  public void tearDown() throws SQLException {
    // Удаляем созданный тарифный план после выполнения каждого теста
    tariffPlanManager.delete(testTariffPlan);
  }

  @Test
  public void testCreateTariffPlan() throws SQLException {
    tariffPlanManager.create(testTariffPlan);
    ResultSet rs = tariffPlanManager.get(testTariffPlan);
    assertTrue(rs.next());
    assertEquals(testTariffPlan.getPrice(), rs.getDouble("tariffPrice"), 0.001);
    assertEquals(testTariffPlan.getSpeed(), rs.getDouble("internetSpeed"), 0.001);
    assertEquals(testTariffPlan.getDescription(), rs.getString("tariffDescription"));
  }

  @Test
  public void testUpdateTariffPlanDescription() throws SQLException {
    tariffPlanManager.create(testTariffPlan);
    tariffPlanManager.update(testTariffPlan, "tariffDescription", "Updated Tariff");
    ResultSet rs = tariffPlanManager.get(testTariffPlan);
    assertTrue(rs.next());
    assertEquals("Updated Tariff", rs.getString("tariffDescription"));
  }
}
