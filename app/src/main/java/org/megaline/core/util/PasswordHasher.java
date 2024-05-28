package org.megaline.core.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static String getHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean passwordCheck(String hash, String password) {
        return hash.equals(getHash(password)) ? true : false;
    }
}