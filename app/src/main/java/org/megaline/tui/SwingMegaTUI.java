package org.megaline.tui;

import org.megaline.core.dao.ConnectionDao;
import org.megaline.core.dao.EmployeeDao;
import org.megaline.core.dao.TariffPlanDao;
import org.megaline.core.dao.UserDao;
import org.megaline.core.models.Employee;
import org.megaline.core.models.TariffPlan;
import org.megaline.core.models.User;
import org.megaline.core.models.Connection;
import java.util.List;

import org.megaline.core.util.DHCP;
import org.megaline.core.util.PasswordHasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingMegaTUI extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwingMegaTUI.class);
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton[] menuButtons;
    private EmployeeDao employeeDao = new EmployeeDao();
    private UserDao userDao = new UserDao();
    private TariffPlanDao tariffPlanDao = new TariffPlanDao();
    private ConnectionDao connectionDao = new ConnectionDao();

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
                manageConnectionsFrame.setSize(400, 300);
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
                            JOptionPane.showMessageDialog(SwingMegaTUI.this, "ID cannot be empty, please enter valid data", "Invalid data", JOptionPane.ERROR_MESSAGE);
                            idField.requestFocusInWindow();
                        } else {
                            try {
                                int id = Integer.parseInt(idText);
                                User user = userDao.findById(id);
                                if (user != null) {
                                    // Создаем новое окно для управления подключениями
                                    JFrame manageConnectionsWindow = new JFrame("Manage Connections for User: " + user.getName());
                                    manageConnectionsWindow.setSize(400, 200);
                                    manageConnectionsWindow.setLocationRelativeTo(null);
                                    manageConnectionsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    Connection connection = connectionDao.findByUserId(user.getId());
                                    TariffPlan tariff = null;
                                    try {
                                        tariff = tariffPlanDao.findById(connection.getTariffPlan().getId());
                                    } catch (NullPointerException npe) {
                                        System.err.println("This user not connected to any tariff plan");
                                        npe.printStackTrace();
                                    }
                                    // Создаем панель для управления подключениями
                                    JPanel manageConnectionsPanel = new JPanel();
                                    manageConnectionsPanel.setLayout(new GridLayout(3, 2));

                                    // Добавляем компоненты для управления подключениями
                                    JLabel internetSpeedLabel = new JLabel("Internet Speed:");
                                    JTextField internetSpeedField = new JTextField(String.valueOf(connection.getConnectionSpeed()));
                                    JLabel tariffPlanLabel = new JLabel("Tariff Plan:");
                                    JComboBox<String> tariffPlanComboBox = new JComboBox<>();
                                    List<TariffPlan> tariffPlans = tariffPlanDao.findAll();
                                    for (TariffPlan plan : tariffPlans) {
                                        tariffPlanComboBox.addItem(plan.getName());
                                    }
                                    if (tariff != null)
                                        tariffPlanComboBox.setSelectedItem(tariff.getName());

                                    JCheckBox connectionStatusCheckBox = new JCheckBox("Connection Status", connection.getConnectionStatus());
                                    JButton applyButton = new JButton("Apply");

                                    manageConnectionsPanel.add(internetSpeedLabel);
                                    manageConnectionsPanel.add(internetSpeedField);
                                    manageConnectionsPanel.add(tariffPlanLabel);
                                    manageConnectionsPanel.add(tariffPlanComboBox);
                                    manageConnectionsPanel.add(connectionStatusCheckBox);
                                    manageConnectionsPanel.add(applyButton);

                                    // Добавляем панель на окно
                                    manageConnectionsWindow.add(manageConnectionsPanel);

                                    // Передаем объект пользователя в окно управления подключениями (например, через конструктор или метод)
                                    // ...

                                    applyButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            // Реализация логики применения изменений
                                        }
                                    });

                                    // Отображаем окно управления подключениями
                                    manageConnectionsWindow.setVisible(true);

                                    // Закрываем текущее окно поиска пользователя
                                    manageConnectionsFrame.dispose();
                                } else {
                                    JOptionPane.showMessageDialog(SwingMegaTUI.this, "No user found with the provided ID", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(SwingMegaTUI.this, "Invalid ID format, please enter a valid integer", "Invalid data", JOptionPane.ERROR_MESSAGE);
                                idField.setText("");
                                idField.requestFocusInWindow();
                            }
                        }
                    }
                });

                manageConnectionsFrame.add(manageConnectionsPanel);
                manageConnectionsFrame.setVisible(true);
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
                faqFrame.setSize(400, 300);
                faqFrame.setLocationRelativeTo(null);
                faqFrame.getContentPane().setBackground(Color.YELLOW);

                JPanel faqPanel = new JPanel();
                faqPanel.setLayout(new BorderLayout());
                faqPanel.setBackground(Color.YELLOW);

                JTextField searchField = new JTextField();
                JTextArea faqArea = new JTextArea();
                faqArea.setEditable(false);
                faqArea.setBackground(Color.YELLOW);

                JButton searchButton = new JButton("Search");
                searchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String query = searchField.getText();
                        // Implement FAQ search logic here

                        faqArea.setText("Result for query: " + query + "\n1. FAQ Entry 1\n2. FAQ Entry 2");
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
        });

        viewTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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