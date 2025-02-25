package es.gmm.psp.virtualScape.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ConsultaEspecial {

    private  String   sala;
    private  int dia;
    private int hora;
    private Contacto contacto;
    private  int jugadores;

    public ConsultaEspecial(String sala, int dia, int hora, Contacto contacto, int jugadores) {
        this.sala = sala;
        this.dia = dia;
        this.hora = hora;
        this.contacto = contacto;
        this.jugadores = jugadores;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
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
}
