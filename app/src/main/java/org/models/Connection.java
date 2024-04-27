package org.models;

import java.util.UUID;

public class Connection {
  private String ipAddress;
  private UUID clientId;
  private boolean connectionStatus;
  private double connectionSpeed;

Connection(String ipAddress, UUID clientId, boolean connectionStatus, double connectionSpeed) {
        this.ipAddress = ipAddress;
        this.clientId = clientId;
        this.connectionStatus = connectionStatus;
        this.connectionSpeed = connectionSpeed;
    }

    Connection(String ipAddress, UUID clientId, boolean connectionStatus) {
        this(ipAddress, clientId, connectionStatus, 20.0);
    }

    Connection(String ipAddress, UUID clientId, double connectionSpeed) {
        this(ipAddress, clientId, false, connectionSpeed);
    }

    Connection(String ipAddress, UUID clientId) {
        this(ipAddress, clientId, false, 20.0);
    }

  public String getIp() {
    return this.ipAddress;
  }

  public UUID getId() {
    return this.clientId;
  }

  public boolean getStatus() {
    return this.connectionStatus;
  }

  public double getSpeed() {
    return this.connectionSpeed;
  }

  public void setStatus(boolean status) {
    this.connectionStatus = status;
  }

  public void setSpeed(double speed) {
    this.connectionSpeed = speed;
  }
}
