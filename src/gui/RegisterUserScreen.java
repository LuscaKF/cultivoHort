package gui;

import utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterUserScreen extends JFrame {
    private JTextField nameField;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    public RegisterUserScreen() {
        setTitle("Register User");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Login:"));
        loginField = new JTextField();
        panel.add(loginField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Role:"));
        roleBox = new JComboBox<>(new String[]{"admin", "user"});
        panel.add(roleBox);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> registerUser());
        panel.add(registerButton);

        add(panel);
    }

    private void registerUser() {
        String name = nameField.getText();
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        String query = "INSERT INTO usuarios (nome, login, senha, funcao) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, login);
            stmt.setString(3, password);
            stmt.setString(4, role);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                dispose(); // Fechar esta janela após o registro do usuário
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register user.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterUserScreen registerUserScreen = new RegisterUserScreen();
            registerUserScreen.setVisible(true);
        });
    }
}
