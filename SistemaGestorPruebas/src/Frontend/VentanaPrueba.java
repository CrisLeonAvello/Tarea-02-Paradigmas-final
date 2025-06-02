package Frontend;

import Backend.Item;
import Backend.ItemOM;
import Backend.ItemVF;
import Backend.Prueba;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.List;

public class VentanaPrueba extends JFrame {
    private final Prueba prueba;
    private final JLabel preguntaLabel;
    private final JPanel respuestaPanel;
    private final JButton retrocederButton;
    private final JButton avanzarFinalizarButton;
    private final ButtonGroup grupoRespuestas;

    public VentanaPrueba(Prueba prueba) {
        this.prueba = prueba;

        setTitle("Prueba");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Etiqueta para mostrar la pregunta actual
        preguntaLabel = new JLabel("Pregunta 1", SwingConstants.CENTER);
        preguntaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(preguntaLabel, BorderLayout.NORTH);

        // Panel para mostrar las opciones de respuesta dinámicamente
        respuestaPanel = new JPanel();
        respuestaPanel.setLayout(new BoxLayout(respuestaPanel, BoxLayout.Y_AXIS));
        add(respuestaPanel, BorderLayout.CENTER);

        // Botones de navegación
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        retrocederButton = new JButton("Retroceder");
        avanzarFinalizarButton = new JButton("Avanzar");
        botonesPanel.add(retrocederButton);
        botonesPanel.add(avanzarFinalizarButton);
        add(botonesPanel, BorderLayout.SOUTH);

        // Configuración inicial de los botones
        retrocederButton.setEnabled(false); // Al iniciar estamos en la primera pregunta
        grupoRespuestas = new ButtonGroup(); // Grupo usado para agrupar las opciones

        // Listeners de los botones de avanzar y retroceder
        retrocederButton.addActionListener(e -> retroceder());
        avanzarFinalizarButton.addActionListener(e -> avanzarOFinalizar());

        setLocationRelativeTo(null);
        setVisible(true);

        mostrarPreguntaActual();
    }

    private void mostrarPreguntaActual() {
        Item itemActual = prueba.getItemActual();
        if (itemActual != null) {
            // Mostrar el número y el texto de la pregunta
            preguntaLabel.setText("Pregunta " + (prueba.getIndiceActual() + 1) + ": " + itemActual.getEnunciado());

            // Renderizar dinámicamente las opciones de respuesta
            renderizarOpciones(itemActual);
        }

        actualizarBotones();
    }

    private void renderizarOpciones(Item item) {
        // Limpiar el panel de respuestas para nuevas opciones
        respuestaPanel.removeAll();
        grupoRespuestas.clearSelection(); // Limpiar selección previa (si hay)

        if (item instanceof ItemVF) {
            // Pregunta de Verdadero/Falso
            JRadioButton opcionVerdadero = new JRadioButton("Verdadero");
            JRadioButton opcionFalso = new JRadioButton("Falso");

            grupoRespuestas.add(opcionVerdadero);
            grupoRespuestas.add(opcionFalso);

            respuestaPanel.add(opcionVerdadero);
            respuestaPanel.add(opcionFalso);

            // Rellenar el valor seleccionado si ya fue respondido
            Boolean respuesta = ((ItemVF) item).getRespuestaUsuario();
            if (respuesta != null) {
                if (respuesta) {
                    opcionVerdadero.setSelected(true);
                } else {
                    opcionFalso.setSelected(true);
                }
            }

        } else if (item instanceof ItemOM) {
            // Pregunta de opción múltiple
            ItemOM itemOM = (ItemOM) item;
            List<String> opciones = itemOM.getOpciones();

            for (int i = 0; i < opciones.size(); i++) {
                JRadioButton opcion = new JRadioButton(opciones.get(i));
                int indice = i; // Necesario para capturar el índice correcto
                grupoRespuestas.add(opcion);
                respuestaPanel.add(opcion);

                // Rellenar el valor seleccionado si ya fue respondido
                if (itemOM.getRespuestaUsuario() == indice) {
                    opcion.setSelected(true);
                }
            }
        }

        // Actualizar visualmente el panel
        respuestaPanel.revalidate();
        respuestaPanel.repaint();
    }

    private void avanzarOFinalizar() {
        guardarRespuesta();

        if (prueba.isUltimoItem()) {
            finalizar();
        } else {
            if (prueba.avanzar()) {
                mostrarPreguntaActual();
            }
        }
    }

    private void retroceder() {
        guardarRespuesta();

        if (prueba.retroceder()) {
            mostrarPreguntaActual();
        }
    }

    private void guardarRespuesta() {
        Item itemActual = prueba.getItemActual();

        if (itemActual instanceof ItemVF) {
            // Guardar respuesta para Verdadero/Falso
            Enumeration<AbstractButton> botones = grupoRespuestas.getElements();
            while (botones.hasMoreElements()) {
                AbstractButton boton = botones.nextElement();
                if (boton.isSelected()) {
                    ((ItemVF) itemActual).setRespuestaUsuario(boton.getText().equals("Verdadero"));
                    break;
                }
            }
        } else if (itemActual instanceof ItemOM) {
            // Guardar respuesta para opción múltiple
            Enumeration<AbstractButton> botones = grupoRespuestas.getElements();
            int indice = 0;
            while (botones.hasMoreElements()) {
                AbstractButton boton = botones.nextElement();
                if (boton.isSelected()) {
                    ((ItemOM) itemActual).setRespuestaUsuario(indice);
                    break;
                }
                indice++;
            }
        }

        // Esto asegura que los cambios visuales se aplican adecuadamente
        respuestaPanel.revalidate();
        respuestaPanel.repaint();
    }

    private void finalizar() {
        guardarRespuesta();

        JOptionPane.showMessageDialog(
                this,
                "Prueba Finalizada. Gracias por participar.",
                "Fin",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Mostrar la ventana de resumen
        new VentanaResumen(prueba);
        dispose(); // Cerrar esta ventana
    }

    private void actualizarBotones() {
        retrocederButton.setEnabled(!prueba.isPrimerItem());

        if (prueba.isUltimoItem()) {
            avanzarFinalizarButton.setText("Finalizar Prueba");
        } else {
            avanzarFinalizarButton.setText("Avanzar");
        }
    }
}