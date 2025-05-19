import java.util.ArrayList;
import java.util.Collections;

public class Mazo {
    private ArrayList<Carta> cartas;

    public Mazo() {
        cartas = new ArrayList<>();
    }

    public void crearMazo() {
        String[] figuras = {"♥", "♦", "♣", "♠"};
        for (String figura : figuras) {
            for (int i = 1; i <= 13; i++) {
                String color = (figura.equals("♥") || figura.equals("♦")) ? "rojo" : "negro";
                Carta carta = new Carta(i, figura, color);
                cartas.add(carta);
            }
        }
    }

    public void revolverMazo(){
        Collections.shuffle(cartas);
    }

    public ArrayList<Carta> tomarCartas(int cantidadCartas) {
        ArrayList<Carta> cartasTomadas = new ArrayList<>();
        for(int i = 0; i < cantidadCartas; i++) {
            Carta carta = cartas.get(i);
            cartasTomadas.add(carta);
        }
        return cartasTomadas;
    }

    public Carta tomarCarta() {
        if (!cartas.isEmpty()) {
            return cartas.removeFirst();
        } else {
            return null;
        }
    }
}
