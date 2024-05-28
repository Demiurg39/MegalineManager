package org.megaline.core.models;

import javax.persistence.*;

@Entity
@Table(name = "tariffplans")
public class TariffPlan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "price")
  private double price;

  @Column(name = "internetSpeed")
  private double internetSpeed;

  @Column(name = "description")
  private String description;

  public TariffPlan() {
  }

  public TariffPlan(double price, double internetSpeed, String description) throws IllegalArgumentException {
    if (internetSpeed > 200) {
      throw new IllegalArgumentException("Unreachable internet speed");
    }
    this.price = price;
    this.internetSpeed = internetSpeed;
    this.description = description;
  }

  public TariffPlan(double price, double internetSpeed) throws IllegalArgumentException {
    if (internetSpeed > 200) {
      throw new IllegalArgumentException("Unreachable internet speed");
    }
    this.price = price;
    this.internetSpeed = internetSpeed;
  }

  public Long getId() {
    return id;
  }

  public double getPrice() {
    return price;
  }

  public double getInternetSpeed() {
    return internetSpeed;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}