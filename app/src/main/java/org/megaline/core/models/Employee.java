package org.megaline.core.models;

import org.megaline.core.util.PasswordHasher;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "employeeId")
  private Long employeeId;

  @Column(name = "passwordHash")
  private String passwordHash;

  public Employee() {
  }

  public Employee(String password) {
    this.passwordHash = PasswordHasher.getHash(password);
  }

  public Long getEmployeeId() {
    return employeeId;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }
}
