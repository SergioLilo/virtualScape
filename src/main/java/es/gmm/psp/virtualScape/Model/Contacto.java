package es.gmm.psp.virtualScape.Model;

public class Contacto {

    private String titular;
    private int telefono;
    private int jugadores;

    public Contacto(String titular, int telefono, int jugadores) {
        this.titular = titular;
        this.telefono = telefono;
        this.jugadores = jugadores;
    }

    public Contacto() {
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getJugadores() {
        return jugadores;
    }

    public void setJugadores(int jugadores) {
        this.jugadores = jugadores;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "titular='" + titular + '\'' +
                ", telefono=" + telefono +
                ", jugadores=" + jugadores +
                '}';
    }
}
