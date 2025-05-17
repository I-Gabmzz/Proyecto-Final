public class Carta {
    private int valor;
    private String color;
    private String figura;

    public Carta(int valor, String color, String figura) {
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

}
