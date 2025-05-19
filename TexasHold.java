import java.util.ArrayList;

public class TexasHold extends JuegoPoker {
    private ArrayList<Carta> cartasComunitarias;
    private int apuestaMinima;
    private int apuestaMaxima;

    public TexasHold(){
        super();
        cartasComunitarias = new ArrayList<>();
        dineroEnBote = 0;
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

    public int getDineroEnBote() {
        return dineroEnBote;
    }

    @Override
    public void repartirCartas() {
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

    public void rondaDeApuestas(){

    }

    @Override
    public void jugarRonda() {
        repartirCartas();
        rondaDeApuestas();

        // Flop
        if (jugadoresActivos() > 1) {
            repartirFlop();
            rondaDeApuestas();
        }

        // Turn
        if (jugadoresActivos() > 1) {
            repartirTurn();
            rondaDeApuestas();
        }

        // River
        if (jugadoresActivos() > 1) {
            repartirRiver();
            rondaDeApuestas();
        }

        // Showdown
        if (jugadoresActivos() > 1) {
            determinarGanador();
        } else {
            for (Jugador jugador : jugadores) {
                if (jugador.estaActivo()) {
                    jugador.setDinero(jugador.getDinero() + dineroEnBote);
                    break;
                }
            }
        }
        dineroEnBote = 0;
        reiniciarApuestas();
    }
    @Override
    public int determinarTurnoInicial(){
        return 0;
    }
    @Override
    public void iniciarJuego(int numJugadores){

    }



}
