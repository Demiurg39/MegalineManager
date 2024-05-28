package org.megaline.core.util;

public class dhcp {
    import java.util.Random;

    public class IPAddressGenerator {

        private static final Random RANDOM = new Random();

        // Генерация случайного IP-адреса в формате "xxx.xxx.xxx.xxx"
        public static String generateIPAddress() {
            StringBuilder ipAddress = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                ipAddress.append(RANDOM.nextInt(256)); // Генерируем случайное число от 0 до 255
                if (i < 3) {
                    ipAddress.append(".");
                }
            }
            return ipAddress.toString();
        }

        // Пример использования
        public static void main(String[] args) {
            String ipAddress = generateIPAddress();
            System.out.println("Random IP Address: " + ipAddress);
        }
    }

}
