package org.megaline.database.manager.models;

import java.util.UUID;

public class User {
  private UUID id;
  private String name;
  private String address;
  private String passportId;

  public User(UUID id, String name, String address, String passportId) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.passportId = passportId;
  }

  public User(String name, String address, String passportId) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.address = address;
    this.passportId = passportId;
  }

  public User(String name, String address) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.address = address;
  }

  public UUID getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getAddress() {
    return this.address;
  }

  public String getPassportId() {
    return this.passportId;
  }
}
