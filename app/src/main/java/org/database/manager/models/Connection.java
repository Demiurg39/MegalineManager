package org.database.manager.models;

import java.util.UUID;

public class Connection {
  private String ipAddress;
  private UUID userId;
  private boolean connectionStatus;
  private double connectionSpeed;
  private int tariffId;

  public Connection(String ipAddress, UUID userId, boolean connectionStatus, TariffPlan tariff) {
    this.ipAddress = ipAddress;
    this.userId = userId;
    this.connectionStatus = connectionStatus;
    this.connectionSpeed = tariff.getSpeed();
    this.tariffId = tariff.getId();
  }

  public Connection(String ipAddress, UUID userId, TariffPlan tariff) {
    this(ipAddress, userId, false, tariff);
  }

  public Connection(UUID userId, TariffPlan tariff) {
    this("127.0.0.1", userId, false, tariff);
  }

  public String getIp() {
    return this.ipAddress;
  }

  public UUID getId() {
    return this.userId;
  }

  public int getStatus() {
    return this.connectionStatus?1:0;
  }

  public double getSpeed() {
    return this.connectionSpeed;
  }

  public int getTariffId() {
    return this.tariffId;
  }

  public void setStatus(boolean status) {
    this.connectionStatus = status;
  }

  public void setSpeed(double speed) {
    this.connectionSpeed = speed;
  }
}
