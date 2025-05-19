import java.util.*;

public class Mano implements Comparable<Mano> {
    private ArrayList<Carta> mano;

    public Mano() {
        mano = new ArrayList<>();
    }

    public ArrayList<Carta> getMano(){
        return mano;
    }

    public Carta eliminarCarta(int posicion){
        return mano.remove(posicion);
    }

    public void agregarCarta(Carta carta) {
        mano.add(carta);
    }

    public int getValorMayor(){
        return Collections.max(mano).getValor();
    }

    public void acomodarMano(){
        Collections.sort(mano);
    }

    public HashMap<Integer,Integer> obtenerFrecuencias(){
        HashMap<Integer, Integer> conteo = new HashMap<>();
        for (Carta carta : mano) {
            int valor = carta.getValor();
            conteo.put(valor, conteo.getOrDefault(valor, 0) + 1);
        }
        return conteo;
    }

    public boolean hayPar(){
        return obtenerFrecuencias()
                 .values().stream()
                .anyMatch(contador -> contador == 2);
    }

    public boolean hayDosPares() {
        return obtenerFrecuencias()
                .values().stream()
                .filter(contador -> contador == 2)
                .count() >= 2;
    }

    public boolean hayTercia(){
        return obtenerFrecuencias()
                .values().stream()
                .anyMatch(contador -> contador == 3);
    }

    public boolean hayPoker(){
        return obtenerFrecuencias()
                .values().stream()
                .anyMatch(contador -> contador == 4);
    }

    public boolean esMismaFigura(){
        return mano.stream()
                .map(Carta::getFigura)
                .distinct()
                .count() == 1;
    }

    public int cuantasRepetidasDe(Carta carta){
        return (int) mano.stream()
                .filter(otraCarta -> otraCarta.getValor() == carta.getValor())
                .count();
    }

    public boolean esFull(){
        return hayPar() && hayTercia();
    }

    public Carta getCartaMasAlta(){
        return Collections.max(mano);
    }

    public boolean esEscalera() {
        acomodarMano();

        List<Integer> valores = new ArrayList<>();
        for (int i = 0; i < mano.size(); i++) {
            valores.add(mano.get(i).getValor());
        }
        if (valores.get(0) == 1 &&
                valores.get(1) == 10 &&
                valores.get(2) == 11 &&
                valores.get(3) == 12 &&
                valores.get(4) == 13) {
            return true;
        }
        for (int i = 0; i < valores.size() - 1; i++) {
            if (valores.get(i + 1) - valores.get(i) != 1) {
                return false;
            }
        }
        return true;
    }

    public boolean esEscaleraReal() {
        if (!esEscaleraDeColor()) {
            return false;
        }
        List<Integer> valores = new ArrayList<>();
        for (int i = 0; i < mano.size(); i++) {
            valores.add(mano.get(i).getValor());
        }
        Collections.sort(valores);

        return valores.get(0) == 1 &&
                valores.get(1) == 10 &&
                valores.get(2) == 11 &&
                valores.get(3) == 12 &&
                valores.get(4) == 13;

    }

    public boolean esEscaleraDeColor(){
        return esEscalera() && esMismaFigura();
    }

    private int evaluarMano() {
        //Escalera real
        if (esEscaleraReal()) return 10;
        //Escalera color
        if (esEscaleraDeColor()) return 9;
        //Poker
        if (hayPoker()) return 8;
        //Full house
        if (esFull()) return 7;
        //Flush
        if (esMismaFigura()) return 6;
        //Escalera normal
        if (esEscalera()) return 5;
        //Tercia
        if (hayTercia()) return 4;
        //Dos pares
        if (hayDosPares()) return 3;
        //Par
        if (hayPar()) return 2;
        //Carta alta
        return 1;
    }

    public boolean contieneAs() {
        return mano.stream().anyMatch(carta -> carta.getValor() == 1);
    }

    @Override
    public int compareTo(Mano otraMano) {
        int primeraMano = evaluarMano();
        int segundaMano = otraMano.evaluarMano();

        if (primeraMano != segundaMano) return Integer.compare(primeraMano, segundaMano);

        switch (primeraMano) {
            case 10: return 0; // Escalera real
            case 9:  return compararPorCartaMasAlta(otraMano); // Escalera de color
            case 8:  //Poker
            case 7:  // Full
            case 6:  return compararFlush(otraMano); // Flush
            case 5:  return compararPorCartaMasAlta(otraMano); // Escalera
            case 4:  // Tercia
            case 3:  // Dos pares
            case 2:  // Un par
            case 1:  return compararPorCartaMasAlta(otraMano); // Carta alta
        }
    }

    private int compararPorCartaMasAlta(Mano otraMano){
        return Integer.compare(this.getValorMayor(), otraMano.getValorMayor());
    }

    private int compararFlush(Mano otraMano) {
        if (this.contieneAs() && !otraMano.contieneAs()) {
            return 1;
        }
        if (!this.contieneAs() && otraMano.contieneAs()) {
            return -1;
        }
        List<Carta> primeraMano = new ArrayList<>(this.mano);
        List<Carta> segundaMano = new ArrayList<>(otraMano.mano);

        primeraMano.sort(Collections.reverseOrder());
        segundaMano.sort(Collections.reverseOrder());

        for (int i = 0; i < primeraMano.size(); i++) {
            int valorA = primeraMano.get(i).getValor();
            int valorB = segundaMano.get(i).getValor();
            if (valorA != valorB) {
                return Integer.compare(valorA, valorB);
            }
        }
        return 0;
    }



}