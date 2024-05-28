package org.megaline.core.models;

import org.megaline.core.dao.TariffPlanDao;
import org.megaline.core.util.DHCP;

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

  public Connection(User user) {
    this.user = user;
    this.ipAddress = DHCP.generateIPAddress();
    this.connectionStatus = false;
    this.connectionSpeed = 0.0;
  }

  public Connection(User user, TariffPlan tariffPlan) {
    this(user);
    this.tariffPlan = tariffPlan;
    this.connectionSpeed = tariffPlan.getInternetSpeed();
    this.connectionStatus = true;
  }

  public Connection(User user, boolean connectionStatus, TariffPlan tariffPlan) {
    this(user, tariffPlan);
    this.connectionStatus = connectionStatus;
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

  public void setInternetSpeed(double internetSpeed) {
    this.connectionSpeed = internetSpeed;
  }

  public void setTariffPlan(TariffPlan tariffPlan) {
    this.tariffPlan = tariffPlan;
  }
}