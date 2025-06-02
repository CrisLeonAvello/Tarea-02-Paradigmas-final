package Frontend;

import Backend.Item;
import Backend.Lector;
import Backend.Prueba;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class LectorArchivos extends JFrame {
    private String rutaArchivo;

    public LectorArchivos() {
        setTitle("Inicio - Seleccionar archivo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Seleccione la ubicación de su archivo.", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        add(label, BorderLayout.NORTH);

        JButton seleccionarArchivoButton = new JButton("Seleccionar Archivo");
        JButton iniciarPruebaButton = new JButton("Iniciar Prueba");
        iniciarPruebaButton.setEnabled(false);

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        botonesPanel.add(seleccionarArchivoButton);
        botonesPanel.add(iniciarPruebaButton);
        add(botonesPanel, BorderLayout.CENTER);

        JLabel estadoArchivoLabel = new JLabel("Archivo no cargado", SwingConstants.CENTER);
        estadoArchivoLabel.setOpaque(true);
        estadoArchivoLabel.setBackground(Color.RED);
        estadoArchivoLabel.setForeground(Color.WHITE);
        add(estadoArchivoLabel, BorderLayout.SOUTH);

        seleccionarArchivoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar archivo CSV");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
            int resultado = fileChooser.showOpenDialog(this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File seleccionado = fileChooser.getSelectedFile();
                if (seleccionado.exists() && seleccionado.isFile() && seleccionado.getName().toLowerCase().endsWith(".csv")) {
                    rutaArchivo = seleccionado.getAbsolutePath();
                    estadoArchivoLabel.setText("Archivo cargado");
                    estadoArchivoLabel.setBackground(Color.GREEN);
                    iniciarPruebaButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Seleccione un archivo válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        iniciarPruebaButton.addActionListener(e -> {
            try {
                List<Item> items = Lector.cargarItems(rutaArchivo);
                new VentanaPrueba(new Prueba(items));
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}