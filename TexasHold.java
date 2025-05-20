import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static void main(String[] args) {
        TexasHold texasHold = new TexasHold();
        texasHold.iniciarJuego();
    }
    public void inicializarJugadores() {

        numeroDeJugadores = InterfazGrafica.getCantidadDeJugadores();
        dineroInicial = InterfazGrafica.getDineroInicial();

        for (int i = 0; i < numeroDeJugadores; i++) {
            String nombre = InterfazGrafica.getNombres().get(i);
            Jugador player = new Jugador(nombre, dineroInicial);
            jugadores.add(player);
        }
        jugarRonda();
    }

    @Override
    public void iniciarJuego(){
        InterfazGrafica.intro();

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

        return 0;
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

}
