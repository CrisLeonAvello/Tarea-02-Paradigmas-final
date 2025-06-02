package Backend;

public abstract class Item {
    private final String enunciado;
    private final String taxonomia;
    private final String tiempoTotal;

    public Item(String taxonomia, String enunciado, String tiempoTotal) {
        if (taxonomia == null || taxonomia.isBlank()) {
            throw new IllegalArgumentException("Taxonomía no puede estar vacía.");
        }
        if (enunciado == null || enunciado.isBlank()) {
            throw new IllegalArgumentException("Enunciado no puede estar vacío.");
        }
        if (tiempoTotal == null || tiempoTotal.isBlank()) {
            throw new IllegalArgumentException("Tiempo total no puede estar vacío.");
        }

        this.taxonomia = taxonomia.trim();
        this.enunciado = enunciado.trim();
        this.tiempoTotal = tiempoTotal.trim();
    }

    public String getTaxonomia() {
        return taxonomia;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getTiempoTotal() {
        return tiempoTotal;
    }

    public abstract boolean isRespondida();

    public abstract boolean esCorrectaRespuesta();

    // Nuevo método abstracto que deben implementar las subclases.
    public abstract void limpiarRespuesta();
}