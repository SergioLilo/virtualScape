package es.gmm.psp.virtualScape.Model;

public class ApiRespuesta {

    boolean exito;
    String mensaje;
    String idGenerado;

    public ApiRespuesta() {
    }

    public ApiRespuesta(boolean exito, String mensaje, String idGenerado) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.idGenerado = idGenerado;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getIdGenerado() {
        return idGenerado;
    }

    public void setIdGenerado(String idGenerado) {
        this.idGenerado = idGenerado;
    }
}
