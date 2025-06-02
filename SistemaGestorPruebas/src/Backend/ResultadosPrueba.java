package Backend;

public class ResultadosPrueba {
    private final int totalPreguntas;
    private final int preguntasCorrectas;

    public ResultadosPrueba(int totalPreguntas, int preguntasCorrectas) {
        this.totalPreguntas = totalPreguntas;
        this.preguntasCorrectas = preguntasCorrectas;
    }

    public int getTotalPreguntas() {
        return totalPreguntas;
    }

    public int getPreguntasCorrectas() {
        return preguntasCorrectas;
    }
}