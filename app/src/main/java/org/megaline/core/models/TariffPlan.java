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

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  public TariffPlan() {
  }

  public TariffPlan(double price, double internetSpeed, String name, String description) throws IllegalArgumentException {
    if (internetSpeed > 200) {
      throw new IllegalArgumentException("Unreachable internet speed");
    }
    this.price = price;
    this.internetSpeed = internetSpeed;
    this.name = name;
    this.description = description;
  }

  public TariffPlan(String name, double price, double internetSpeed) throws IllegalArgumentException {
    if (internetSpeed > 300) {
      throw new IllegalArgumentException("Unreachable internet speed");
    }
    this.name = name;
    this.price = price;
    this.internetSpeed = internetSpeed;
  }

  public Long getId() {
    return id;
  }

  public double getPrice() {
    return price;
  }

  public double getInternetSpeed() { return internetSpeed; }

  public String getName() { return name; }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Tariff:" + "\n" +
            "ID = " + id + "\n" +
            "Name = " + name + '\n' +
            "Price = " + price + '\n' +
            "Internet speed = " + internetSpeed + '\n' +
            description + '\n'
            ;
  }
}