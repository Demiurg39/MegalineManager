package org.control;

public class Manager {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new Manager().getGreeting());
    }
}
