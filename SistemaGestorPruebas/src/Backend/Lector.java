package Backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lector {
    public static List<Item> cargarItems(String rutaArchivo) throws IOException {
        List<Item> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int lineaNumero = 0;

            while ((linea = br.readLine()) != null) {
                lineaNumero++;

                // Ignorar línea de cabecera
                if (lineaNumero == 1 && linea.startsWith("Taxonomía")) continue;

                // Dividir la línea respetando comas dentro de comillas
                String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (partes.length != 4) {
                    System.err.println("Línea " + lineaNumero + " no tiene el formato esperado.");
                    continue;
                }

                try {
                    String taxonomia = partes[0].replaceAll("\"", "").trim();
                    String enunciado = partes[1].replaceAll("\"", "").trim();
                    String tiempoTotal = partes[2].replaceAll("\"", "").trim();
                    String respuestaCorrecta = partes[3].replaceAll("\"", "").trim();

                    if (respuestaCorrecta.equalsIgnoreCase("true") || respuestaCorrecta.equalsIgnoreCase("false")) {
                        items.add(new ItemVF(taxonomia, enunciado, Integer.parseInt(tiempoTotal), Boolean.parseBoolean(respuestaCorrecta)));
                    } else {
                        String[] opciones = enunciado.split(";");
                        if (opciones.length > 1) {
                            String textoPregunta = opciones[0].trim();
                            List<String> listaOpciones = new ArrayList<>();
                            for (int i = 1; i < opciones.length; i++) {
                                listaOpciones.add(opciones[i].trim());
                            }

                            int indiceCorrecto = listaOpciones.indexOf(respuestaCorrecta);

                            if (indiceCorrecto == -1) {
                                System.err.println("Respuesta no encontrada en las opciones (línea " + lineaNumero + ").");
                            } else {
                                items.add(new ItemOM(taxonomia, textoPregunta, Integer.parseInt(tiempoTotal), listaOpciones, indiceCorrecto));
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error procesando línea " + lineaNumero + ": " + e.getMessage());
                }
            }
        }
        return items;
    }
}