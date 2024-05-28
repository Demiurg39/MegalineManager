package org.megaline.tui;

import org.megaline.core.dao.EmployeeDao;
import org.megaline.core.models.Employee;
import org.megaline.core.util.PasswordHasher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingMegaTUI extends JFrame {

    private JTextField nameField;
    private JPasswordField passwordField;

    public SwingMegaTUI() {
        setTitle("MegaLine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.YELLOW);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2));
        mainPanel.setBackground(Color.YELLOW);

        JPanel megaLinePanel = new JPanel();
        megaLinePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        megaLinePanel.setBackground(Color.YELLOW);

        JLabel megaLabel = new JLabel("Mega");
        megaLabel.setFont(new Font(megaLabel.getFont().getName(), Font.PLAIN, megaLabel.getFont().getSize() * 2));
        megaLabel.setForeground(Color.BLUE);

        JLabel lineLabel = new JLabel("Line");
        lineLabel.setFont(new Font(lineLabel.getFont().getName(), Font.PLAIN, lineLabel.getFont().getSize() * 2));
        lineLabel.setForeground(Color.ORANGE);

        megaLinePanel.add(megaLabel);
        megaLinePanel.add(lineLabel);

        EmployeeDao employeeDao = new EmployeeDao();

        JLabel nameLabel = new JLabel("Employee id:");
        nameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        mainPanel.add(megaLinePanel);
        mainPanel.add(new JLabel());
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        ActionListener loginListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long employeeId = Long.parseLong(nameField.getText());
                    String password = new String(passwordField.getPassword());

                    Employee employee = employeeDao.findById(employeeId);

                    if (employee == null) {
                        JOptionPane.showMessageDialog(SwingMegaTUI.this, "Invalid ID, please enter a valid ID", "Invalid employee ID", JOptionPane.ERROR_MESSAGE);
                        nameField.setText("");
                        nameField.requestFocusInWindow();
                    } else {
                        if (PasswordHasher.passwordCheck(password, employee.getPasswordHash())) {
                            JOptionPane.showMessageDialog(SwingMegaTUI.this, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            showMenuWindow();
                        } else {
                            JOptionPane.showMessageDialog(SwingMegaTUI.this, "Invalid password, please try again", "Invalid password", JOptionPane.ERROR_MESSAGE);
                            passwordField.setText("");
                            passwordField.requestFocusInWindow();
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SwingMegaTUI.this, "Invalid ID format, please enter a valid ID", "Invalid employee ID", JOptionPane.ERROR_MESSAGE);
                    nameField.setText("");
                    nameField.requestFocusInWindow();
                }
            }
        };

        passwordField.addActionListener(loginListener);
        nameField.addActionListener(loginListener);

        add(mainPanel);
    }

    private void showMenuWindow() {
        JFrame menuFrame = new JFrame("Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(300, 200);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.getContentPane().setBackground(Color.YELLOW);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(8, 1));
        menuPanel.setBackground(Color.YELLOW);

        JButton searchByNumberButton = new JButton("Search for a client by number");
        JButton registerButton = new JButton("Register a new client");
        JButton viewTicketsButton = new JButton("View active tickets");
        JButton createTicketButton = new JButton("Create a new ticket");
        JButton tariffsAndServicesButton = new JButton("Tariffs and Services");
        JButton faqButton = new JButton("Frequently Asked Questions");
        JButton quitButton = new JButton("Quit");

        JButton[] menuButtons = {searchByNumberButton, registerButton, viewTicketsButton, createTicketButton, tariffsAndServicesButton, faqButton, quitButton};

        for (JButton menuButton : menuButtons) {
            menuButton.setBackground(Color.CYAN);
            menuPanel.add(menuButton);
        }

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegistrationWindow();
            }
        });

        faqButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFAQWindow();
            }
        });

        searchByNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchByNumberWindow();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        createTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateTicketWindow();
            }
        });

        tariffsAndServicesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTariffsAndServicesWindow();
            }
        });

        viewTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showActiveTicketsWindow();
            }
        });

        menuFrame.add(menuPanel);
        menuFrame.setVisible(true);
    }

    private void showRegistrationWindow() {
        JFrame registrationFrame = new JFrame("Register a New Client");
        registrationFrame.setSize(400, 300);
        registrationFrame.setLocationRelativeTo(null);
        registrationFrame.getContentPane().setBackground(Color.YELLOW);

        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(new GridLayout(5, 2));
        registrationPanel.setBackground(Color.YELLOW);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        JLabel passportLabel = new JLabel("Passport ID:");
        JTextField passportField = new JTextField();
        JButton registerButton = new JButton("Register");

        registrationPanel.add(nameLabel);
        registrationPanel.add(nameField);
        registrationPanel.add(addressLabel);
        registrationPanel.add(addressField);
        registrationPanel.add(passportLabel);
        registrationPanel.add(passportField);
        registrationPanel.add(new JLabel()); // Empty cell
        registrationPanel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String address = addressField.getText();
                String passportId = passportField.getText();
                // Implement client registration logic here

                JOptionPane.showMessageDialog(registrationFrame, "Client registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                registrationFrame.dispose();
            }
        });

        registrationFrame.add(registrationPanel);
        registrationFrame.setVisible(true);
    }

    private void showFAQWindow() {
        JFrame faqFrame = new JFrame("Frequently Asked Questions");
        faqFrame.setSize(400, 300);
        faqFrame.setLocationRelativeTo(null);
        faqFrame.getContentPane().setBackground(Color.YELLOW);

        JTextArea faqArea = new JTextArea("1. How to register a new client?\n\n2. How to create a new ticket?\n\n3. How to view active tickets?");
        faqArea.setEditable(false);
        faqArea.setBackground(Color.YELLOW);

        faqFrame.add(new JScrollPane(faqArea));
        faqFrame.setVisible(true);
    }

    private void showSearchByNumberWindow() {
        JFrame searchFrame = new JFrame("Search for a Client by Number");
        searchFrame.setSize(400, 200);
        searchFrame.setLocationRelativeTo(null);
        searchFrame.getContentPane().setBackground(Color.YELLOW);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(3, 2));
        searchPanel.setBackground(Color.YELLOW);

        JLabel numberLabel = new JLabel("Client Number:");
        JTextField numberField = new JTextField();
        JButton searchButton = new JButton("Search");

        searchPanel.add(numberLabel);
        searchPanel.add(numberField);
        searchPanel.add(new JLabel()); // Empty cell
        searchPanel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientNumber = numberField.getText();
                // Implement search logic here

                JOptionPane.showMessageDialog(searchFrame, "Client found: " + clientNumber, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                searchFrame.dispose();
            }
        });

        searchFrame.add(searchPanel);
        searchFrame.setVisible(true);
    }

    private void showCreateTicketWindow() {
        JFrame createTicketFrame = new JFrame("Create a New Ticket");
        createTicketFrame.setSize(400, 300);
        createTicketFrame.setLocationRelativeTo(null);
        createTicketFrame.getContentPane().setBackground(Color.YELLOW);

        JPanel createTicketPanel = new JPanel();
        createTicketPanel.setLayout(new GridLayout(5, 2));
        createTicketPanel.setBackground(Color.YELLOW);

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        JTextArea descriptionArea = new JTextArea();
        JButton createButton = new JButton("Create");

        createTicketPanel.add(titleLabel);
        createTicketPanel.add(titleField);
        createTicketPanel.add(descriptionLabel);
        createTicketPanel.add(new JScrollPane(descriptionArea));
        createTicketPanel.add(new JLabel()); // Empty cell
        createTicketPanel.add(createButton);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionArea.getText();
                // Implement ticket creation logic here

                JOptionPane.showMessageDialog(createTicketFrame, "Ticket created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                createTicketFrame.dispose();
            }
        });

        createTicketFrame.add(createTicketPanel);
        createTicketFrame.setVisible(true);
    }

    private void showTariffsAndServicesWindow() {
        JFrame tariffsFrame = new JFrame("Tariffs and Services");
        tariffsFrame.setSize(400, 300);
        tariffsFrame.setLocationRelativeTo(null);
        tariffsFrame.getContentPane().setBackground(Color.YELLOW);

        JTextArea tariffsArea = new JTextArea("Tariff 1: ...\n\nTariff 2: ...\n\nService 1: ...\n\nService 2: ...");
        tariffsArea.setEditable(false);
        tariffsArea.setBackground(Color.YELLOW);

        tariffsFrame.add(new JScrollPane(tariffsArea));
        tariffsFrame.setVisible(true);
    }

    private void showActiveTicketsWindow() {
        JFrame activeTicketsFrame = new JFrame("Active Tickets");
        activeTicketsFrame.setSize(400, 300);
        activeTicketsFrame.setLocationRelativeTo(null);
        activeTicketsFrame.getContentPane().setBackground(Color.YELLOW);

        JTextArea activeTicketsArea = new JTextArea("Ticket 1: ...\n\nTicket 2: ...\n\nTicket 3: ...");
        activeTicketsArea.setEditable(false);
        activeTicketsArea.setBackground(Color.YELLOW);

        activeTicketsFrame.add(new JScrollPane(activeTicketsArea));
        activeTicketsFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingMegaTUI frame = new SwingMegaTUI();
            frame.setVisible(true);
        });
    }
}