package Backend;

public class ItemVF extends Item {
    private final boolean respuestaCorrecta;    // Respuesta correcta para el ítem (true/false).
    private Boolean respuestaUsuario;          // Respuesta del usuario (null si no ha respondido).

    public ItemVF(String taxonomia, String enunciado, int tiempoTotal, boolean respuestaCorrecta) {
        super(taxonomia, enunciado, Integer.toString(tiempoTotal));
        this.respuestaCorrecta = respuestaCorrecta;
        this.respuestaUsuario = null;
    }

    public Boolean getRespuestaUsuario() {
        return respuestaUsuario;
    }

    public void setRespuestaUsuario(Boolean respuestaUsuario) {
        this.respuestaUsuario = respuestaUsuario;
    }

    public boolean getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    @Override
    public boolean esCorrectaRespuesta() {
        return respuestaUsuario != null && respuestaUsuario == respuestaCorrecta;
    }

    @Override
    public boolean isRespondida() {
        return respuestaUsuario != null;
    }

    @Override
    public void limpiarRespuesta() {
        this.respuestaUsuario = null;
    }
}