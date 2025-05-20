import javax.swing.*;
import java.util.ArrayList;

public abstract class JuegoPoker {
    protected static int numeroDeJugadores;
    protected int dineroInicial;
    protected static int dineroEnBote = 0;
    protected static ArrayList<Jugador> jugadores = new ArrayList<>();
    protected Mazo mazo;
    protected static int turnoActual;

    public JuegoPoker() {
        mazo = new Mazo();
    }

    public abstract void repartirCartas();
    public abstract int determinarGanador();
    public abstract void mostrarMano();
    public abstract void jugarRonda();
    public abstract int determinarTurnoInicial();
    public abstract void iniciarJuego();

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

    public static void apostar(Jugador jugador, int cantidad){
        if(jugador.getDinero() >= cantidad){
            jugador.setDinero(jugador.getDinero() - cantidad);
            dineroEnBote += cantidad;
            InterfazGrafica.cantidadEnBote.setText(String.valueOf(dineroEnBote));
        } else {
            JOptionPane.showMessageDialog(null, "No tienes suficientes fondos para apostar", "Error", JOptionPane.ERROR_MESSAGE);
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
