package org.megaline.tui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SwingMegaTUI extends JFrame {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField phoneNumberField;
    private JPasswordField passwordField;
    private JButton[] menuButtons;
    private int selectedButtonIndex = -1;
    private List<Ticket> activeTickets = new ArrayList<>();

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

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel surnameLabel = new JLabel("Surname:");
        surnameField = new JTextField();
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        mainPanel.add(megaLinePanel);
        mainPanel.add(new JLabel());
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(surnameLabel);
        mainPanel.add(surnameField);
        mainPanel.add(phoneNumberLabel);
        mainPanel.add(phoneNumberField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        phoneNumberField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = phoneNumberField.getText();
                if (phoneNumber.matches("0[0-9]{9}")) {
                    passwordField.requestFocusInWindow();
                } else {
                    JOptionPane.showMessageDialog(SwingMegaTUI.this, "Please enter a valid phone number starting with 0 and containing 10 digits.", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                    phoneNumberField.setText("");
                    phoneNumberField.requestFocusInWindow();
                }
            }
        });

        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surnameField.requestFocusInWindow();
            }
        });

        surnameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                phoneNumberField.requestFocusInWindow();
            }
        });

        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                showMenuWindow();
            }
        });

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
        JButton viewTicketsButton = new JButton("View active tickets"); // добавляем кнопку "View active tickets"
        JButton createTicketButton = new JButton("Create a new ticket");
        JButton tariffsAndServicesButton = new JButton("Tariffs and Services");
        JButton faqButton = new JButton("Frequently Asked Questions");
        JButton quitButton = new JButton("Quit");

        menuButtons = new JButton[]{searchByNumberButton, registerButton, viewTicketsButton, createTicketButton, tariffsAndServicesButton, faqButton, quitButton};

        for (int i = 0; i < menuButtons.length; i++) {
            menuButtons[i].addMouseListener(new ButtonMouseListener(i));
            menuButtons[i].setBackground(Color.CYAN);
            menuPanel.add(menuButtons[i]);
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

        viewTicketsButton.addActionListener(new ActionListener() { // добавляем слушатель для кнопки "View active tickets"
            @Override
            public void actionPerformed(ActionEvent e) {
                showActiveTicketsWindow();
            }
        });

        menuFrame.add(menuPanel);
        menuFrame.setVisible(true);
    }


    private void showCreateTicketWindow() {
        JFrame createTicketFrame = new JFrame("Create Ticket");
        createTicketFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createTicketFrame.setSize(400, 300);
        createTicketFrame.setLocationRelativeTo(null);

        JPanel createTicketPanel = new JPanel();
        createTicketPanel.setLayout(new GridLayout(4, 2));
        createTicketPanel.setBackground(Color.YELLOW);

        JLabel phoneNumberLabel = new JLabel("Client Phone Number:");
        JTextField phoneNumberField = new JTextField();
        JLabel questionLabel = new JLabel("Question:");
        JTextField questionField = new JTextField();

        JButton addButton = new JButton("Add");
        addButton.setBackground(Color.CYAN);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = phoneNumberField.getText();
                String question = questionField.getText();

                activeTickets.add(new Ticket(phoneNumber, question));

                JOptionPane.showMessageDialog(createTicketFrame, "Ticket created successfully!");

                createTicketFrame.dispose();
            }
        });

        createTicketPanel.add(phoneNumberLabel);
        createTicketPanel.add(phoneNumberField);
        createTicketPanel.add(questionLabel);
        createTicketPanel.add(questionField);
        createTicketPanel.add(new JLabel());
        createTicketPanel.add(addButton);

        createTicketFrame.add(createTicketPanel);
        createTicketFrame.setVisible(true);
    }

    private void showSearchByNumberWindow() {
        JFrame searchByNumberFrame = new JFrame("Search by Number");
        searchByNumberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchByNumberFrame.setSize(400, 150);
        searchByNumberFrame.setLocationRelativeTo(null);

        JPanel searchByNumberPanel = new JPanel();
        searchByNumberPanel.setLayout(new GridLayout(2, 2));
        searchByNumberPanel.setBackground(Color.YELLOW);

        JLabel phoneNumberLabel = new JLabel("Enter Client Phone Number:");
        JTextField phoneNumberField = new JTextField();
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = phoneNumberField.getText();
                // Здесь будет ваш код для выполнения поиска в базе данных по введенному номеру
                // В данном случае просто выведем сообщение о том, что функционал будет реализован в будущем
                JOptionPane.showMessageDialog(searchByNumberFrame, "Search functionality will be implemented in the future.");
                searchByNumberFrame.dispose();
            }
        });

        searchByNumberPanel.add(phoneNumberLabel);
        searchByNumberPanel.add(phoneNumberField);
        searchByNumberPanel.add(new JLabel()); // Пустая метка для размещения кнопки в следующей строке
        searchByNumberPanel.add(searchButton);

        searchByNumberFrame.add(searchByNumberPanel);
        searchByNumberFrame.setVisible(true);
    }


    private void showRegistrationWindow() {
        JFrame registrationFrame = new JFrame("Registration");
        registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registrationFrame.setSize(400, 300);
        registrationFrame.setLocationRelativeTo(null);

        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(new GridLayout(6, 2));
        registrationPanel.setBackground(Color.YELLOW);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel surnameLabel = new JLabel("Surname:");
        JTextField surnameField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        JLabel idNumberLabel = new JLabel("ID Number:");
        JTextField idNumberField = new JTextField();
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        JTextField phoneNumberField = new JTextField();

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.CYAN);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String address = addressField.getText();
                String idNumber = idNumberField.getText();
                String phoneNumber = phoneNumberField.getText();

                // Здесь можно выполнить дополнительные действия, например, сохранить данные в базе данных или вывести их на консоль

                JOptionPane.showMessageDialog(registrationFrame, "Client registered successfully!");

                registrationFrame.dispose();
            }
        });

        registrationPanel.add(nameLabel);
        registrationPanel.add(nameField);
        registrationPanel.add(surnameLabel);
        registrationPanel.add(surnameField);
        registrationPanel.add(addressLabel);
        registrationPanel.add(addressField);
        registrationPanel.add(idNumberLabel);
        registrationPanel.add(idNumberField);
        registrationPanel.add(phoneNumberLabel);
        registrationPanel.add(phoneNumberField);
        registrationPanel.add(new JLabel()); // Пустая метка для размещения кнопки в следующей строке
        registrationPanel.add(registerButton);

        registrationFrame.add(registrationPanel);
        registrationFrame.setVisible(true);

        // Добавляем слушатель событий на поле "Имя"
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surnameField.requestFocusInWindow(); // Переход на поле "Фамилия"
            }
        });

        // Добавляем слушатель событий на поле "Фамилия"
        surnameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addressField.requestFocusInWindow(); // Переход на поле "Адрес"
            }
        });

        // Добавляем слушатель событий на поле "Адрес"
        addressField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idNumberField.requestFocusInWindow(); // Переход на поле "ID номер"
            }
        });

        // Добавляем слушатель событий на поле "ID номер"
        idNumberField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                phoneNumberField.requestFocusInWindow(); // Переход на поле "Номер телефона"
            }
        });

        // Добавляем слушатель событий на поле "Номер телефона"
        phoneNumberField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerButton.doClick(); // Вызываем нажатие кнопки "Register" для завершения регистрации
            }
        });
    }

    private void showFAQWindow() {
        JTextArea faqTextArea = new JTextArea();
        faqTextArea.setEditable(false);
        faqTextArea.setBackground(Color.YELLOW);

        // Добавим частозадаваемые вопросы и ответы
        faqTextArea.append("Frequently Asked Questions:\n\n");
        faqTextArea.append("Q: How can I check my current tariff plan and balance?\n");
        faqTextArea.append("A: You can check your balance and tariff plan by dialing *XXX#.\n\n");
        faqTextArea.append("Q: How do I activate international roaming?\n");
        faqTextArea.append("A: International roaming can be activated by calling our customer service hotline.\n\n");
        faqTextArea.append("Q: How can I top up my account balance?\n");
        faqTextArea.append("A: You can top up your account balance by visiting our nearest store or using our mobile app.\n\n");
        faqTextArea.append("Q: How do I report a lost or stolen SIM card?\n");
        faqTextArea.append("A: You should immediately contact our customer service hotline to report a lost or stolen SIM card.\n\n");
        faqTextArea.append("Q: How can I change my current tariff plan?\n");
        faqTextArea.append("A: You can change your tariff plan by logging into your account on our website or by visiting our store.\n\n");
        faqTextArea.append("Q: How do I deactivate additional services?\n");
        faqTextArea.append("A: You can deactivate additional services by sending an SMS with a specific keyword to a specified number.\n\n");
        faqTextArea.append("Q: What should I do if I'm experiencing network issues?\n");
        faqTextArea.append("A: You should check your phone's settings and restart your device. If the issue persists, contact customer support.\n\n");
        faqTextArea.append("Q: How do I activate call forwarding?\n");
        faqTextArea.append("A: Call forwarding can be activated by dialing a specific code followed by the phone number you want to forward calls to.\n\n");
        faqTextArea.append("Q: How can I get help with billing issues?\n");
        faqTextArea.append("A: For assistance with billing issues, you can contact our customer service hotline or visit our website to chat with a representative.\n\n");
        faqTextArea.append("Q: How do I unsubscribe from promotional messages?\n");
        faqTextArea.append("A: You can unsubscribe from promotional messages by replying to the message with a specific keyword or by changing your preferences in your account settings.\n\n");

        JScrollPane scrollPane = new JScrollPane(faqTextArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Frequently Asked Questions", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showActiveTicketsWindow() {
        JFrame activeTicketsFrame = new JFrame("Active Tickets");
        activeTicketsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        activeTicketsFrame.setSize(400, 300);
        activeTicketsFrame.setLocationRelativeTo(null);

        JPanel activeTicketsPanel = new JPanel();
        activeTicketsPanel.setLayout(new GridLayout(activeTickets.size() + 1, 2));
        activeTicketsPanel.setBackground(Color.YELLOW);

        JLabel phoneNumberLabel = new JLabel("Client Phone Number");
        JLabel questionLabel = new JLabel("Question");
        activeTicketsPanel.add(phoneNumberLabel);
        activeTicketsPanel.add(questionLabel);

        for (Ticket ticket : activeTickets) {
            JLabel phoneNumberValue = new JLabel(ticket.getPhoneNumber());
            JLabel questionValue = new JLabel(ticket.getQuestion());
            activeTicketsPanel.add(phoneNumberValue);
            activeTicketsPanel.add(questionValue);
        }

        JScrollPane scrollPane = new JScrollPane(activeTicketsPanel);
        activeTicketsFrame.add(scrollPane);
        activeTicketsFrame.setVisible(true);
    }

    private void showTariffsAndServicesWindow() {
        JFrame tariffsServicesFrame = new JFrame("Tariffs and Services");
        tariffsServicesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tariffsServicesFrame.setSize(400, 200);
        tariffsServicesFrame.setLocationRelativeTo(null);
        tariffsServicesFrame.getContentPane().setBackground(Color.YELLOW);

        JPanel tariffsServicesPanel = new JPanel();
        tariffsServicesPanel.setLayout(new GridLayout(2, 1));
        tariffsServicesPanel.setBackground(Color.YELLOW);

        JButton tariffsButton = new JButton("Tariffs");
        JButton servicesButton = new JButton("Services");

        tariffsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTariffsWindow();
            }
        });

        servicesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showServicesWindow();
            }
        });

        tariffsServicesPanel.add(tariffsButton);
        tariffsServicesPanel.add(servicesButton);

        tariffsServicesFrame.add(tariffsServicesPanel);
        tariffsServicesFrame.setVisible(true);
    }

    private void showTariffsWindow() {
        JTextArea tariffsTextArea = new JTextArea();
        tariffsTextArea.setEditable(false);
        tariffsTextArea.setBackground(Color.YELLOW);

        // Add available tariffs
        tariffsTextArea.append("Available Tariffs:\n\n");
        tariffsTextArea.append("1. MegaPlan - $30/month\n");
        tariffsTextArea.append("   - Unlimited calls\n");
        tariffsTextArea.append("   - 10GB data\n");
        tariffsTextArea.append("   - Unlimited texts\n\n");
        tariffsTextArea.append("2. UltraPlan - $50/month\n");
        tariffsTextArea.append("   - Unlimited calls\n");
        tariffsTextArea.append("   - 30GB data\n");
        tariffsTextArea.append("   - Unlimited texts\n\n");
        tariffsTextArea.append("3. FamilyPlan - $80/month\n");
        tariffsTextArea.append("   - Unlimited calls within family\n");
        tariffsTextArea.append("   - 50GB shared data\n");
        tariffsTextArea.append("   - Unlimited texts\n\n");

        JScrollPane scrollPane = new JScrollPane(tariffsTextArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(null, scrollPane, "Available Tariffs", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showServicesWindow() {
        JTextArea servicesTextArea = new JTextArea();
        servicesTextArea.setEditable(false);
        servicesTextArea.setBackground(Color.YELLOW);

        // Add available services
        servicesTextArea.append("Available Services:\n\n");
        servicesTextArea.append("1. International Roaming\n");
        servicesTextArea.append("2. Call Forwarding\n");
        servicesTextArea.append("3. Voicemail\n");
        servicesTextArea.append("4. Data Packs\n");
        servicesTextArea.append("5. International Calling Packs\n");
        servicesTextArea.append("6. Device Insurance\n");
        servicesTextArea.append("7. Music Streaming\n");
        servicesTextArea.append("8. Movie Streaming\n");
        servicesTextArea.append("9. Gaming Subscription\n");

        JScrollPane scrollPane = new JScrollPane(servicesTextArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(null, scrollPane, "Available Services", JOptionPane.INFORMATION_MESSAGE);
    }

    private class ButtonMouseListener extends MouseAdapter {
        private int buttonIndex;

        public ButtonMouseListener(int buttonIndex) {
            this.buttonIndex = buttonIndex;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            selectedButtonIndex = buttonIndex;
            updateButtonStyles();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            selectedButtonIndex = -1;
            updateButtonStyles();
        }
    }

    private void updateButtonStyles() {
        for (int i = 0; i < menuButtons.length; i++) {
            if (i == selectedButtonIndex) {
                menuButtons[i].setForeground(Color.BLUE);
            } else {
                menuButtons[i].setForeground(Color.BLACK);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SwingMegaTUI().setVisible(true);
            }
        });
    }
}

class Ticket {
    private String phoneNumber;
    private String question;

    public Ticket(String phoneNumber, String question) {
        this.phoneNumber = phoneNumber;
        this.question = question;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getQuestion() {
        return question;
    }
}
