import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class TexasHold extends JuegoPoker {
    private ArrayList<Carta> cartasComunitarias;
    private int apuestaMinima;
    private int apuestaMaxima;
    private int etapaActual = 0;
    public enum EstadoJuego {
        PREFLOP, FLOP, TURN, RIVER, SHOWDOWN
    }
    private EstadoJuego estadoJuego;

    public TexasHold(){
        super();
        cartasComunitarias = new ArrayList<>();
        dineroEnBote = 0;
        apuestaMinima = 10;
    }

    public void inicializarJugadores() {
        for (int i = 0; i < cantidadDeJugadores; i++) {
            String nombre = nombres.get(i);
            Jugador player = new Jugador(nombre, dineroInicial);
            jugadores.add(player);
        }
    }

    @Override
    public void iniciarJuego(){

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
    public int determinarGanador() {
        ArrayList<Jugador> jugadoresActivos = new ArrayList<>(
                jugadores.stream()
                        .filter(Jugador::estaActivo)
                        .collect(Collectors.toList())
        );

        if (jugadoresActivos.size() == 1) {
            Jugador ganador = jugadoresActivos.get(0);
            ganador.setDinero(ganador.getDinero() + dineroEnBote);
            return jugadores.indexOf(ganador);
        }

        ArrayList<Mano> mejoresManos = new ArrayList<>();
        for (Jugador jugador : jugadoresActivos) {
            Mano mejorMano = encontrarMejorMano(jugador);
            mejoresManos.add(mejorMano);
        }

        Mano mejorManoGlobal = Collections.max(mejoresManos);

        ArrayList<Integer> indicesGanadores = new ArrayList<>();
        for (int i = 0; i < mejoresManos.size(); i++) {
            if (mejoresManos.get(i).compareTo(mejorManoGlobal) == 0) {
                Jugador jugadorGanador = jugadoresActivos.get(i);
                indicesGanadores.add(jugadores.indexOf(jugadorGanador));
            }
        }

        int gananciaPorJugador = dineroEnBote / indicesGanadores.size();
        for (int indice : indicesGanadores) {
            Jugador jugador = jugadores.get(indice);
            jugador.setDinero(jugador.getDinero() + gananciaPorJugador);
        }

        dineroEnBote = 0;
        return indicesGanadores.get(0);
    }

    private Mano encontrarMejorMano(Jugador jugador) {
        ArrayList<Carta> todasLasCartas = new ArrayList<>();
        todasLasCartas.addAll(jugador.getMano().getMano());
        todasLasCartas.addAll(cartasComunitarias);

        ArrayList<Mano> combinaciones = generarCombinaciones(todasLasCartas);
        return Collections.max(combinaciones);
    }

    private ArrayList<Mano> generarCombinaciones(ArrayList<Carta> cartas) {
        ArrayList<Mano> combinaciones = new ArrayList<>();
        combinar(cartas, 0, new ArrayList<>(), combinaciones);
        return combinaciones;
    }

    private void combinar(ArrayList<Carta> cartas, int inicio, ArrayList<Carta> actual, ArrayList<Mano> resultado) {
        if (actual.size() == 5) {
            Mano mano = new Mano();
            for (Carta carta : actual) {
                mano.agregarCarta(carta);
            }
            resultado.add(mano);
            return;
        }

        for (int i = inicio; i < cartas.size(); i++) {
            actual.add(cartas.get(i));
            combinar(cartas, i + 1, actual, resultado);
            actual.remove(actual.size() - 1);
        }
    }


    @Override
    public void mostrarMano() {
        ArrayList<Carta> mano = jugadores.get(turnoActual).getMano().getMano();
        manoTexas1.setIcon(mano.get(0).getImagen());
        manoTexas2.setIcon(mano.get(1).getImagen());
    }

    public void mostrarCartasComunitarias() {
        ImageIcon cartaVolteada = new ImageIcon(RUTA_ARCHIVOS_VISUALES + "Cubierta Carta.png");
        Image imagenVolteadaEscalada = cartaVolteada.getImage().getScaledInstance(120, 170, Image.SCALE_SMOOTH);
        ImageIcon iconoVolteado = new ImageIcon(imagenVolteadaEscalada);
        switch (etapaActual) {
            case 0:
                riverTexas1.setIcon(iconoVolteado);
                riverTexas2.setIcon(iconoVolteado);
                riverTexas3.setIcon(iconoVolteado);
                riverTexas4.setIcon(iconoVolteado);
                riverTexas5.setIcon(iconoVolteado);
                break;
            case 1:
                riverTexas1.setIcon(escalarCarta(cartasComunitarias.get(0)));
                riverTexas2.setIcon(escalarCarta(cartasComunitarias.get(1)));
                riverTexas3.setIcon(escalarCarta(cartasComunitarias.get(2)));
                riverTexas4.setIcon(iconoVolteado);
                riverTexas5.setIcon(iconoVolteado);
                break;
            case 2:
                riverTexas1.setIcon(escalarCarta(cartasComunitarias.get(0)));
                riverTexas2.setIcon(escalarCarta(cartasComunitarias.get(1)));
                riverTexas3.setIcon(escalarCarta(cartasComunitarias.get(2)));
                riverTexas4.setIcon(escalarCarta(cartasComunitarias.get(3)));
                riverTexas5.setIcon(iconoVolteado);
                break;
            case 3:
                riverTexas1.setIcon(escalarCarta(cartasComunitarias.get(0)));
                riverTexas2.setIcon(escalarCarta(cartasComunitarias.get(1)));
                riverTexas3.setIcon(escalarCarta(cartasComunitarias.get(2)));
                riverTexas4.setIcon(escalarCarta(cartasComunitarias.get(3)));
                riverTexas5.setIcon(escalarCarta(cartasComunitarias.get(4)));
                break;
        }
    }

    public static ImageIcon escalarCarta(Carta carta) {
        Image imagenOriginal = carta.getImagen().getImage();
        Image imagenEscalada = imagenOriginal.getScaledInstance(120, 170, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    public void primeraRondaDeApuestas(){
        Jugador jugador = jugadores.get(turnoActual);
        apostar(jugador,apuestaMinima);
        turnoActual = cambiarTurno();
        actualizarTablero();
        jugador = jugadores.get(turnoActual);
        int segundaApuesta = apuestaMinima * 2;
        apostar(jugador,segundaApuesta);
        turnoActual = cambiarTurno();
        jugador = jugadores.get(turnoActual);

    }

    @Override
    public void jugarRonda() {
        estadoJuego = EstadoJuego.PREFLOP;
        repartirCartas();
        mostrarMano();
        iniciarRondaDeApuestas(); // Espera interacción del usuario
    }

    private void iniciarRondaDeApuestas() {
        reiniciarApuestas();
        apuestaActual = 0;
        turnoActual = 0;
        JuegoPoker.actualizarTablero();
        // Los botones ahora manejan el flujo
    }

    private void avanzarEtapa() {
        switch (estadoJuego) {
            case PREFLOP:
                estadoJuego = EstadoJuego.FLOP;
                repartirFlop();
                mostrarCartasComunitarias();
                iniciarRondaDeApuestas();
                break;
            case FLOP:
                estadoJuego = EstadoJuego.TURN;
                repartirTurn();
                mostrarCartasComunitarias();
                iniciarRondaDeApuestas();
                break;
            case TURN:
                estadoJuego = EstadoJuego.RIVER;
                repartirRiver();
                mostrarCartasComunitarias();
                iniciarRondaDeApuestas();
                break;
            case RIVER:
                estadoJuego = EstadoJuego.SHOWDOWN;
                determinarGanador();
                break;
        }
    }

    public void subir(Jugador jugador, int cantidad) {
        int total = apuestaActual + cantidad;
        if (jugador.getDinero() >= total) {
            jugador.setDinero(jugador.getDinero() - total);
            jugador.setApuestaActual(total);
            apuestaActual = total;
            dineroEnBote += total;
            JuegoPoker.actualizarTablero(); // Actualizar la interfaz
        }
    }

    public static void igualar(Jugador jugador, int apuestaMaxima) {
        int cantidad = apuestaMaxima - jugador.getApuestaActual();
        if (jugador.getDinero() >= cantidad) {
            jugador.setDinero(jugador.getDinero() - cantidad);
            jugador.setApuestaActual(apuestaMaxima);
            dineroEnBote += cantidad;
            JuegoPoker.actualizarTablero();
        }
    }

    public static void fold(Jugador jugador) {
        jugador.setActivo(false);
        JOptionPane.showMessageDialog(null, jugador.getNombre() + " ha hecho fold.");
    }

    public static void pasar() {
        turnoActual = (turnoActual + 1) % jugadores.size();
        JuegoPoker.actualizarTablero();
    }

    public void avanzarTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();
        JuegoPoker.actualizarTablero();

        // Si todos han apostado, avanzar etapa (Flop, Turn, River, etc.)
        if (todosHanApostado()) {
            avanzarEtapa();
        }
    }

    private boolean todosHanApostado() {
        for (Jugador jugador : jugadores) {
            if (jugador.estaActivo() && jugador.getApuestaActual() < apuestaActual) {
                return false;
            }
        }
        return true;
    }

}
