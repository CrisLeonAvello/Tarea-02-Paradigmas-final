package Backend;

public class ResultadosPrueba {
    private final int preguntasCorrectas;
    private final int preguntasIncorrectas;
    private final int totalPreguntas;
    private double porcentajeAcierto;
    private String tiempoUtilizado;

    // Constructor modificado para coincidir con tu uso en la clase Prueba
    public ResultadosPrueba(int totalPreguntas, int preguntasCorrectas) {
        this.totalPreguntas = totalPreguntas;
        this.preguntasCorrectas = preguntasCorrectas;
        this.preguntasIncorrectas = totalPreguntas - preguntasCorrectas;
        this.tiempoUtilizado = "N/A"; // Puedes actualizarlo después si es necesario
        calcularPorcentajeAcierto();
    }

    private void calcularPorcentajeAcierto() {
        if (totalPreguntas > 0) {
            this.porcentajeAcierto = ((double) preguntasCorrectas / totalPreguntas) * 100;
        } else {
            this.porcentajeAcierto = 0.0;
        }
    }

    // Getters
    public int getPreguntasCorrectas() {
        return preguntasCorrectas;
    }

    public int getPreguntasIncorrectas() {
        return preguntasIncorrectas;
    }

    public int getTotalPreguntas() {
        return totalPreguntas;
    }

    public String getPorcentajeAciertoFormateado() {
        return String.format("%.2f%%", porcentajeAcierto);
    }

    public double getPorcentajeAcierto() {
        return porcentajeAcierto;
    }

    public String getTiempoUtilizado() {
        return tiempoUtilizado;
    }

    public void setTiempoUtilizado(String tiempoUtilizado) {
        this.tiempoUtilizado = tiempoUtilizado;
    }

    public String getEstadoAprobacion() {
        return porcentajeAcierto >= 60.0 ? "APROBADO" : "REPROBADO";
    }

    public String getCalificacionFormateada() {
        return String.format("%.2f/10.00", porcentajeAcierto / 10.0);
    }

    public String getColorResultado() {
        if (porcentajeAcierto >= 80) {
            return "#2ecc71"; // Verde
        } else if (porcentajeAcierto >= 60) {
            return "#f1c40f"; // Amarillo
        } else {
            return "#e74c3c"; // Rojo
        }
    }
}