import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

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
        texasHold.repartirCartas();
        texasHold.mostrarMano();
        texasHold.mostrarCartasComunitarias();
    }

    public void inicializarJugadores() {
        for (int i = 0; i < cantidadDeJugadores; i++) {
            String nombre = nombres.get(i);
            Jugador player = new Jugador(nombre, dineroInicial);
            jugadores.add(player);
        }
        jugarRonda();
    }

    @Override
    public void iniciarJuego(){
        //intro();
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

    public void manejarAccionJugador(String accion, int cantidad) {
        Jugador jugadorActual = jugadores.get(turnoActual);

        switch(accion) {
            case "PASAR":
                pasar();
                break;
            case "APOSTAR":
                apostar(jugadorActual, cantidad);
                break;
            case "IGUALAR":
                igualar(jugadorActual, apuestaMaxima);
                break;
            case "SUBIR":
                subir(jugadorActual, cantidad, apuestaMaxima);
                break;
            case "FOLD":
                fold(jugadorActual);
                break;
        }
        turnoActual = (turnoActual + 1) % jugadores.size();
        actualizarTablero();
    }



    @Override
    public void mostrarMano() {
        ArrayList<Carta> mano = jugadores.get(turnoActual).getMano().getMano();
        manoTexas1.setIcon(mano.get(0).getImagen());
        manoTexas2.setIcon(mano.get(1).getImagen());
    }

    public void mostrarCartasComunitarias() {
        ImageIcon river1 = new ImageIcon(cartasComunitarias.get(0).getImagen().getImage()
                .getScaledInstance(120, 170, Image.SCALE_SMOOTH));
        ImageIcon river2 = new ImageIcon(cartasComunitarias.get(1).getImagen().getImage()
                .getScaledInstance(120, 170, Image.SCALE_SMOOTH));
        ImageIcon river3 = new ImageIcon(cartasComunitarias.get(2).getImagen().getImage()
                .getScaledInstance(120, 170, Image.SCALE_SMOOTH));
        ImageIcon river4 = new ImageIcon(cartasComunitarias.get(3).getImagen().getImage()
                .getScaledInstance(120, 170, Image.SCALE_SMOOTH));
        ImageIcon river5 = new ImageIcon(cartasComunitarias.get(4).getImagen().getImage()
                .getScaledInstance(120, 170, Image.SCALE_SMOOTH));

        riverTexas1.setIcon(river1);
        riverTexas2.setIcon(river2);
        riverTexas3.setIcon(river3);
        riverTexas4.setIcon(river4);
        riverTexas5.setIcon(river5);
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

}
