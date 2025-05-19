import java.util.ArrayList;

public class TexasHold extends JuegoPoker {
    private ArrayList<Carta> cartasComunitarias;
    private int bote;
    private int apuestaMinima;
    private int apuestaMaxima;

    public TexasHold(){
        super();
        cartasComunitarias = new ArrayList<>();
        bote = 0;
        apuestaMinima = 10;
    }

    public void repartirFlop() {
        if (cartasComunitarias.isEmpty()) {
            cartasComunitarias.addAll(mazo.tomarCartas(3));
        }
    }

    public void repartirTurn() {
        if (cartasComunitarias.size() == 3) {
            cartasComunitarias.add(mazo.tomarCarta());
        }
    }

    public void repartirRiver() {
        if (cartasComunitarias.size() == 4) {
            cartasComunitarias.add(mazo.tomarCarta());
        }
    }

    private int jugadoresActivos() {
        return (int) jugadores.stream().filter(Jugador::estaActivo).count();
    }

    private void reiniciarApuestas() {
        jugadores.forEach(j -> j.setApuestaActual(0));
    }

    public ArrayList<Carta> getCartasComunitarias() {
        return cartasComunitarias;
    }

    public int getBote() {
        return bote;
    }

    @Override
    public void repartirCartas() {
        // Repartir 2 cartas a cada jugador
        for (Jugador jugador : jugadores) {
            ArrayList<Carta> cartas = mazo.tomarCartas(2);
            jugador.getMano().getMano().clear();
            jugador.getMano().getMano().addAll(cartas);
        }
        cartasComunitarias.clear();
    }

    @Override
    public int determinarGanador(){

    }
    @Override
    public void mostrarMano(){

    }
    @Override
    public void jugarRonda(){

    }
    @Override
    public int determinarTurnoInicial(){

    }
    @Override
    public void iniciarJuego(int numJugadores){

    }

}
