package org.megaline.core.models;

import javax.persistence.*;

@Entity
@Table(name = "connections")
public class Connection {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "ipAddress", nullable = false)
  private String ipAddress;

  @Column(name = "connectionStatus", nullable = false)
  private boolean connectionStatus;

  @Column(name = "connectionSpeed")
  private double connectionSpeed;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tariffId")
  private TariffPlan tariffPlan;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId")
  private User user;

  public Connection() {
  }

  public Connection(String ipAddress, User user, boolean connectionStatus) {
    this.ipAddress = ipAddress;
    this.user = user;
    this.connectionStatus = connectionStatus;
  }

  public Connection(String ipAddress, User user) {
    this(ipAddress, user, false);
  }

  public Connection(User user) {
    this("127.0.0.1", user, false);
  }

  public Long getId() {
    return id;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public boolean getConnectionStatus() {
    return connectionStatus;
  }

  public double getConnectionSpeed() {
    return connectionSpeed;
  }

  public TariffPlan getTariffPlan() {
    return tariffPlan;
  }

  public User getUser() {
    return user;
  }

  public void setConnectionStatus(boolean connectionStatus) {
    this.connectionStatus = connectionStatus;
  }

  public void setConnectionSpeed(double connectionSpeed) {
    this.connectionSpeed = connectionSpeed;
  }
}