package org.megaline.core.util;

import java.util.Random;

public class DHCP {

    private static final Random RANDOM = new Random();

    public static String generateIPAddress() {
        StringBuilder ipAddress = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            ipAddress.append(RANDOM.nextInt(256));
            if (i < 3) {
                ipAddress.append(".");
            }
        }
        return ipAddress.toString();
   }
}
