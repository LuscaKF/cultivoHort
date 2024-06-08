package gui;

import utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminScreen extends JFrame {
    private JTable userTable;
    private DefaultTableModel tableModel;

    public AdminScreen() {
        setTitle("Admin Screen");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Tabela de usuários
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Login");
        tableModel.addColumn("Função");
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton viewButton = new JButton("Visualizar");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewUsers();
            }
        });
        buttonPanel.add(viewButton);

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editUser();
            }
        });
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Remover");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
        buttonPanel.add(deleteButton);

        JButton registerUserButton = new JButton("Cadastrar Usuário");
        registerUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterUserScreen registerUserScreen = new RegisterUserScreen();
                registerUserScreen.setVisible(true);
            }
        });
        buttonPanel.add(registerUserButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implemente a lógica para fazer logout
                dispose(); // Fechar a tela atual
                LoginScreen loginScreen = new LoginScreen(); // Abrir a tela de login
                loginScreen.setVisible(true);
            }
        });
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void viewUsers() {
        // Limpa a tabela antes de exibir os usuários
        tableModel.setRowCount(0);

        String query = "SELECT * FROM usuarios";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String login = rs.getString("login");
                String funcao = rs.getString("funcao");

                tableModel.addRow(new Object[]{id, nome, login, funcao});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao buscar usuários: " + e.getMessage());
        }
    }

    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.");
            return;
        }

        Object userIdObj = userTable.getValueAt(selectedRow, 0);
        if (!(userIdObj instanceof Integer)) {
            JOptionPane.showMessageDialog(this, "Erro ao obter o ID do usuário.");
            return;
        }

        int userId = (int) userIdObj;

        String userName = (String) userTable.getValueAt(selectedRow, 1);
        String userLogin = (String) userTable.getValueAt(selectedRow, 2);
        String userFunction = (String) userTable.getValueAt(selectedRow, 3);

        // Abrir a tela de edição com os detalhes do usuário
        SwingUtilities.invokeLater(() -> {
            EditUserScreen editUserScreen = new EditUserScreen(userId, userName, userLogin, userFunction);
            editUserScreen.setVisible(true);
        });
    }




    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para remover.");
            return;
        }

        int userId = (int) userTable.getValueAt(selectedRow, 0);

        int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover este usuário?");
        if (option == JOptionPane.YES_OPTION) {
            String query = "DELETE FROM usuarios WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, userId);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Usuário removido com sucesso!");
                // Atualizar a tabela após remover o usuário
                viewUsers();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao remover usuário: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminScreen().setVisible(true);
            }
        });
    }
}
