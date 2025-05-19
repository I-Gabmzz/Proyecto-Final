import java.util.ArrayList;

public abstract class JuegoPoker {
    protected int numeroDeJugadores;
    protected int dineroInicial;
    protected ArrayList<Jugador> jugadores;
    protected Mazo mazo;
    protected int turnoActual;

    public JuegoPoker() {
        jugadores = new ArrayList<>();
        mazo = new Mazo();
    }

    public abstract void repartirCartas();
    public abstract int determinarGanador();
    public abstract void mostrarMano();
    public abstract void jugarRonda();
    public abstract int determinarTurnoInicial();
    public abstract void iniciarJuego(int numJugadores);

    public void mostrarManos(){
        //Mostrar las cartas de manera grafica
    }

    public void pasar(){
        turnoActual = (turnoActual + 1) % jugadores.size();
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void apostar(Jugador jugador, int cantidad){
        if(jugador.getDinero() >= cantidad){
            jugador.setDinero(jugador.getDinero() - cantidad);
            //Mostrar la apuesta del jugador de manera grafica
        } else {
            //Mostrar un mensaje de que el jugador no tiene fondos suficientes
        }
    }

    public void igualar(Jugador jugador, int apuestaMaxima){
        int cantidadApostar = apuestaMaxima - jugador.getApuestaActual();
        if(jugador.getDinero() >= cantidadApostar){
            jugador.setDinero(jugador.getDinero() - cantidadApostar);
            jugador.setApuestaActual(apuestaMaxima);
            //Mostrar mensaje con interfaz donde se iguala la apuesta
        } else {
            //Mostrar mensaje con interfaz donde el jugador no puede igualar la apuesta
        }
    }

    public void subir(Jugador jugador, int cantidad, int apuestaMaxima){
        int cantidadTotal = apuestaMaxima + cantidad;
        if(jugador.getDinero() >= cantidadTotal){
            jugador.setDinero(jugador.getDinero() - cantidadTotal);
            jugador.setApuestaActual(cantidadTotal);
           //Mostrar mensaje con interfaz donde el jugador sube la apuesta
        } else {
            //Mostrar mensaje con interfaz donde el jugador no puede subir la apuesta por fondos insuficientes
        }
    }

    public void fold(Jugador jugador){
        jugador.setActivo(false);
        //Mostrar mensaje con interfaz que el jugador ha hecho fold
    }
}
