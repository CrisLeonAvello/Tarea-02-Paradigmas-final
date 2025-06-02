package Frontend;

import Backend.ResultadosPrueba;
import javax.swing.*;
import java.awt.*;

public class VentanaResumen extends JFrame {
    private final ResultadosPrueba resultados;

    public VentanaResumen(ResultadosPrueba resultados) {
        this.resultados = resultados;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Resumen de la Prueba");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Componentes
        JLabel lblTotal = new JLabel("Total de preguntas: " + resultados.getTotalPreguntas());
        JLabel lblCorrectas = new JLabel("Respuestas correctas: " + resultados.getPreguntasCorrectas());
        JLabel lblIncorrectas = new JLabel("Respuestas incorrectas: " + resultados.getPreguntasIncorrectas());
        JLabel lblPorcentaje = new JLabel("Porcentaje de acierto: " + resultados.getPorcentajeAciertoFormateado());
        JLabel lblCalificacion = new JLabel("Calificación: " + resultados.getCalificacionFormateada());
        
        JLabel lblEstado = new JLabel("Estado: " + resultados.getEstadoAprobacion());
        lblEstado.setForeground(Color.decode(resultados.getColorResultado()));
        lblEstado.setFont(new Font(lblEstado.getFont().getName(), Font.BOLD, 16));

        // Agregar componentes al panel
        Component[] componentes = {
            lblTotal, lblCorrectas, lblIncorrectas, 
            lblPorcentaje, lblCalificacion, lblEstado
        };

        for (Component comp : componentes) {
            comp.setFont(new Font("Arial", Font.PLAIN, 14));
            panel.add(comp);
            panel.add(Box.createVerticalStrut(15));
        }

        // Botón para cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panel.add(btnCerrar);

        add(panel);
    }
}