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

public class UserVegetableScreen extends JFrame {
    private JTable vegetableTable;
    private DefaultTableModel tableModel;

    public UserVegetableScreen() {
        setTitle("User Vegetable Screen");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Tabela de hortaliças
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Espécie");
        tableModel.addColumn("Data de Plantio");
        tableModel.addColumn("Observações");
        vegetableTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(vegetableTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton viewButton = new JButton("Visualizar");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewVegetables();
            }
        });
        buttonPanel.add(viewButton);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addVegetable();
            }
        });
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editVegetable();
            }
        });
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Remover");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteVegetable();
            }
        });
        buttonPanel.add(deleteButton);

        // Botão de Logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void viewVegetables() {
        // Limpa a tabela antes de exibir as hortaliças
        tableModel.setRowCount(0);

        String query = "SELECT * FROM hortalicas";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String especie = rs.getString("especie");
                String dataPlantio = rs.getString("data_plantio");
                String observacoes = rs.getString("observacoes");

                tableModel.addRow(new Object[]{id, nome, especie, dataPlantio, observacoes});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao buscar hortaliças: " + e.getMessage());
        }
    }

    private void addVegetable() {
        JTextField nameField = new JTextField();
        JTextField speciesField = new JTextField();
        JTextField plantingDateField = new JTextField();
        JTextField observationsField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Nome:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Espécie:"));
        inputPanel.add(speciesField);
        inputPanel.add(new JLabel("Data de Plantio (YYYY-MM-DD):"));
        inputPanel.add(plantingDateField);
        inputPanel.add(new JLabel("Observações:"));
        inputPanel.add(observationsField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Adicionar Hortaliça",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String species = speciesField.getText();
            String plantingDate = plantingDateField.getText();
            String observations = observationsField.getText();

            String query = "INSERT INTO hortalicas (nome, especie, data_plantio, observacoes) VALUES (?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, name);
                stmt.setString(2, species);
                stmt.setString(3, plantingDate);
                stmt.setString(4, observations);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Hortaliça adicionada com sucesso!");
                    // Atualizar a tabela após adicionar a hortaliça
                    viewVegetables();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao adicionar hortaliça.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao adicionar hortaliça: " + e.getMessage());
            }
        }
    }

    private void editVegetable() {
        int selectedRow = vegetableTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma hortaliça para editar.");
            return;
        }

        int vegetableId = (int) vegetableTable.getValueAt(selectedRow, 0);
        String vegetableName = (String) vegetableTable.getValueAt(selectedRow, 1);
        String vegetableSpecies = (String) vegetableTable.getValueAt(selectedRow, 2);
        String plantingDate = (String) vegetableTable.getValueAt(selectedRow, 3);
        String observations = (String) vegetableTable.getValueAt(selectedRow, 4);

        // Abrir a tela de edição com os detalhes da hortaliça
        SwingUtilities.invokeLater(() -> {
            EditVegetableScreen editVegetableScreen = new EditVegetableScreen(vegetableId, vegetableName, vegetableSpecies, plantingDate, observations);
            editVegetableScreen.setVisible(true);
        });
    }

    private void deleteVegetable() {
        int selectedRow = vegetableTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma hortaliça para remover.");
            return;
        }

        int vegetableId = (int) vegetableTable.getValueAt(selectedRow, 0);

        int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover esta hortaliça?");
        if (option == JOptionPane.YES_OPTION) {
            String query = "DELETE FROM hortalicas WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, vegetableId);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Hortaliça removida com sucesso!");
                // Atualizar a tabela após remover a hortaliça
                viewVegetables();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao remover hortaliça: " + e.getMessage());
            }
        }
    }

    private void logout() {
        // Fecha a tela atual
        dispose();

        // Abre a tela de login
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserVegetableScreen().setVisible(true);
            }
        });
    }
}
