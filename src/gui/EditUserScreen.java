package gui;

import utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditUserScreen extends JFrame {
    private JTextField nameField;
    private JTextField loginField;
    private JComboBox<String> roleBox;
    private final JLabel userIdLabel;

    public EditUserScreen(int userId, String userName, String userLogin, String userFunction) {
        setTitle("Editar Usuário");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fechar apenas esta janela
        setLocationRelativeTo(null);

        userIdLabel = new JLabel(String.valueOf(userId)); // Inicializar o userIdLabel com o ID do usuário
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("ID:"));
        panel.add(userIdLabel); // Adicionar userIdLabel ao painel

        panel.add(new JLabel("Nome:"));
        nameField = new JTextField(userName);
        panel.add(nameField);

        panel.add(new JLabel("Login:"));
        loginField = new JTextField(userLogin);
        panel.add(loginField);

        panel.add(new JLabel("Função:"));
        roleBox = new JComboBox<>(new String[]{"admin", "user"});
        roleBox.setSelectedItem(userFunction);
        panel.add(roleBox);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> saveChanges());
        panel.add(saveButton);

        add(panel);
    }

    private void saveChanges() {
        // Removido o trecho que tenta obter o texto do userIdLabel, pois não é mais necessário

        int userId = Integer.parseInt(userIdLabel.getText()); // Obtendo o ID do usuário diretamente do userIdLabel

        String newName = nameField.getText();
        String newLogin = loginField.getText();
        String newFunction = (String) roleBox.getSelectedItem();

        String query = "UPDATE usuarios SET nome = ?, login = ?, funcao = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newName);
            stmt.setString(2, newLogin);
            stmt.setString(3, newFunction);
            stmt.setInt(4, userId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Nenhuma alteração realizada.");
            }

            dispose(); // Fechar esta janela após salvar as alterações

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar alterações: " + e.getMessage());
        }
    }
}
