package org.megaline.core.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static String getHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean passwordCheck(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}