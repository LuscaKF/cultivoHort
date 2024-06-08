package gui;

import utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginScreen extends JFrame {
    private JTextField loginField;
    private JPasswordField passwordField;

    public LoginScreen() {
        setTitle("Login Screen");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Login:"));
        loginField = new JTextField();
        panel.add(loginField);

        panel.add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        panel.add(loginButton);

        add(panel);
    }

    private void login() {
        String login = loginField.getText();
        String senha = new String(passwordField.getPassword());

        String query = "SELECT funcao FROM usuarios WHERE login = ? AND senha = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String funcao = rs.getString("funcao");
                if ("admin".equals(funcao)) {
                    this.dispose();
                    new AdminScreen().setVisible(true);
                } else {
                    this.dispose();
                    UserVegetableScreen vegetableScreen = new UserVegetableScreen();
                    vegetableScreen.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login ou senha incorretos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao fazer login: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginScreen().setVisible(true);
            }
        });
    }
}
