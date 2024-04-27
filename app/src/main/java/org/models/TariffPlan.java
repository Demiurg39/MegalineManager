package org.models;

public class TariffPlan {
  private double price;
  private double internetSpeed;
  private String description;


    public TariffPlan(double price, double internetSpeed, String description) throws IllegalArgumentException {
        if (internetSpeed > 200) {
            throw new IllegalArgumentException("Unreachable internet speed");
        }
        this.price = price;
        this.internetSpeed = internetSpeed;
        this.description = description;
    }

  public double getPrice() {
    return this.price;
  }

  public double getSpeed() {
    return this.internetSpeed;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
