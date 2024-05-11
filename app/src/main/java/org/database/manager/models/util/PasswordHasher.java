package org.database.manager.models.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

  public static String getHash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }
}


