public class Jugador {
    private String nombre;
    private Mano mano;
    private int dinero;
    private boolean sigueActivo;
    private boolean yaHizoFold;
    private int apuestaActual;

    public Jugador(String nombre, int dinero , Mano mano){
        this.nombre = nombre;
        this.dinero = dinero;
        this.mano = mano;
        this.sigueActivo = true;
        this.yaHizoFold = false;
        this.apuestaActual = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public Mano getMano() {
        return mano;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public boolean estaActivo() {
        return sigueActivo && !yaHizoFold;
    }

    public boolean haHechoFold() {
        return yaHizoFold;
    }

    public int getApuestaActual() {
        return apuestaActual;
    }

    public void setApuestaActual(int apuestaActual) {
        this.apuestaActual = apuestaActual;
    }

    public void setActivo(boolean activo) {
        this.sigueActivo = activo;
    }
}
