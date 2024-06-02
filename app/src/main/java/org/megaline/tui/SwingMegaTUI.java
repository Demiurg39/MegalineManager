package org.megaline.tui;

import org.megaline.core.dao.*;
import org.megaline.core.models.*;

import java.awt.event.*;
import java.util.List;

import org.megaline.core.util.PasswordHasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class SwingMegaTUI extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwingMegaTUI.class);
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton[] menuButtons;
    private EmployeeDao employeeDao = new EmployeeDao();
    private UserDao userDao = new UserDao();
    private TariffPlanDao tariffPlanDao = new TariffPlanDao();
    private ConnectionDao connectionDao = new ConnectionDao();
    private QuestionDao questionDao = new QuestionDao();
    private TicketDao ticketDao = new TicketDao();

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
                    LOGGER.info("Working with employee id {}", employeeId);

                    Employee employee = employeeDao.findById(employeeId);

                    if (employee == null) {
                        JOptionPane.showMessageDialog(SwingMegaTUI.this, "Invalid ID, please enter a valid ID", "Invalid employee ID", JOptionPane.ERROR_MESSAGE);
                        nameField.setText("");
                        nameField.requestFocusInWindow();
                    } else {
                        LOGGER.info("Working with password {}", password);
                        LOGGER.debug("Employee id 1 hash : {}", employee.getPasswordHash());
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
        menuFrame.setSize(600, 480);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.getContentPane().setBackground(Color.YELLOW);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(8, 1));
        menuPanel.setBackground(Color.YELLOW);

        JButton searchUserButton = new JButton("Search for a client"); // here will open a new window and will suggested search for name, id, address or passport id
        JButton registerButton = new JButton("Register a new client"); // There will be open a form for creating and submitting a new client, also will be offered to create connection, if chosen then also in form will appear tariff plan
        JButton manageConnectionsButton = new JButton("Manage user connection"); // There will be like menu where you can type clien id and it provides you another window where you can manage connection, there will be like change internet speed, change tariffplan change connection state
        JButton viewTicketsButton = new JButton("View active tickets"); // добавляем кнопку "View active tickets"
        JButton createTicketButton = new JButton("Create a new ticket");
        JButton tariffsAndServicesButton = new JButton("Tariffs listener"); // there will be like listening from tariff plans table all records
        JButton faqButton = new JButton("Frequently Asked Questions"); // will open a window with search and we can put the question and it will search across faq table in db (need to create faq model and table)
        JButton quitButton = new JButton("Quit");

        menuButtons = new JButton[]{searchUserButton, registerButton, manageConnectionsButton, viewTicketsButton, createTicketButton, tariffsAndServicesButton, faqButton, quitButton};

        for (JButton menuButton : menuButtons) {
            menuButton.setBackground(Color.CYAN);
            menuPanel.add(menuButton);
        }

        searchUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame searchFrame = new JFrame("Search for a Client");
                searchFrame.setSize(600, 400);
                searchFrame.setLocationRelativeTo(null);
                searchFrame.getContentPane().setBackground(Color.YELLOW);

                JPanel searchPanel = new JPanel();
                searchPanel.setLayout(new GridLayout(5, 2));
                searchPanel.setBackground(Color.YELLOW);

                JLabel idLabel = new JLabel(" ID:");
                JTextField idField = new JTextField();
                JLabel passportLabel = new JLabel(" Passport ID:");
                JTextField passportField = new JTextField();
                JButton searchButton = new JButton("Search");

                searchPanel.add(idLabel);
                searchPanel.add(idField);
                searchPanel.add(passportLabel);
                searchPanel.add(passportField);
                searchPanel.add(new JLabel()); // Empty cell
                searchPanel.add(searchButton);

                searchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String idText = idField.getText();
                        String passportId = passportField.getText();

                        if (idText.isEmpty() && passportId.isEmpty()) {
                            JOptionPane.showMessageDialog(SwingMegaTUI.this, "ID or passport ID cannot be empty, please enter valid data", "Invalid data", JOptionPane.ERROR_MESSAGE);
                            idField.requestFocusInWindow();
                        } else {
                            try {
                                int id = idText.isEmpty() ? 0 : Integer.parseInt(idText);
                                User user = null;
                                if (id != 0) {
                                    user = userDao.findById(id);
                                    idField.setText("");
                                } else {
                                    user = userDao.findByPassport(passportId);
                                    passportField.setText("");
                                }
                                if (user != null) {
                                    JOptionPane.showMessageDialog(searchFrame, "Search results:\n" + user.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(searchFrame, "No user found with the provided information", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(SwingMegaTUI.this, "Invalid ID format, please enter a valid integer", "Invalid data", JOptionPane.ERROR_MESSAGE);
                                idField.setText("");
                                idField.requestFocusInWindow();
                            }
                        }
                    }
                });

                searchFrame.add(searchPanel);
                searchFrame.setVisible(true);
            }
        });



        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame registrationFrame = new JFrame("Register a New Client");
                registrationFrame.setSize(600, 400);
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
                JCheckBox createConnectionCheckBox = new JCheckBox("Create Connection");
                JLabel tariffPlanLabel = new JLabel("Tariff Plan:");
                JComboBox<String> tariffPlanComboBox = new JComboBox<>();

                // Fetch tariff plans from the database
                List<TariffPlan> tariffPlans = tariffPlanDao.findAll();

                for (TariffPlan plan : tariffPlans) {
                    tariffPlanComboBox.addItem(plan.getName());
                }

                tariffPlanLabel.setVisible(false);
                tariffPlanComboBox.setVisible(false);

                createConnectionCheckBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tariffPlanLabel.setVisible(createConnectionCheckBox.isSelected());
                        tariffPlanComboBox.setVisible(createConnectionCheckBox.isSelected());
                    }
                });

                JButton registerButton = new JButton("Register");

                registrationPanel.add(nameLabel);
                registrationPanel.add(nameField);
                registrationPanel.add(new JLabel()); // Empty cell
                registrationPanel.add(addressLabel);
                registrationPanel.add(addressField);
                registrationPanel.add(new JLabel()); // Empty cell
                registrationPanel.add(passportLabel);
                registrationPanel.add(passportField);
                registrationPanel.add(createConnectionCheckBox);
                registrationPanel.add(tariffPlanLabel);
                registrationPanel.add(tariffPlanComboBox);
                registrationPanel.add(new JLabel()); // Empty cell
                registrationPanel.add(registerButton);

                registerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        String address = addressField.getText();
                        String passportId = passportField.getText();

                        Connection connection = null;
                        User user = new User(name, address, passportId);
                        boolean userSaved = userDao.saveOrUpdate(user);

                        boolean connectionSaved = false;
                        if (createConnectionCheckBox.isSelected()) {
                            Object selectedTariffObject = tariffPlanComboBox.getSelectedItem();
                            TariffPlan selectedTariff =  tariffPlanDao.findByName((String) selectedTariffObject);

                            if (selectedTariff != null) {
                                connection = new Connection(user, selectedTariff);
                                connectionSaved = connectionDao.saveOrUpdate(connection);
                            } else {
                                LOGGER.error("Failed to get tariff plan");
                            }
                        }

                        if (userSaved && (!createConnectionCheckBox.isSelected() || connectionSaved)) {
                            JOptionPane.showMessageDialog(registrationFrame, "Client registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            registrationFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(registrationFrame, "Failed to register client", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                registrationFrame.add(registrationPanel);
                registrationFrame.setVisible(true);
            }
        });

        manageConnectionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame manageConnectionsFrame = new JFrame("Manage User Connection");
                manageConnectionsFrame.setSize(600, 400);
                manageConnectionsFrame.setLocationRelativeTo(null);
                manageConnectionsFrame.getContentPane().setBackground(Color.YELLOW);

                JPanel manageConnectionsPanel = new JPanel();
                manageConnectionsPanel.setLayout(new GridLayout(4, 2));
                manageConnectionsPanel.setBackground(Color.YELLOW);

                JLabel idLabel = new JLabel("Client ID:");
                JTextField idField = new JTextField();
                JButton searchButton = new JButton("Search");

                manageConnectionsPanel.add(idLabel);
                manageConnectionsPanel.add(idField);
                manageConnectionsPanel.add(new JLabel()); // Empty cell
                manageConnectionsPanel.add(searchButton);

                searchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String idText = idField.getText();

                        if (idText.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "ID cannot be empty, please enter valid data", "Invalid data", JOptionPane.ERROR_MESSAGE);
                            idField.requestFocusInWindow();
                        } else {
                            try {
                                int id = Integer.parseInt(idText);
                                User user = userDao.findById(id);
                                if (user != null) {
                                    // Создаем новое окно для управления подключениями
                                    JFrame manageConnectionsWindow = new JFrame("Manage Connections for User: " + user.getName());
                                    manageConnectionsWindow.setSize(600, 400);
                                    manageConnectionsWindow.setLocationRelativeTo(null);
                                    manageConnectionsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                                    Connection[] connectionHolder = new Connection[1]; // Используем массив для хранения connection
                                    connectionHolder[0] = connectionDao.findByUserId(user.getId());
                                    TariffPlan tariff = null;
                                    if (connectionHolder[0] != null && connectionHolder[0].getTariffPlan() != null) {
                                        tariff = tariffPlanDao.findById(connectionHolder[0].getTariffPlan().getId());
                                    }

                                    // Создаем панель для управления подключениями
                                    JPanel manageConnectionsPanel = new JPanel();
                                    manageConnectionsPanel.setLayout(new GridLayout(4, 2));

                                    // Добавляем компоненты для управления подключениями
                                    JLabel internetSpeedLabel = new JLabel("Internet Speed:");
                                    JTextField internetSpeedField = new JTextField(connectionHolder[0] != null ? String.valueOf(connectionHolder[0].getConnectionSpeed()) : "");
                                    TariffPlan finalTariff = tariff;
                                    internetSpeedField.addFocusListener(new FocusAdapter() {
                                        @Override
                                        public void focusGained(FocusEvent e) {
                                            internetSpeedField.setText("");
                                        }
                                   });

                                    JLabel tariffPlanLabel = new JLabel("Tariff Plan:");
                                    JComboBox<String> tariffPlanComboBox = new JComboBox<>();
                                    List<TariffPlan> tariffPlans = tariffPlanDao.findAll();
                                    for (TariffPlan plan : tariffPlans) {
                                        tariffPlanComboBox.addItem(plan.getName());
                                    }
                                    if (tariff != null)
                                        tariffPlanComboBox.setSelectedItem(tariff.getName());

                                    tariffPlanComboBox.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String selectedTariffName = (String) tariffPlanComboBox.getSelectedItem();
                                            TariffPlan selectedTariff = tariffPlanDao.findByName(selectedTariffName);
                                            if (selectedTariff != null) {
                                                internetSpeedField.setText(String.valueOf(selectedTariff.getInternetSpeed()));
                                            }
                                        }
                                    });


                                    JCheckBox connectionStatusCheckBox = new JCheckBox("Connection Status", connectionHolder[0] != null && connectionHolder[0].getConnectionStatus());

                                    JButton applyButton = new JButton("Apply");

                                    boolean[] changesMade = {false};
                                    connectionStatusCheckBox.addItemListener(new ItemListener() {
                                        @Override
                                        public void itemStateChanged(ItemEvent e) {
                                            changesMade[0] = true;
                                        }
                                    });
                                    internetSpeedField.getDocument().addDocumentListener(new DocumentListener() {
                                        @Override
                                        public void insertUpdate(DocumentEvent e) {
                                            changesMade[0] = true;
                                        }

                                        @Override
                                        public void removeUpdate(DocumentEvent e) {
                                            changesMade[0] = true;
                                        }

                                        @Override
                                        public void changedUpdate(DocumentEvent e) {
                                            changesMade[0] = true;
                                        }
                                    });
                                    tariffPlanComboBox.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            changesMade[0] = true;
                                        }
                                    });

                                    manageConnectionsPanel.add(internetSpeedLabel);
                                    manageConnectionsPanel.add(internetSpeedField);
                                    manageConnectionsPanel.add(tariffPlanLabel);
                                    manageConnectionsPanel.add(tariffPlanComboBox);
                                    manageConnectionsPanel.add(new JLabel()); // Empty cell for layout
                                    manageConnectionsPanel.add(connectionStatusCheckBox);
                                    manageConnectionsPanel.add(new JLabel()); // Empty cell for layout
                                    manageConnectionsPanel.add(applyButton);

                                    // Добавляем панель на окно
                                    manageConnectionsWindow.add(manageConnectionsPanel);

                                    applyButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (connectionHolder[0] == null) {
                                                int response = JOptionPane.showConfirmDialog(manageConnectionsWindow, "No connection found for this user. Do you want to create a new connection?", "Create Connection", JOptionPane.YES_NO_OPTION);
                                                if (response == JOptionPane.YES_OPTION) {
                                                    String selectedTariffName = (String) tariffPlanComboBox.getSelectedItem();
                                                    TariffPlan selectedTariff = tariffPlanDao.findByName(selectedTariffName);
                                                    Connection newConnection = new Connection(user, selectedTariff);
                                                    newConnection.setConnectionSpeed(internetSpeedField != null ? Double.parseDouble(internetSpeedField.getText()) : selectedTariff.getInternetSpeed());
                                                    newConnection.setConnectionStatus(connectionStatusCheckBox.isSelected());
                                                    boolean isCreated = connectionDao.saveOrUpdate(newConnection);

                                                    if (isCreated) {
                                                        JOptionPane.showMessageDialog(manageConnectionsWindow, "New connection created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                                        connectionHolder[0] = newConnection; // Обновляем connectionHolder
                                                    } else {
                                                        JOptionPane.showMessageDialog(manageConnectionsWindow, "Fail to create new connection", "Failed", JOptionPane.ERROR_MESSAGE);
                                                    }
                                                }
                                            } else {
                                                // Проверяем, были ли внесены изменения
                                                if (changesMade[0]) {
                                                    String selectedTariffName = (String) tariffPlanComboBox.getSelectedItem();
                                                    TariffPlan selectedTariff = tariffPlanDao.findByName(selectedTariffName);
                                                    connectionHolder[0].setConnectionSpeed(internetSpeedField != null ? Double.parseDouble(internetSpeedField.getText()) : selectedTariff.getInternetSpeed());
                                                    connectionHolder[0].setTariffPlan(selectedTariff);
                                                    connectionHolder[0].setConnectionStatus(connectionStatusCheckBox.isSelected());
                                                    connectionDao.saveOrUpdate(connectionHolder[0]);
                                                    JOptionPane.showMessageDialog(manageConnectionsWindow, "Changes applied successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                                } else {
                                                    JOptionPane.showMessageDialog(manageConnectionsWindow, "No changes made.", "Information", JOptionPane.INFORMATION_MESSAGE);
                                                }
                                            }
                                        }
                                    });

                                    // Отображаем окно управления подключениями
                                    manageConnectionsWindow.setVisible(true);
                                    idField.setText("");
                                    idField.requestFocusInWindow();

                                    // Закрываем текущее окно поиска пользователя
                                    manageConnectionsFrame.dispose();
                                } else {
                                    JOptionPane.showMessageDialog(null, "No user found with the provided ID", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Invalid ID format, please enter a valid integer", "Invalid data", JOptionPane.ERROR_MESSAGE);
                                idField.setText("");
                                idField.requestFocusInWindow();
                            }
                        }
                    }
                });

                manageConnectionsFrame.add(manageConnectionsPanel);
                manageConnectionsFrame.setVisible(true);
                idField.requestFocusInWindow();
            }
        });

        tariffsAndServicesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame tariffsFrame = new JFrame("Tariffs");
                tariffsFrame.setSize(1200, 600);
                tariffsFrame.setLocationRelativeTo(null);
                tariffsFrame.getContentPane().setBackground(Color.YELLOW);

                JTextArea tariffsArea = new JTextArea();
                List<TariffPlan> tariffPlans = tariffPlanDao.findAll();
                StringBuilder sb = new StringBuilder();
                for (TariffPlan plan : tariffPlans) {
                    sb.append(plan.toString()).append("\n");
                }
                tariffsArea.setText(sb.toString());
                tariffsArea.setEditable(false);
                tariffsArea.setBackground(Color.YELLOW);

                tariffsFrame.add(new JScrollPane(tariffsArea));
                tariffsFrame.setVisible(true);
            }
        });

        faqButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame faqFrame = new JFrame("Frequently Asked Questions");
                faqFrame.setSize(600, 400);
                faqFrame.setLocationRelativeTo(null);
                faqFrame.getContentPane().setBackground(Color.YELLOW);

                JPanel faqPanel = new JPanel();
                faqPanel.setLayout(new BorderLayout());
                faqPanel.setBackground(Color.YELLOW);

                JTextField searchField = new JTextField();
                JTextArea faqArea = new JTextArea();
                Font font = new Font("Arial", Font.PLAIN, 14); // Создание нового шрифта
                faqArea.setFont(font); // Установка шрифта для текстовой области
                faqArea.setEditable(false);
                faqArea.setBackground(Color.YELLOW);
                faqArea.setRows(20); // Установка числа строк в текстовой области
                faqArea.setColumns(40); // Установка числа столбцов в текстовой области
                faqArea.setEditable(false);
                faqArea.setBackground(Color.YELLOW);

                JButton searchButton = new JButton("Search");
                searchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String query = searchField.getText();

                        // Выполнить поиск вопросов и ответов
                        List<Question> searchResults = questionDao.searchQuestionsAndAnswers(query);

                        // Отобразить результаты поиска в текстовой области
                        if (searchResults.isEmpty()) {
                            faqArea.setText("No results found for query: " + query);
                        } else {
                            StringBuilder resultText = new StringBuilder("Results for query: " + query + "\n");
                            for (Question question : searchResults) {
                                resultText.append(question.getQuestion()).append("\n");
                                for (Answer answer : question.getAnswers()) {
                                    resultText.append("- ").append(answer.getAnswer()).append("\n");
                                }
                                resultText.append("\n");
                            }
                            faqArea.setText(resultText.toString());
                        }
                    }
                });

                faqPanel.add(searchField, BorderLayout.NORTH);
                faqPanel.add(new JScrollPane(faqArea), BorderLayout.CENTER);
                faqPanel.add(searchButton, BorderLayout.SOUTH);

                faqFrame.add(faqPanel);
                faqFrame.setVisible(true);
            }
        });

        createTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame createTicketFrame = new JFrame("Create a New Ticket");
                createTicketFrame.setSize(600, 600);
                createTicketFrame.setLocationRelativeTo(null);
                createTicketFrame.getContentPane().setBackground(Color.YELLOW);

                JPanel createTicketPanel = new JPanel();
                createTicketPanel.setLayout(new GridLayout(6, 2));
                createTicketPanel.setBackground(Color.YELLOW);

                JLabel userIdLabel = new JLabel("User ID");
                JTextField userIdField = new JTextField();

                JLabel titleLabel = new JLabel("Title:");
                JTextField titleField = new JTextField();

                JLabel descriptionLabel = new JLabel("Description:");
                JTextArea descriptionArea = new JTextArea();

                JButton createButton = new JButton("Create");

                createTicketPanel.add(userIdLabel);
                createTicketPanel.add(userIdField);
                createTicketPanel.add(titleLabel);
                createTicketPanel.add(titleField);
                createTicketPanel.add(descriptionLabel);
                createTicketPanel.add(new JScrollPane(descriptionArea));
                createTicketPanel.add(new JLabel()); // Empty cell
                createTicketPanel.add(createButton);

                createButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Long userId= Long.parseLong(userIdField.getText());
                        // Здесь выполните поиск пользователя по userId или passportId
                        User user = userDao.findById(userId); // Примерный метод для поиска пользователя по userId или passportId

                        String title = titleField.getText();
                        String description = descriptionArea.getText();

                        if (user != null) {
                            Ticket newTicket = new Ticket(title, description, user);
                            boolean createdSuccessfully = ticketDao.saveOrUpdate(newTicket);

                            if (createdSuccessfully) {
                                JOptionPane.showMessageDialog(createTicketFrame, "Ticket created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(createTicketFrame, "Failed to create ticket", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            createTicketFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(createTicketFrame, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                createTicketFrame.add(createTicketPanel);
                createTicketFrame.setVisible(true);
            }
        });

        viewTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Ticket> tickets = ticketDao.findAll();
                displayTickets(tickets);
            }

            public void displayTickets(List<Ticket> tickets) {
                JFrame ticketsFrame = new JFrame("Tickets");
                ticketsFrame.setSize(600, 400);
                ticketsFrame.setLocationRelativeTo(null);
                ticketsFrame.getContentPane().setBackground(Color.WHITE);

                JPanel ticketsPanel = new JPanel(new GridLayout(tickets.size(), 1));

                for (Ticket ticket : tickets) {
                    JButton ticketButton = new JButton("Ticket : " + ticket.getTitle() + " " + ticket.getId());
                    if (ticket.getStatus().equals("Open"))
                        ticketButton.setBackground(Color.GREEN);
                    else {
                        ticketButton.setBackground(Color.RED);
                    }
                    ticketButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            displayTicketDetails(ticket);
                        }
                    });
                    ticketsPanel.add(ticketButton);
                }

                ticketsFrame.add(new JScrollPane(ticketsPanel));
                ticketsFrame.setVisible(true);
            }

            public void displayTicketDetails(Ticket ticket) {
                JFrame ticketFrame = new JFrame("Ticket Details: " + ticket.getTitle());
                ticketFrame.setSize(600, 400);
                ticketFrame.setLocationRelativeTo(null);
                ticketFrame.getContentPane().setBackground(Color.WHITE);

                JTextArea detailsArea = new JTextArea(ticket.toString());
                detailsArea.setEditable(false);

                if (ticket.getStatus().equals("Open")) {
                    JButton closeButton = new JButton("Close Ticket");
                    closeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int option = JOptionPane.showConfirmDialog(ticketFrame, "Are you sure you want to close this ticket?", "Confirm", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.YES_OPTION) {
                                // Implement ticket closing logic here
                                ticket.setStatus("Closed");
                                ticketDao.saveOrUpdate(ticket);
                                JOptionPane.showMessageDialog(ticketFrame, "Ticket closed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                                ticketFrame.dispose();
                            }
                        }
                    });
                    ticketFrame.add(closeButton, BorderLayout.SOUTH);
                }

                ticketFrame.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
                ticketFrame.setVisible(true);
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        menuFrame.add(menuPanel);
        menuFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingMegaTUI frame = new SwingMegaTUI();
            frame.setVisible(true);
        });
    }
}