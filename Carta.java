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

    private String generarRuta() {
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

        String nombreValor;
        if (valor == 1) {
            nombreValor = "As";
        }else if (valor == 11) {
            nombreValor = "Joto";
        }else if (valor == 12) {
            nombreValor = "Queen";
        }else if (valor == 13){
            nombreValor = "King";
        }else{
            nombreValor = String.valueOf(valor);
        }
        return "C:\\Users\\14321\\IdeaProjects\\Proyecto-Final\\" + nombreFigura + "\\" + nombreValor + ".png";
        //return "C:\\Users\\PC OSTRICH\\IdeaProjects\\Proyecto-Final\\" + nombreFigura + "\\" + nombreValor + ".png";
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
