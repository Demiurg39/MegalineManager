package org.models;

import java.util.UUID;

public class User {
  private UUID id;
  private String name;
  private String address;
  private String email;

  public User(UUID id, String name, String address, String email) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.email = email;
  }

  public User(String name, String address, String email) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.address = address;
    this.email = email;
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

  public String getEmail() {
    return this.email;
  }
}
