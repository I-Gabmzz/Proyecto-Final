import java.util.ArrayList;
import java.util.Collections;

public class Mazo {
    private ArrayList<Carta> cartas;

    public Mazo() {
        cartas = new ArrayList<>();
    }

    public void crearMazo() {
        String[] figuras = {"♥", "♣", "♦", "♠"};
        for (int i = 0; i < figuras.length; i++) {
            for (int j = 1; j < 14; j++) {
                String color;
                switch (figuras[i]) {
                    case "♥":
                    case "♦":
                        color = "rojo";
                        break;
                    case "♣":
                    case "♠":
                        color = "negro";
                        break;
                    default:
                        color = "";
                }
                Carta carta = new Carta(j, figuras[i], color);
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
            return cartas.remove(0);
        } else {
            return null;
        }
    }
}
