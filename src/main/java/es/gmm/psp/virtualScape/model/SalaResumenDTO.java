package es.gmm.psp.virtualScape.model;

public class SalaResumenDTO {
    private String sala;
    private int totalReservas;

    public SalaResumenDTO(String sala, int totalReservas) {
        this.sala = sala;
        this.totalReservas = totalReservas;
    }

    // Getters
    public String getSala() {
        return sala;
    }

    public int getTotalReservas() {
        return totalReservas;
    }
}
