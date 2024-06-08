package gui;

import utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditVegetableScreen extends JFrame {
    private JTextField nameField;
    private JTextField speciesField;
    private JTextField plantingDateField;
    private JTextArea observationsArea;
    private int vegetableId;

    public EditVegetableScreen(int vegetableId, String vegetableName, String vegetableSpecies, String plantingDate, String observations) {
        this.vegetableId = vegetableId;
        setTitle("Editar Hortaliça");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("ID:"));
        panel.add(new JLabel(String.valueOf(vegetableId)));

        panel.add(new JLabel("Nome:"));
        nameField = new JTextField(vegetableName);
        panel.add(nameField);

        panel.add(new JLabel("Espécie:"));
        speciesField = new JTextField(vegetableSpecies);
        panel.add(speciesField);

        panel.add(new JLabel("Data de Plantio:"));
        plantingDateField = new JTextField(plantingDate);
        panel.add(plantingDateField);

        panel.add(new JLabel("Observações:"));
        observationsArea = new JTextArea(observations);
        panel.add(new JScrollPane(observationsArea));

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });
        panel.add(saveButton);

        add(panel);
    }

    private void saveChanges() {
        String newName = nameField.getText();
        String newSpecies = speciesField.getText();
        String newPlantingDate = plantingDateField.getText();
        String newObservations = observationsArea.getText();

        String query = "UPDATE hortalicas SET nome = ?, especie = ?, data_plantio = ?, observacoes = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newName);
            stmt.setString(2, newSpecies);
            stmt.setString(3, newPlantingDate);
            stmt.setString(4, newObservations);
            stmt.setInt(5, vegetableId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Nenhuma alteração realizada.");
            }

            dispose(); // Fechar esta janela após salvar as alterações

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar alterações: " + ex.getMessage());
        }
    }
}
