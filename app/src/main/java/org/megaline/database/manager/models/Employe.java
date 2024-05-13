package org.megaline.database.manager.models;

import org.megaline.database.manager.util.PasswordHasher;

public class Employe {

  private int employeId;
  private String passwordHash;
  private Privileges privileges;

  public Employe(int employeId, String password) {
    this.employeId = employeId;
    this.passwordHash = PasswordHasher.getHash(password);
    this.privileges = Privileges.USER;
  }

  public Employe(int id, String password, Privileges privileges) {
    this(id, password);
    this.privileges = privileges;
  }

  public int getEmployeId() {
    return employeId;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public Privileges getPrivileges() {
    return privileges;
  }

  public void setPrivileges(Privileges privileges, Employe who) {
    if (who.getPrivileges() == Privileges.ADMIN)
      this.privileges = privileges;
    return;
  }

}
