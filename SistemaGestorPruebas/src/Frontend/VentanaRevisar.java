package Frontend;

import Backend.Item;
import Backend.ItemOM;
import Backend.ItemVF;
import Backend.Prueba;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.List;

public class VentanaRevisar extends JFrame {
    private final Prueba prueba;
    private final JLabel preguntaLabel;
    private final JPanel respuestaPanel;
    private final JButton retrocederButton;
    private final JButton avanzarButton;
    private final JButton volverResumenButton;

    public VentanaRevisar(Prueba prueba) {
        this.prueba = prueba;

        setTitle("Revisión de Respuestas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Etiqueta para mostrar la pregunta actual
        preguntaLabel = new JLabel("Pregunta 1", SwingConstants.CENTER);
        preguntaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(preguntaLabel, BorderLayout.NORTH);

        // Panel para mostrar las opciones de respuesta y el resultado
        respuestaPanel = new JPanel();
        respuestaPanel.setLayout(new BoxLayout(respuestaPanel, BoxLayout.Y_AXIS));
        add(respuestaPanel, BorderLayout.CENTER);

        // Botones de navegación
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        retrocederButton = new JButton("Retroceder");
        avanzarButton = new JButton("Avanzar");
        volverResumenButton = new JButton("Volver al Resumen");

        botonesPanel.add(retrocederButton);
        botonesPanel.add(avanzarButton);
        botonesPanel.add(volverResumenButton);
        add(botonesPanel, BorderLayout.SOUTH);

        // Configuración inicial de los botones
        retrocederButton.setEnabled(false); // Al iniciar estamos en la primera pregunta
        volverResumenButton.setVisible(false);

        // Listeners de botones
        retrocederButton.addActionListener(e -> retroceder());
        avanzarButton.addActionListener(e -> avanzar());
        volverResumenButton.addActionListener(e -> volverAlResumen());

        setLocationRelativeTo(null);
        setVisible(true);

        mostrarPreguntaActual();
    }

    private void mostrarPreguntaActual() {
        Item itemActual = prueba.getItemActual();
        if (itemActual != null) {
            // Mostrar el número y el texto de la pregunta
            preguntaLabel.setText("Pregunta " + (prueba.getIndiceActual() + 1) + ": " + itemActual.getEnunciado());

            // Renderizar opciones y el resultado
            renderizarOpciones(itemActual);
        }

        actualizarBotones();
    }

    private void renderizarOpciones(Item item) {
        // Limpiar el panel de respuesta para mostrar los nuevos datos
        respuestaPanel.removeAll();

        // Mostrar la respuesta correcta y la del usuario
        JLabel resultadoLabel = new JLabel();
        resultadoLabel.setFont(new Font("Arial", Font.BOLD, 14));

        if (item.esCorrectaRespuesta()) {
            resultadoLabel.setText("Respuesta Correcta ✅");
            resultadoLabel.setForeground(Color.GREEN);
        } else {
            resultadoLabel.setText("Respuesta Incorrecta ❌");
            resultadoLabel.setForeground(Color.RED);
        }
        respuestaPanel.add(resultadoLabel);

        if (item instanceof ItemVF) {
            ItemVF itemVF = (ItemVF) item;
            respuestaPanel.add(new JLabel("Respuesta Correcta: " + (itemVF.getRespuestaCorrecta() ? "Verdadero" : "Falso")));
            respuestaPanel.add(new JLabel("Tu Respuesta: " + (itemVF.getRespuestaUsuario() != null ? (itemVF.getRespuestaUsuario() ? "Verdadero" : "Falso") : "No respondida")));
        } else if (item instanceof ItemOM) {
            ItemOM itemOM = (ItemOM) item;
            List<String> opciones = itemOM.getOpciones();
            respuestaPanel.add(new JLabel("Respuesta Correcta: " + itemOM.getRespuestaCorrecta()));

            if (itemOM.getRespuestaUsuario() != -1) {
                respuestaPanel.add(new JLabel("Tu Respuesta: " + opciones.get(itemOM.getRespuestaUsuario())));
            } else {
                respuestaPanel.add(new JLabel("Tu Respuesta: No respondida"));
            }
        }

        // Actualizar visualmente el panel
        respuestaPanel.revalidate();
        respuestaPanel.repaint();
    }

    private void avanzar() {
        if (prueba.isUltimoItem()) {
            avanzarButton.setVisible(false);
            volverResumenButton.setVisible(true);
        } else {
            if (prueba.avanzar()) {
                mostrarPreguntaActual();
            }
        }
    }

    private void retroceder() {
        if (prueba.retroceder()) {
            mostrarPreguntaActual();
        }
    }

    private void volverAlResumen() {
        new VentanaResumen(prueba); // Volver a la ventana de resumen
        dispose(); // Cierra la ventana de revisión
    }

    private void actualizarBotones() {
        retrocederButton.setEnabled(!prueba.isPrimerItem());

        if (prueba.isUltimoItem()) {
            avanzarButton.setText("Finalizar Revisión");
        } else {
            avanzarButton.setText("Avanzar");
        }
    }
}