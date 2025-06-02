package Backend;

import java.util.ArrayList;
import java.util.List;

public class ItemOM extends Item {
    private final List<String> opciones;
    private final int indiceCorrecto;         // Índice de la respuesta correcta.
    private int respuestaUsuario = -1;       // Respuesta del usuario (-1 significa no respondida).

    public ItemOM(String taxonomia, String enunciado, int tiempoTotal, List<String> opciones, int indiceCorrecto) {
        super(taxonomia, enunciado, Integer.toString(tiempoTotal));
        this.opciones = new ArrayList<>(opciones);
        this.indiceCorrecto = indiceCorrecto;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public String getRespuestaCorrecta() {
        return opciones.get(indiceCorrecto);
    }

    public int getRespuestaUsuario() {
        return respuestaUsuario;
    }

    public void setRespuestaUsuario(int respuestaUsuario) {
        this.respuestaUsuario = respuestaUsuario;
    }

    @Override
    public boolean isRespondida() {
        return respuestaUsuario != -1;
    }

    @Override
    public void limpiarRespuesta() {
        this.respuestaUsuario = -1;
    }

    @Override
    public boolean esCorrectaRespuesta() {
        return respuestaUsuario == indiceCorrecto;
    }
}