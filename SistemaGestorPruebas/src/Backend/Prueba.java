package Backend;

import java.util.List;

public class Prueba {
    private final List<Item> items;
    private int indiceActual;

    public Prueba(List<Item> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("La lista de ítems no puede estar vacía.");
        }
        this.items = items;
        this.indiceActual = 0; // Iniciamos desde el primer ítem.
    }

    public Item getItemActual() {
        return items.get(indiceActual); // Devuelve el ítem actual.
    }

    public int getIndiceActual() {
        return indiceActual;
    }

    public boolean avanzar() {
        if (indiceActual < items.size() - 1) {
            indiceActual++;
            return true;
        }
        return false; // No se puede avanzar más allá del último ítem.
    }

    public boolean retroceder() {
        if (indiceActual > 0) {
            indiceActual--;
            return true;
        }
        return false; // No se puede retroceder más allá del primer ítem.
    }

    public boolean isUltimoItem() {
        return indiceActual == items.size() - 1;
    }

    public boolean isPrimerItem() {
        return indiceActual == 0;
    }

    public List<Item> getItems() {
        return items;
    }

    // Nuevo método para calcular los resultados de la prueba
    public ResultadosPrueba obtenerResultados() {
        int totalPreguntas = items.size();
        int preguntasCorrectas = 0;

        for (Item item : items) {
            if (item.isRespondida() && item.esCorrectaRespuesta()) {
                preguntasCorrectas++;
            }
        }

        return new ResultadosPrueba(totalPreguntas, preguntasCorrectas);
    }
}