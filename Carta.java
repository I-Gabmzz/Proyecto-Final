import javax.swing.*;
import java.awt.*;

public class Carta implements Comparable<Carta> {
    private int valor;
    private String color;
    private String figura;
    private String ruta;

    public Carta(int valor, String figura, String color) {
        this.valor = valor;
        this.color = color;
        this.figura = figura;
    }

    public ImageIcon getImagen() {
        String nombreFigura;
        switch (figura) {
            case "♥":
                nombreFigura = "Corazones";
                break;
            case "♦":
                nombreFigura = "Diamantes";
                break;
            case "♣":
                nombreFigura = "Treboles";
                break;
            case "♠":
                nombreFigura = "Picas";
                break;
            default:
                nombreFigura = "";
                break;
        }

        String nombreValor = switch(valor) {
            case 1 -> "As";
            case 2 -> "Dos";
            case 3 -> "Tres";
            case 4 -> "Cuatro";
            case 5 -> "Cinco";
            case 6 -> "Seis";
            case 7 -> "Siete";
            case 8 -> "Ocho";
            case 9 -> "Nueve";
            case 10 -> "Diez";
            case 11 -> "Joto";
            case 12 -> "Queen";
            case 13 -> "King";
            default -> String.valueOf(valor);
        };

        String ruta = "C:\\Users\\14321\\IdeaProjects\\Proyecto-Final\\Cartas\\" + nombreFigura + "\\" + nombreValor + ".png";
        //String ruta = "C:\\Users\\PC OSTRICH\\Proyecto-Final\\Cartas\\" + nombreFigura + "\\" + nombreValor + ".png";
        ImageIcon icono = new ImageIcon(ruta);
        Image imagenEscalada = icono.getImage().getScaledInstance(250, 350, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    public int getValor() {
        return valor;
    }
    public String getColor() {
        return color;
    }
    public String getFigura() {
        return figura;
    }

    public String toString() {
        return valor + " " + figura + " " + color;
    }

    @Override
    public int compareTo(Carta otraCarta) {
        return Integer.compare(this.valor, otraCarta.getValor());
    }

}