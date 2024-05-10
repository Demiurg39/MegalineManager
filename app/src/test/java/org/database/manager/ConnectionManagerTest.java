package org.database.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.database.manager.ConnectionManager;
import org.database.manager.UserManager;
import org.database.manager.models.Connection;
import org.database.manager.models.TariffPlan;
import org.database.manager.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectionManagerTest {

  private ConnectionManager connectionManager;
  private Connection testConnection;
  private TariffPlan testTariffPlan;

  private UserManager userManager;
  private User testUser;

  @BeforeEach
  public void setUp() throws SQLException {
    userManager = new UserManager();
    testUser = new User("Test User", "Test Address", "test@example.com");
    userManager.create(testUser);
    connectionManager = new ConnectionManager();
    testTariffPlan = new TariffPlan(50.0, 100.0, "Test Tariff");
    testConnection = new Connection("192.168.0.1", testUser.getId(), false, testTariffPlan);
  }

  @AfterEach
  public void tearDown() throws SQLException {
    // Удаляем созданное соединение после выполнения каждого теста
    connectionManager.delete(testConnection);
    userManager.delete(testUser);
  }

  @Test
  public void testCreateConnection() throws SQLException {
    connectionManager.create(testConnection);
    ResultSet rs = connectionManager.get(testConnection);
    assertTrue(rs.next());
    assertEquals(testConnection.getIp(), rs.getString("ipAddress"));
    assertEquals(testConnection.getId(), UUID.fromString(rs.getString("userId")));
    assertEquals(testConnection.getStatus(), rs.getInt("connectionStatus"));
    assertEquals(testConnection.getTariffId(), rs.getInt("tariffId"));
  }

  @Test
  public void testUpdateConnectionStatus() throws SQLException {
    connectionManager.create(testConnection);
    testConnection.setStatus(true);
    connectionManager.update(testConnection, "connectionStatus", "1");
    ResultSet rs = connectionManager.get(testConnection);
    assertTrue(rs.next());
    assertEquals(true, rs.getBoolean("connectionStatus"));
  }

  @Test
  public void testUpdateConnectionSpeed() throws SQLException {
    connectionManager.create(testConnection);
    testConnection.setSpeed(150.0);
    connectionManager.update(testConnection, "connectionSpeed", "150.0");
    ResultSet rs = connectionManager.get(testConnection);
    assertTrue(rs.next());
    assertEquals(150.0, rs.getDouble("connectionSpeed"));
  }
}
