package es.gmm.psp.virtualScape.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "salas")
public class Sala {

    String nombre;
    int capacidadMin;
    int capacidadMax;
    List<String> tematicas;

    public Sala(String nombre, int capacidadMin, int capacidadMax, List<String> tematicas) {
        this.nombre = nombre;
        this.capacidadMin = capacidadMin;
        this.capacidadMax = capacidadMax;
        this.tematicas = tematicas;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidadMin() {
        return capacidadMin;
    }

    public void setCapacidadMin(int capacidadMin) {
        this.capacidadMin = capacidadMin;
    }

    public int getCapacidadMax() {
        return capacidadMax;
    }

    public void setCapacidadMax(int capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public List<String> getTematicas() {
        return tematicas;
    }

    public void setTematicas(List<String> tematicas) {
        this.tematicas = tematicas;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "nombre='" + nombre + '\'' +
                ", capacidadMin=" + capacidadMin +
                ", capacidadMax=" + capacidadMax +
                ", tematicas=" + tematicas +
                '}';
    }
}
