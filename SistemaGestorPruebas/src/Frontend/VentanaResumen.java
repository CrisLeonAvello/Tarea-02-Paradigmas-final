package Frontend;

import Backend.Prueba;
import Backend.ResultadosPrueba;

import javax.swing.*;
import java.awt.*;

public class VentanaResumen extends JFrame {

    public VentanaResumen(Prueba prueba) {
        setTitle("Resumen de Resultados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Obtener los resultados de la prueba
        ResultadosPrueba resultados = prueba.obtenerResultados();

        // Panel para mostrar los datos de resultados
        JTextArea resultadosArea = new JTextArea();
        resultadosArea.setEditable(false);
        resultadosArea.setText("Resultados de la Prueba:\n" +
                "Total de Preguntas: " + resultados.getTotalPreguntas() +
                "\nCorrectas: " + resultados.getPreguntasCorrectas());

        add(new JScrollPane(resultadosArea), BorderLayout.CENTER);

        // Botones inferiores
        JButton revisarButton = new JButton("Revisar Respuestas");
        JButton salirButton = new JButton("Salir");

        // Escucha del botón revisar
        revisarButton.addActionListener(e -> {
            // Abrir la ventana de revisión de preguntas
            new VentanaRevisar(prueba);
            dispose(); // Cerrar la ventana de resumen actual
        });

        // Salir cierra la ventana
        salirButton.addActionListener(e -> dispose());

        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        botonesPanel.add(revisarButton);
        botonesPanel.add(salirButton);

        add(botonesPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Centrar en pantalla
        setVisible(true);
    }
}