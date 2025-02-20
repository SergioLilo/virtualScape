package es.gmm.psp.virtualScape.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservas")
public class Reserva {


    @Id
    private String id;
    private String nombreSala;
    private  Fecha fecha;
    private  Contacto contacto;
    private int jugadores;

    public Reserva(String nombreSala, Fecha fecha, Contacto contacto) {
        this.nombreSala = nombreSala;
        this.fecha = fecha;
        this.contacto = contacto;
    }

    public Reserva() {
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public int getJugadores() {
        return jugadores;
    }

    public void setJugadores(int jugadores) {
        this.jugadores = jugadores;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "nombreSala='" + nombreSala + '\'' +
                ", fecha=" + fecha +
                ", contacto=" + contacto +
                ", numJugadores=" + jugadores +
                '}';
    }
}
