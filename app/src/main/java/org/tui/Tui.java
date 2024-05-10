package org.tui;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Tui {
    public static void main(String[] args) {
        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            Screen screen = new TerminalScreen(terminal);

            TextGraphics tg = screen.newTextGraphics();

            screen.startScreen();

            // Screen dimensions
            TerminalSize screenSize = screen.getTerminalSize();
            int screenWidth = screenSize.getColumns();
            int screenHeight = screenSize.getRows();

            // Top-left corner position of the rectangle
            int rectangleX = screenWidth / 8;
            int rectangleY = screenHeight / 8;

            // Rectangle dimensions
            int rectangleWidth = screenWidth * 3 / 4;
            int rectangleHeight = screenHeight * 3 / 4;

            boolean exitRequested = false; // Флаг для выхода из программы

            mainLoop: do {
                // Clear the screen
                screen.clear();

                drawMegalineHeader(screen, screenWidth);

                // Draw the rectangle around the form
                drawRectangle(screen, rectangleX, rectangleY, rectangleWidth, rectangleHeight, '.', TextColor.ANSI.YELLOW_BRIGHT, TextColor.ANSI.YELLOW_BRIGHT);

                // Text input positions
                int inputX = rectangleX + 2;
                int inputY = rectangleY + 2;

                // Request for name
                tg.setForegroundColor(TextColor.ANSI.CYAN);
                tg.putString(inputX, inputY, "Enter your name:  ", SGR.BOLD);
                screen.refresh();
                String name = readInput(screen, inputX + 17, inputY);

                // Request for surname
                tg.putString(inputX, inputY + 2, "Enter your surname:  ", SGR.BOLD);
                screen.refresh();
                String surname = readInput(screen, inputX + 20, inputY + 2);

                // Request for phone number
                tg.putString(inputX, inputY + 4, "Enter your phone number:  ", SGR.BOLD);
                screen.refresh();
                String phoneNumber = readPhoneNumber(screen, inputX + 26, inputY + 4);

                // Clear the screen for the menu
                screen.clear();

                boolean showMenu = true; // Variable to track if menu should be shown

                // Wait for user input in the menu
                int menuOption;
                do {
                    if (showMenu) {
                        drawMenu(screen, inputX, inputY);
                    }

                    menuOption = readMenuOption(screen);
                    showMenu = false; // Hide menu after choosing an option
                } while (menuOption < 1 || menuOption > 6);

                // Handle the selected menu option
                switch (menuOption) {
                    case 1:
                        boolean returnToMainMenu = false;
                        do {
                            // Search for a client by phone number
                            screen.clear(); // Clear the screen
                            drawRectangle(screen, rectangleX, rectangleY, rectangleWidth, rectangleHeight, '.', TextColor.ANSI.YELLOW_BRIGHT, TextColor.ANSI.YELLOW_BRIGHT);
                            tg.putString(inputX, inputY, "Enter phone number:", SGR.BOLD);
                            screen.refresh();
                            phoneNumber = readPhoneNumber(screen, inputX + 21, inputY); // Read phone number
                            // You can add your logic here to check the phone number in the database
                            tg.putString(inputX, inputY + 2, "Press Enter to return to the main menu.", SGR.BOLD);
                            screen.refresh();
                            KeyStroke enterStroke;
                            while ((enterStroke = screen.readInput()).getKeyType() != KeyType.Enter) ;
                            returnToMainMenu = true;
                        } while (!returnToMainMenu);
                        continue mainLoop; // возвращаемся к началу главного цикла
                    case 2:
                        // Register a new client
                        screen.clear(); // Clear the screen
                        drawRectangle(screen, rectangleX, rectangleY, rectangleWidth, rectangleHeight, '.', TextColor.ANSI.YELLOW_BRIGHT, TextColor.ANSI.YELLOW_BRIGHT);
                        registerNewClient(screen, inputX, inputY);
                        break; // возвращаемся к меню выбора действий
                    case 3:
                        // View active tickets
                        screen.clear(); // Clear the screen
                        drawRectangle(screen, rectangleX, rectangleY, rectangleWidth, rectangleHeight, '.', TextColor.ANSI.YELLOW_BRIGHT, TextColor.ANSI.YELLOW_BRIGHT);
                        viewActiveTickets(screen, inputX, inputY);
                        break; // возвращаемся к меню выбора действий
                    case 4:
                        // Create a new ticket
                        screen.clear(); // Clear the screen
                        drawRectangle(screen, rectangleX, rectangleY, rectangleWidth, rectangleHeight, '.', TextColor.ANSI.YELLOW_BRIGHT, TextColor.ANSI.YELLOW_BRIGHT);
                        createNewTicket(screen, inputX, inputY);
                        break; // возвращаемся к меню выбора действий
                    case 5:
                        // Frequently Asked Questions
                        screen.clear(); // Clear the screen
                        drawRectangle(screen, rectangleX, rectangleY, rectangleWidth, rectangleHeight, '.', TextColor.ANSI.YELLOW_BRIGHT, TextColor.ANSI.YELLOW_BRIGHT);
                        frequentlyAskedQuestions(screen, inputX, inputY);
                        break; // возвращаемся к меню выбора действий
                    case 6:
                        // Quit
                        exitRequested = true;
                        break; // выходим из switch и цикла
                }
            } while (!exitRequested);

            // Wait for user to press Enter before closing the application
            screen.readInput();
            screen.stopScreen();
            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void drawMegalineHeader(Screen screen, int screenWidth) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(screenWidth / 2 - 4, 1, "Megaline", SGR.BOLD);
    }

    private static void drawRectangle(Screen screen, int x, int y, int width, int height, char symbol, TextColor foregroundColor, TextColor backgroundColor) {
        TextGraphics tg = screen.newTextGraphics();
        for (int row = y; row < y + height; row++) {
            for (int col = x; col < x + width; col++) {
                if (row == y || row == y + height - 1 || col == x || col == x + width - 1) {
                    screen.setCharacter(col, row, new TextCharacter(symbol, foregroundColor, backgroundColor));
                }
            }
        }
    }

    private static String readInput(Screen screen, int startX, int startY) throws IOException {
        StringBuilder sb = new StringBuilder();
        TextGraphics tg = screen.newTextGraphics();

        // Set cursor position
        tg.putString(new TerminalPosition(startX, startY), "");

        screen.refresh();

        KeyStroke keyStroke;
        while (true) {
            keyStroke = screen.readInput();
            if (keyStroke != null) {
                if (keyStroke.getKeyType() == KeyType.Enter) {
                    screen.setCursorPosition(new TerminalPosition(startX, startY + 1));
                    screen.refresh();
                    break;
                } else if (keyStroke.getKeyType() == KeyType.Backspace && sb.length() > 0) {
                    // Delete the last character on Backspace
                    sb.deleteCharAt(sb.length() - 1);

                    // Refresh the screen after deleting the character
                    tg.putString(new TerminalPosition(startX, startY), sb.toString() + " "); // Add space to clear the character
                    screen.refresh();
                } else if (keyStroke.getKeyType() == KeyType.Character) {
                    // Add the character to the string
                    sb.append(keyStroke.getCharacter());

                    // Display the entered text
                    tg.putString(new TerminalPosition(startX, startY), sb.toString());
                    screen.refresh();
                }
            }
        }
        return sb.toString();
    }

    private static String readPhoneNumber(Screen screen, int startX, int startY) throws IOException {
        StringBuilder sb = new StringBuilder();
        TextGraphics tg = screen.newTextGraphics();

        // Set cursor position
        tg.putString(new TerminalPosition(startX, startY), "");

        screen.refresh();

        KeyStroke keyStroke;
        while (true) {
            keyStroke = screen.readInput();
            if (keyStroke != null) {
                if (keyStroke.getKeyType() == KeyType.Enter) {
                    screen.setCursorPosition(new TerminalPosition(startX, startY + 1));
                    screen.refresh();
                    break;
                } else if (keyStroke.getKeyType() == KeyType.Backspace && sb.length() > 0) {
                    // Delete the last character on Backspace
                    sb.deleteCharAt(sb.length() - 1);

                    // Refresh the screen after deleting the character
                    tg.putString(new TerminalPosition(startX + sb.length(), startY), " "); // Overwrite with space to clear the character
                    screen.refresh();
                } else if (keyStroke.getKeyType() == KeyType.Character && Character.isDigit(keyStroke.getCharacter())) {
                    // If the entered key is a digit, append it to the string
                    char digit = keyStroke.getCharacter();

                    // Check for the maximum length of the phone number and the condition that the first digit should be 0
                    if (sb.length() < 10 && (sb.length() == 0 && digit == '0' || sb.length() > 0)) {
                        sb.append(digit);

                        // Display the entered text
                        tg.putString(new TerminalPosition(startX + sb.length() - 1, startY), String.valueOf(digit)); // Update X position dynamically
                        screen.refresh();
                    }
                }
            }
        }
        return sb.toString();
    }

    private static void registerNewClient(Screen screen, int startX, int startY) throws IOException {
        TextGraphics tg = screen.newTextGraphics();

        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(startX, startY, "Register a new client", SGR.BOLD);
        tg.putString(startX, startY + 2, "Enter the client's name: ", SGR.BOLD);
        String name = readInput(screen, startX + 25, startY + 2);
        tg.putString(startX, startY + 4, "Enter the client's surname: ", SGR.BOLD);
        String surname = readInput(screen, startX + 29, startY + 4);
        tg.putString(startX, startY + 6, "Enter the client's address: ", SGR.BOLD);
        String address = readInput(screen, startX + 29, startY + 6);
        tg.putString(startX, startY + 8, "Enter the client's email: ", SGR.BOLD);
        String email = readInput(screen, startX + 28, startY + 8);
        String phoneNumber;
        boolean validPhoneNumber = false;
        while (!validPhoneNumber) {
            tg.putString(startX, startY + 10, "Enter the client's phone number : ", SGR.BOLD);
            phoneNumber = readPhoneNumber(screen, startX + 35, startY + 10);
            // Check that the phone number consists of 10 digits and starts with 0
            if (phoneNumber.matches("0\\d{9}")) {
                validPhoneNumber = true;
            } else {
                tg.putString(startX, startY + 12, "The phone number must start with 0 and consist of 10 digits. Please try again.", SGR.BOLD);
            }
        }
        // Logic for saving the new client to the database
        tg.putString(startX, startY + 12, "Client successfully registered.", SGR.BOLD);
        screen.refresh();
        // Wait for user input to return to the menu
        tg.putString(startX, startY + 14, "Press Enter to return to the main menu.", SGR.BOLD);
        screen.refresh();
        KeyStroke enterStroke;
        while ((enterStroke = screen.readInput()).getKeyType() != KeyType.Enter) ;
    }

    private static void viewActiveTickets(Screen screen, int startX, int startY) throws IOException {
        TextGraphics tg = screen.newTextGraphics();

        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(startX, startY, "View active tickets", SGR.BOLD);
        // Logic to fetch and display active tickets
        // Placeholder
        tg.putString(startX, startY + 2, "No active tickets found.", SGR.BOLD);
        screen.refresh();
        // Wait for user input to return to the menu
        tg.putString(startX, startY + 4, "Press Enter to return to the main menu.", SGR.BOLD);
        screen.refresh();
        KeyStroke enterStroke;
        while ((enterStroke = screen.readInput()).getKeyType() != KeyType.Enter) ;
    }

    private static void createNewTicket(Screen screen, int startX, int startY) throws IOException {
        TextGraphics tg = screen.newTextGraphics();

        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(startX, startY, "Create a new ticket", SGR.BOLD);
        tg.putString(startX, startY + 2, "Enter the description of the ticket: ", SGR.BOLD);
        String description = readInput(screen, startX + 34, startY + 2);
        // Logic for creating a new ticket
        tg.putString(startX, startY + 4, "Ticket created successfully.", SGR.BOLD);
        screen.refresh();
        // Wait for user input to return to the menu
        tg.putString(startX, startY + 6, "Press Enter to return to the main menu.", SGR.BOLD);
        screen.refresh();
        KeyStroke enterStroke;
        while ((enterStroke = screen.readInput()).getKeyType() != KeyType.Enter) ;
    }

    private static void frequentlyAskedQuestions(Screen screen, int startX, int startY) throws IOException {
        TextGraphics tg = screen.newTextGraphics();

        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(startX, startY, "Frequently Asked Questions", SGR.BOLD);
        // Display FAQ
        tg.putString(startX, startY + 2, "1. Available tariffs", SGR.BOLD);
        tg.putString(startX, startY + 3, "2. Internet problem", SGR.BOLD);
        tg.putString(startX, startY + 5, "3. Tariff changes", SGR.BOLD);
        tg.putString(startX, startY + 6, "4. Number changes", SGR.BOLD);
        tg.putString(startX, startY + 8, "5. Internet setup", SGR.BOLD);
        tg.putString(startX, startY + 9, "6. Available services", SGR.BOLD);
        screen.refresh();
        // Wait for user input to return to the menu
        tg.putString(startX, startY + 11, "Press Enter to return to the main menu.", SGR.BOLD);
        screen.refresh();
        KeyStroke enterStroke;
        while ((enterStroke = screen.readInput()).getKeyType() != KeyType.Enter) ;
    }

    private static void drawMenu(Screen screen, int inputX, int inputY) throws IOException {
        TextGraphics tg = screen.newTextGraphics();
        // Draw the rectangle around the menu
        int rectangleX = inputX - 2;
        int rectangleY = inputY - 1;
        int rectangleWidth = 40;
        int rectangleHeight = 10;
        drawRectangle(screen, rectangleX, rectangleY, rectangleWidth, rectangleHeight, '.', TextColor.ANSI.YELLOW_BRIGHT, TextColor.ANSI.YELLOW_BRIGHT);

        // Show menu options
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(inputX, inputY, "Menu:", SGR.BOLD);
        tg.putString(inputX, inputY + 1, "1. Search for a client by number", SGR.BOLD);
        tg.putString(inputX, inputY + 2, "2. Register a new client", SGR.BOLD);
        tg.putString(inputX, inputY + 3, "3. View active tickets", SGR.BOLD);
        tg.putString(inputX, inputY + 4, "4. Create a new ticket", SGR.BOLD);
        tg.putString(inputX, inputY + 5, "5. Frequently Asked Questions", SGR.BOLD);
        tg.putString(inputX, inputY + 6, "6. Quit", SGR.BOLD);
        screen.refresh();
    }

    private static int readMenuOption(Screen screen) throws IOException {
        KeyStroke keyStroke;
        int menuOption;
        do {
            keyStroke = screen.readInput();
            menuOption = -1; // Initialize with an invalid option
            if (keyStroke != null && keyStroke.getKeyType() == KeyType.Character && Character.isDigit(keyStroke.getCharacter())) {
                menuOption = Character.getNumericValue(keyStroke.getCharacter());
            }
        } while (menuOption < 1 || menuOption > 6);
        return menuOption;
    }
}