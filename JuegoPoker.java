import javax.swing.*;
import java.util.ArrayList;

public abstract class JuegoPoker {
    protected static int numeroDeJugadores;
    protected static int dineroInicial;
    protected static int dineroEnBote = 0;
    protected static ArrayList<Jugador> jugadores = new ArrayList<>();
    protected Mazo mazo;
    protected static int turnoActual = 0;

    public JuegoPoker() {
        mazo = new Mazo();
    }

    public abstract void repartirCartas();
    public abstract int determinarGanador();
    public abstract void mostrarMano();
    public abstract void jugarRonda();
    public abstract void iniciarJuego();


    public static void actualizarTablero() {
        InterfazGrafica.avisoDeTurno.setText("Es turno de: " + JuegoPoker.jugadores.get(JuegoPoker.turnoActual).getNombre());
        InterfazGrafica.dineroEnMano.setText("$" + JuegoPoker.jugadores.get(JuegoPoker.turnoActual).getDinero());
        InterfazGrafica.manoActual.setText("Mano de " + JuegoPoker.jugadores.get(JuegoPoker.turnoActual).getNombre());
    }

    public void mostrarManos(){

    }

    public void pasar(){
        turnoActual = (turnoActual + 1) % jugadores.size();
        InterfazGrafica.avisoDeTurno.setText("Es turno de: " + JuegoPoker.jugadores.get(JuegoPoker.turnoActual).getNombre());
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
            InterfazGrafica.cantidadEnBote.setText("$ " + dineroEnBote);
            InterfazGrafica.dineroEnMano.setText("$" + jugador.getDinero());
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

    public static void fold(Jugador jugador){
        jugador.setActivo(false);
        JOptionPane.showMessageDialog(null, "Has hecho fold, pierdes la ronda", "Fold", JOptionPane.INFORMATION_MESSAGE);
    }
}
