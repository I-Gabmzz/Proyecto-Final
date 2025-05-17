public class Carta {
    private int valor;
    private String color;
    private String figura;

    public Carta(int valor, String figura, String color) {
        this.valor = valor;
        this.color = color;
        this.figura = figura;
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

}
