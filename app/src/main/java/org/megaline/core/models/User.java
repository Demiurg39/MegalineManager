package org.megaline.core.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "address")
  private String address;

  @Column(name = "passportId", unique = true)
  private String passportId;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Connection connection;

  public User() {
  }

  public User(String name, String address, String passportId) {
    this.name = name;
    this.address = address;
    this.passportId = passportId;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getPassportId() {
    return passportId;
  }

  @Override
  public String toString() {
    return "Client:" + "\n" +
            "ID = " + id + "\n" +
            "Name = " + name + '\n' +
            "Address = " + address + '\n' +
            "Passport ID = " + passportId + '\n'
            ;
  }
}