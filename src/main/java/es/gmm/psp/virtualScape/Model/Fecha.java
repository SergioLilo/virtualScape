package es.gmm.psp.virtualScape.Model;

public class Fecha {


    private int diaReserva;
    private  int horaReserva;

    public Fecha(int diaReserva, int horaReserva) {
        this.diaReserva = diaReserva;
        this.horaReserva = horaReserva;
    }

    public int getDiaReserva() {
        return diaReserva;
    }

    public void setDiaReserva(int diaReserva) {
        this.diaReserva = diaReserva;
    }

    public int getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(int horaReserva) {
        this.horaReserva = horaReserva;
    }
}
