import java.util.ArrayList;

public abstract class JuegoPoker {
    private int numeroDeJugadores;
    private int dineroInicial;
    private ArrayList<Mano> manoJugadores;
    private Mazo mazo;
    private int turnoActual;

    public JuegoPoker() {
        manoJugadores = new ArrayList<>();
    }

    public abstract void repartirCartas();
    public abstract int determinarGanador();
    public abstract void mostrarMano();
    public abstract void jugarRonda();
    public abstract int determinarTurnoInicial();
    public abstract void iniciarJuego(int numJugadores);

    public void mostrarManos(){

    }

    public void pasar(){

    }

    public void apostar(){

    }

    public void igualar(){

    }

    public void subir(){

    }

    public void fold(){

    }
}
