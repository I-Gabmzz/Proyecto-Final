import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public abstract class JuegoPoker {
    static Clip SONIDO_FONDO;
    static String RUTA_ARCHIVOS_VISUALES = "RecursosVisuales\\";
    static String RUTA_ARCHIVOS_FICHAS = "RecursosVisuales\\Fichas\\";


    protected static int cantidadDeJugadores = 2;
    protected static int dineroEnBote;
    protected static int turnoActual;
    protected static int modoDeJuego;
    protected static int dineroInicial;
    protected static Mazo mazo = new Mazo();
    protected static ArrayList<Jugador> jugadores = new ArrayList<>();
    protected static ArrayList<String> nombres = new ArrayList<>();


    private static JFrame ventanaPrincipal;
    public static JTextField cantidadEnBote = new JTextField(1);
    public static JTextField avisoDeTurno = new JTextField(1);
    public static JTextField dineroEnMano = new JTextField(1);
    public static JTextField manoActual = new JTextField(1);
    public static JLabel manoTexas1 = new JLabel(), manoTexas2 = new JLabel();
    public static JLabel riverTexas1, riverTexas2, riverTexas3;
    public static JLabel riverTexas4, riverTexas5;


    public abstract void repartirCartas();
    public abstract int determinarGanador();
    public abstract void mostrarMano();
    public abstract void jugarRonda();
    public abstract void iniciarJuego();

    public static void intro() {
        ventanaPrincipal = new JFrame("Golden Dynasty");
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventanaPrincipal.setResizable(false);
        ventanaPrincipal.setUndecorated(true);

        ImageIcon gifIcon = new ImageIcon(RUTA_ARCHIVOS_VISUALES + "intro.gif");
        JLabel gifLabel = new JLabel(gifIcon);
        ventanaPrincipal.setContentPane(gifLabel);
        ventanaPrincipal.setVisible(true);
        reproducirSonidoFondo(RUTA_ARCHIVOS_VISUALES + "Intro.wav");
        new Timer(6000, e -> {
            ((Timer) e.getSource()).stop();
            detenerSonidoFondo();
            menuInicial();
        }).start();
    }
    public static void main(String [] args) {
        intro();
    }
    public static void menuInicial() {
        JLabel fondo = new JLabel(new ImageIcon(RUTA_ARCHIVOS_VISUALES + "PantallaInicial.png"));
        fondo.setLayout(new GridBagLayout());

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new GridLayout(3, 1, 30, 20));

        JButton botonJugar = new JButton("▶ Jugar");
        JButton botonCreditos = new JButton("👤 Créditos");
        JButton botonSalir = new JButton("🚪 Salir");

        Stream.of(botonJugar, botonCreditos, botonSalir).forEach(boton -> {
            boton.setFont(new Font("Noto Sans", Font.BOLD, 40));
            boton.setBackground(new Color(243, 216, 140));
            boton.setFocusPainted(false);
            boton.setBorderPainted(false);
            boton.setPreferredSize(new Dimension(400, 125));
            panelBotones.add(boton);
        });

        GridBagConstraints posicionBotones = new GridBagConstraints();
        posicionBotones.insets = new Insets(350, 0, 0, 0);
        fondo.add(panelBotones, posicionBotones);
        ventanaPrincipal.setVisible(true);

        reproducirSonidoFondo(RUTA_ARCHIVOS_VISUALES + "Soundtrack.wav");

        botonJugar.addActionListener(e -> {
            reproducirSonidoClick();
            mostrarJuegos();
        });

        botonCreditos.addActionListener(e -> {
            reproducirSonidoClick();
            mostrarCreditos();
        });

        botonSalir.addActionListener(e -> {
            detenerSonidoFondo();
            reproducirSonidoClick();
            int respuesta = JOptionPane.showConfirmDialog(
                    ventanaPrincipal,
                    "¿Estás seguro que quieres salir del juego?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                reproducirSonidoFondo(RUTA_ARCHIVOS_VISUALES + "Soundtrack.wav");
            }
        });
        ventanaPrincipal.setContentPane(fondo);
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
    }

    public static void mostrarJuegos() {
        AtomicInteger juegoSeleccionado = new AtomicInteger(0);

        JLabel fondo = new JLabel(new ImageIcon(RUTA_ARCHIVOS_VISUALES + "ElegirModo.png"));
        fondo.setLayout(new GridBagLayout());

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new GridLayout(1, 2, 550, 0));

        JPanel panelBotonSolo = new JPanel();
        panelBotonSolo.setLayout(new GridBagLayout());

        JButton botonTexas = new JButton("");
        JButton botonFiveDraw = new JButton("");
        JButton botonRegresar = new JButton("↩ Regresar");

        botonRegresar.setFont(new Font("Noto Sans", Font.BOLD, 40));
        botonRegresar.setBackground(new Color(243, 216, 140));
        botonRegresar.setFocusPainted(false);
        botonRegresar.setBorderPainted(false);
        panelBotonSolo.add(botonRegresar);

        botonTexas.setIcon(new ImageIcon(RUTA_ARCHIVOS_VISUALES + "TexasModo.gif"));
        botonFiveDraw.setIcon(new ImageIcon(RUTA_ARCHIVOS_VISUALES + "FiveModo.gif"));

        Stream.of(botonTexas, botonFiveDraw).forEach(boton -> {
            boton.setFocusPainted(false);
            boton.setBorderPainted(false);
            boton.setContentAreaFilled(false);
            panelBotones.add(boton);
        });

        GridBagConstraints posicionBotones = new GridBagConstraints();
        posicionBotones.gridx = 0;
        posicionBotones.gridy = 0;
        posicionBotones.anchor = GridBagConstraints.CENTER;
        posicionBotones.insets = new Insets(210, 65, 45, 65);
        fondo.add(panelBotones, posicionBotones);

        GridBagConstraints posicionBotonAtras = new GridBagConstraints();
        posicionBotonAtras.gridx = 0;
        posicionBotonAtras.gridy = 1;
        posicionBotonAtras.anchor = GridBagConstraints.SOUTH;
        posicionBotonAtras.insets = new Insets(0, 0, 150, 0);
        fondo.add(panelBotonSolo, posicionBotonAtras);

        botonTexas.addActionListener(e -> {
            juegoSeleccionado.set(1);
            modoDeJuego = 1;
            reproducirSonidoClick();
            solicitarJugadores(modoDeJuego);
        });

        botonFiveDraw.addActionListener(e -> {
            juegoSeleccionado.set(2);
            modoDeJuego = 2;
            reproducirSonidoClick();
            solicitarJugadores(modoDeJuego);
        });

        botonRegresar.addActionListener(e -> {
            detenerSonidoFondo();
            reproducirSonidoClick();
            menuInicial();
        });
        ventanaPrincipal.setContentPane(fondo);
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
        ventanaPrincipal.setVisible(true);
    }
    public static void solicitarJugadores(int modoDeJuego) {
        JLabel fondo = new JLabel(new ImageIcon(RUTA_ARCHIVOS_VISUALES + "CantidadDeJugadores.png"));

        JTextArea numeroDeJugadores = new JTextArea(cantidadDeJugadores + " Jugadores");
        numeroDeJugadores.setFont(new Font("Noto Sans", Font.BOLD, 80));
        numeroDeJugadores.setForeground(new Color(243, 216, 140));
        numeroDeJugadores.setOpaque(false);
        numeroDeJugadores.setLineWrap(true);
        numeroDeJugadores.setWrapStyleWord(true);
        numeroDeJugadores.setAlignmentX(Component.CENTER_ALIGNMENT);
        numeroDeJugadores.setBounds(725, 625, 475, 100);
        fondo.add(numeroDeJugadores);

        JButton botonAumentarJugadores = new JButton(">");
        JButton botonDisminuirJugadores = new JButton("<");

        Stream.of(botonAumentarJugadores, botonDisminuirJugadores).forEach(boton -> {
            boton.setFont(new Font("Noto Sans", Font.BOLD, 100));
            boton.setBackground(new Color(243, 216, 140));
            boton.setFocusPainted(false);
            boton.setBorderPainted(false);
            fondo.add(boton);
        });
        botonDisminuirJugadores.setBounds(600, 625, 100, 100);
        botonAumentarJugadores.setBounds(1225, 625, 100, 100);

        botonDisminuirJugadores.addActionListener(e -> {
            reproducirSonidoClick();
            if(cantidadDeJugadores > 2) {
                cantidadDeJugadores -= 1;
                numeroDeJugadores.setText(cantidadDeJugadores + " Jugadores");
            } else {
                JOptionPane.showMessageDialog(ventanaPrincipal, "Debe tener al menos 2 jugadores", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        botonAumentarJugadores.addActionListener(e -> {
            reproducirSonidoClick();
            if (modoDeJuego == 1) {
                if (cantidadDeJugadores < 10) {
                    cantidadDeJugadores += 1;
                    numeroDeJugadores.setText(cantidadDeJugadores + " Jugadores");
                } else {
                    JOptionPane.showMessageDialog(ventanaPrincipal, "Solo puedes tener maximo 10 jugadores", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                if (cantidadDeJugadores < 7) {
                    cantidadDeJugadores += 1;
                    numeroDeJugadores.setText(cantidadDeJugadores + " Jugadores");
                } else {
                    JOptionPane.showMessageDialog(ventanaPrincipal, "Solo puedes tener maximo 7 jugadores", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.setFont(new Font("Noto Sans", Font.BOLD, 60));
        botonAceptar.setBackground(new Color(243, 216, 140));
        botonAceptar.setFocusPainted(false);
        botonAceptar.setBorderPainted(false);
        botonAceptar.setBounds(800, 775, 300, 100);
        fondo.add(botonAceptar);

        botonAceptar.addActionListener(e -> {
            reproducirSonidoClick();

            nombres.clear();
            for (int i = 0; i < cantidadDeJugadores; i++) {
                solicitarNombreDeJugador(i + 1);
            }
            escogerDineroInicial();

            if (modoDeJuego == 1) {
                tableroTexas();
                TexasHold juego = new TexasHold();
                juego.inicializarJugadores();
                actualizarTablero();
                juego.repartirCartas();
                juego.mostrarMano();
                juego.mostrarCartasComunitarias();
                juego.jugarRonda();
            } else {
                tableroFiveDraw();
                FiveCard juego = new FiveCard();
                actualizarTablero();
            }
        });

        ventanaPrincipal.setContentPane(fondo);
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
        ventanaPrincipal.setVisible(true);
    }

    public static void solicitarNombreDeJugador(int cantidadDeJugadores) {
        JDialog nombreJugador = new JDialog();
        nombreJugador.setUndecorated(true);
        nombreJugador.setSize(1000, 350);
        nombreJugador.setLocationRelativeTo(null);
        nombreJugador.setModal(true);
        nombreJugador.setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel titulo = new JLabel("Ingrese el nombre del jugador " + cantidadDeJugadores + "\uD83D\uDC64", SwingConstants.CENTER);
        titulo.setFont(new Font("Noto Sans", Font.BOLD, 50));
        titulo.setForeground(new Color(243, 216, 140));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JTextField campoNombre = new JTextField();
        campoNombre.setFont(new Font("Noto Sans", Font.PLAIN, 50));
        campoNombre.setBackground(new Color(243, 216, 140));
        campoNombre.setHorizontalAlignment(JTextField.CENTER);
        panelPrincipal.add(campoNombre, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(41, 41, 41));
        JButton botonAceptar = new JButton("Aceptar");

        botonAceptar.setFont(new Font("Noto Sans", Font.BOLD, 32));
        botonAceptar.setBackground(new Color(243, 216, 140));
        botonAceptar.setFocusPainted(false);

        botonAceptar.addActionListener(e -> {
            reproducirSonidoClick();
            if (!campoNombre.getText().trim().isEmpty()) {
                String nombre = campoNombre.getText();
                nombres.add(nombre);
                nombreJugador.dispose();
            } else {
                JOptionPane.showMessageDialog(nombreJugador, "Debe ingresar un nombre válido", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        campoNombre.addActionListener(e -> botonAceptar.doClick());

        panelBoton.add(botonAceptar);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
        panelPrincipal.setBackground(new Color(41, 41, 41));
        nombreJugador.add(panelPrincipal);
        nombreJugador.setVisible(true);
        System.out.println(nombres);
    }

    public static void escogerDineroInicial() {
        JDialog ventanaDinero = new JDialog();
        ventanaDinero.setSize(1200, 300);
        ventanaDinero.setLocationRelativeTo(null);
        ventanaDinero.setUndecorated(true);
        ventanaDinero.setModal(true);
        ventanaDinero.setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panelPrincipal.setBackground(new Color(41, 41, 41));

        JLabel titulo = new JLabel("\uD83D\uDCB5 Seleccione la cantidad de dinero inicial \uD83D\uDCB8", SwingConstants.CENTER);
        titulo.setFont(new Font("Noto Sans", Font.BOLD, 50));
        titulo.setForeground(new Color(243, 216, 140));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 15, 15));
        panelBotones.setBackground(new Color(41, 41, 41));

        JButton boton1 = new JButton(" 1000 ");
        JButton boton2 = new JButton(" 3000 ");
        JButton boton3 = new JButton(" 5000 ");

        Stream.of(boton1, boton2, boton3).forEach(boton -> {
            boton.setFont(new Font("Noto Sans", Font.BOLD, 50));
            boton.setBackground(new Color(243, 216, 140));
            boton.setFocusPainted(false);
        });

        boton1.addActionListener(e -> {
            dineroInicial = 1000;
            ventanaDinero.dispose();
        });

        boton2.addActionListener(e -> {
            dineroInicial = 3000;
            ventanaDinero.dispose();
        });

        boton3.addActionListener(e -> {
            dineroInicial = 5000;
            ventanaDinero.dispose();
        });

        panelBotones.add(boton1);
        panelBotones.add(boton2);
        panelBotones.add(boton3);

        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        ventanaDinero.add(panelPrincipal);
        ventanaDinero.setVisible(true);
        System.out.println(dineroInicial);
        System.out.println(cantidadDeJugadores);
    }

    public static void tableroGeneral(JLabel fondo) {
        JButton[] botonesAcciones = new JButton[5];
        for (int i = 0; i < 5; i++) {
            botonesAcciones[i] = new JButton("");
            botonesAcciones[i].setIcon(new ImageIcon(RUTA_ARCHIVOS_VISUALES + "Boton" + (1+i) + ".png"));
            botonesAcciones[i].setContentAreaFilled(false);
            botonesAcciones[i].setFocusPainted(false);
            botonesAcciones[i].setBorderPainted(false);
            fondo.add(botonesAcciones[i]);
        }

        botonesAcciones[0].setBounds(100, 350, 200, 200);
        botonesAcciones[1].setBounds(100, 600, 200, 200);
        botonesAcciones[2].setBounds(100, 850, 200, 200);
        botonesAcciones[3].setBounds(350, 650, 200, 200);
        botonesAcciones[4].setBounds(350, 850, 200, 200);

        cantidadEnBote.setFont(new Font("Noto Sans", Font.BOLD, 40));
        cantidadEnBote.setForeground(Color.WHITE);
        cantidadEnBote.setOpaque(false);
        cantidadEnBote.setBorder(null);
        cantidadEnBote.setHorizontalAlignment(JTextField.CENTER);
        cantidadEnBote.setBounds(1025, 212, 200, 200);
        fondo.add(cantidadEnBote);

        Stream.of(avisoDeTurno, manoActual, dineroEnMano).forEach(campo -> {
            campo.setFont(new Font("Noto Sans", Font.BOLD, 40));
            campo.setBackground(new Color(243, 216, 140));
            campo.setBorder(null);
            campo.setEditable(false);
            campo.setHorizontalAlignment(JTextField.CENTER);
            fondo.add(campo);
        });

        avisoDeTurno.setBounds(10, 10, 400, 100);
        dineroEnMano.setBounds(1550, 825, 200, 100);
        manoActual.setBounds(760, 640, 400, 100);

        JButton[] botonesFicha = new JButton[6];
        int delta = 175;
        for (int i = 0; i < 6; i++) {
            botonesFicha[i] = new JButton("");
            botonesFicha[i].setIcon(new ImageIcon(RUTA_ARCHIVOS_FICHAS + "Ficha" + (1+i) + "Atras.png"));
            botonesFicha[i].setContentAreaFilled(false);
            botonesFicha[i].setFocusPainted(false);
            botonesFicha[i].setBorderPainted(false);
            if (i > 2) {
                botonesFicha[i].setBounds(1400 + ((i-3)*delta), 900, 150, 150);
            } else {
                botonesFicha[i].setBounds(1400 + (i*delta), 700, 150, 150);
            }
            botonesFicha[i].setRolloverIcon(new ImageIcon(RUTA_ARCHIVOS_FICHAS + "Ficha" + (1+i) + "Cara.png"));
            botonesFicha[i].setRolloverEnabled(true);
            fondo.add(botonesFicha[i]);
        }

        botonesFicha[0].addActionListener(e -> {
            reproducirSonidoClick();
            apostar(jugadores.get(turnoActual), 5);
        });

        botonesFicha[1].addActionListener(e -> {
            reproducirSonidoClick();
            apostar(jugadores.get(turnoActual), 20);
        });

        botonesFicha[2].addActionListener(e -> {
            reproducirSonidoClick();
            apostar(jugadores.get(turnoActual), 50);
        });

        botonesFicha[3].addActionListener(e -> {
            reproducirSonidoClick();
            apostar(jugadores.get(turnoActual), 100);
        });

        botonesFicha[4].addActionListener(e -> {
            reproducirSonidoClick();
            apostar(jugadores.get(turnoActual), 500);
        });

        botonesFicha[5].addActionListener(e -> {
            reproducirSonidoClick();
            apostar(jugadores.get(turnoActual), 2000);
        });

        JButton botonInformacion = new JButton("ℹ Informacion");
        JButton botonCombinaciones = new JButton("\uD83C\uDCCF Combinaciones");
        JButton botonConfiguraciones = new JButton("⚙ Opciones");

        Stream.of(botonInformacion, botonCombinaciones,botonConfiguraciones).forEach(boton -> {
            boton.setFont(new Font("Noto Sans", Font.BOLD, 40));
            boton.setBackground(new Color(243, 216, 140));
            boton.setFocusPainted(false);
            boton.setBorderPainted(false);
            fondo.add(boton);
        });

        botonCombinaciones.setFont(new Font("Noto Sans", Font.BOLD, 35));

        botonInformacion.setBounds(1575, 200, 325, 75);
        botonCombinaciones.setBounds(1575, 400, 325, 75);
        botonConfiguraciones.setBounds(100, 200, 300, 75);

        JButton botonAllIn = new JButton("");
        botonAllIn.setIcon(new ImageIcon(RUTA_ARCHIVOS_FICHAS + "FichaAllIn.png"));
        botonAllIn.setContentAreaFilled(false);
        botonAllIn.setFocusPainted(false);
        botonAllIn.setBorderPainted(false);
        botonAllIn.setBounds(1525, 575, 250, 100);
        fondo.add(botonAllIn);

        botonInformacion.addActionListener(e -> {
            reproducirSonidoClick();
            mostrarInformacion(modoDeJuego);
        });

        botonCombinaciones.addActionListener(e -> {
            reproducirSonidoClick();
            mostrarCombinaciones();
        });

        botonConfiguraciones.addActionListener(e -> {
            reproducirSonidoClick();
            mostrarMenuOpciones();
        });

        botonAllIn.addActionListener(e -> {
            reproducirSonidoClick();
            apostar(jugadores.get(turnoActual),
                    jugadores.get(turnoActual).getDinero());
        });

        botonesAcciones[0].addActionListener(e -> { // Botón Apostar/Subir
            reproducirSonidoClick();
            String apuestaStr = JOptionPane.showInputDialog(
                    null,
                    "Ingrese la cantidad que desea apostar:",
                    "Subir apuesta",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (apuestaStr != null && !apuestaStr.trim().isEmpty() && apuestaStr.matches("\\d+")) {
                int cantidad = Integer.parseInt(apuestaStr);
                //manejarAccionJugador("APOSTAR", cantidad);
            }
        });

        botonesAcciones[1].addActionListener(e -> { // Botón Igualar
            reproducirSonidoClick();
            //manejarAccionJugador("IGUALAR", 0);
        });

        botonesAcciones[2].addActionListener(e -> { // Botón Fold
            reproducirSonidoClick();
           // manejarAccionJugador("FOLD", 0);
        });

        botonesAcciones[3].addActionListener(e -> { // Botón Pasar
            reproducirSonidoClick();

            //manejarAccionJugador("PASAR", 0);
        });

        botonesAcciones[4].addActionListener(e -> { // Botón All-in
            reproducirSonidoClick();
            Jugador jugador =jugadores.get(turnoActual);
           // manejarAccionJugador("APOSTAR", jugador.getDinero());
        });
    }

    public static void actualizarTablero() {
        avisoDeTurno.setText("Es turno de: " + JuegoPoker.jugadores.get(JuegoPoker.turnoActual).getNombre());
        dineroEnMano.setText("$" + JuegoPoker.jugadores.get(JuegoPoker.turnoActual).getDinero());
        manoActual.setText("Mano de " + JuegoPoker.jugadores.get(JuegoPoker.turnoActual).getNombre());
    }



    public static void tableroFiveDraw() {
        JLabel fondo = new JLabel(new ImageIcon(RUTA_ARCHIVOS_VISUALES + "TableroFiveCard.png"));

        tableroGeneral(fondo);

        ventanaPrincipal.setContentPane(fondo);
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
        ventanaPrincipal.setVisible(true);
    }

    public static void tableroTexas() {
        JLabel fondo = new JLabel(new ImageIcon(RUTA_ARCHIVOS_VISUALES + "TableroTexas.png"));

        manoTexas1.setBounds(650, 725, 250, 350);
        fondo.add(manoTexas1);

        manoTexas2.setBounds(1025, 725, 250, 350);
        fondo.add(manoTexas2);

        riverTexas1 = new JLabel();
        riverTexas1.setBounds(615, 370, 120, 170);
        fondo.add(riverTexas1);

        riverTexas2 = new JLabel();
        riverTexas2.setBounds(755, 370, 120, 170);
        fondo.add(riverTexas2);

        riverTexas3 = new JLabel();
        riverTexas3.setBounds(895, 370, 120, 170);
        fondo.add(riverTexas3);

        riverTexas4 = new JLabel();
        riverTexas4.setBounds(1035, 370, 120, 170);
        fondo.add(riverTexas4);

        riverTexas5 = new JLabel();
        riverTexas5.setBounds(1175, 370, 120, 170);
        fondo.add(riverTexas5);

        tableroGeneral(fondo);

        ventanaPrincipal.setContentPane(fondo);
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
        ventanaPrincipal.setVisible(true);
    }

    public static void apostar(Jugador jugador, int cantidad){
        if(jugador.getDinero() >= cantidad){
            jugador.setDinero(jugador.getDinero() - cantidad);
            dineroEnBote += cantidad;
            cantidadEnBote.setText("$ " + dineroEnBote);
            dineroEnMano.setText("$" + jugador.getDinero());
        } else {
            JOptionPane.showMessageDialog(null, "No tienes suficientes fondos para apostar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void pasar(){
        turnoActual = (turnoActual + 1) % jugadores.size();
        avisoDeTurno.setText("Es turno de: " + JuegoPoker.jugadores.get(JuegoPoker.turnoActual).getNombre());
    }

    public static void igualar(Jugador jugador, int apuestaMaxima){
        int cantidadApostar = apuestaMaxima - jugador.getApuestaActual();
        if(jugador.getDinero() >= cantidadApostar){
            jugador.setDinero(jugador.getDinero() - cantidadApostar);
            jugador.setApuestaActual(apuestaMaxima);
            //Mostrar mensaje con interfaz donde se iguala la apuesta
        } else {
            //Mostrar mensaje con interfaz donde el jugador no puede igualar la apuesta
        }
    }

    public static void subir(Jugador jugador, int cantidad, int apuestaMaxima){
        int cantidadTotal = apuestaMaxima + cantidad;
        if(jugador.getDinero() >= cantidadTotal){
            jugador.setDinero(jugador.getDinero() - cantidadTotal);
            jugador.setApuestaActual(cantidadTotal);
            //Mostrar mensaje con interfaz donde el jugador sube la apuesta
        } else {
            //Mostrar mensaje con interfaz donde el jugador no puede subir la apuesta por fondos insuficientes
        }
    }

    public static void fold(Jugador jugador){
        jugador.setActivo(false);
        JOptionPane.showMessageDialog(null, "Has hecho fold, pierdes la ronda", "Fold", JOptionPane.INFORMATION_MESSAGE);
    }
































































    public static void detenerSonidoFondo() {
        SONIDO_FONDO.stop();
        SONIDO_FONDO.close();
    }
    public static void reproducirSonidoFondo(String rutaArchivo) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(rutaArchivo));
            SONIDO_FONDO = AudioSystem.getClip();
            SONIDO_FONDO.open(audioInputStream);
            SONIDO_FONDO.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void reproducirSonidoClick() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File( RUTA_ARCHIVOS_VISUALES + "SonidoBoton.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void mostrarCreditos() {
        Color colorBoton = new Color(243, 216, 140);

        JPanel panelCreditos = new JPanel(new BorderLayout(10, 10));
        panelCreditos.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelCreditos.setBackground(new Color(41, 41, 41));

        JLabel titulo = new JLabel("Créditos", SwingConstants.CENTER);
        titulo.setFont(new Font("Noto Sans", Font.BOLD, 45));
        titulo.setForeground(colorBoton);
        panelCreditos.add(titulo, BorderLayout.NORTH);

        JTextArea contenido = new JTextArea(
                "Desarrollado por:\n" +
                        "• Diego Erik Alfonso Montoya (1198520)\n" +
                        "• Angel Gabriel Manjarrez Moreno (1197503)\n\n" +
                        "Versión: 20/05/2025\n" +
                        "© Todos los derechos reservados"
        );
        contenido.setFont(new Font("Noto Sans", Font.PLAIN, 30));
        contenido.setEditable(false);
        contenido.setBackground(colorBoton);
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelCreditos.add(new JScrollPane(contenido), BorderLayout.CENTER);

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> {
            reproducirSonidoClick();
            ((Window) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
        });
        cerrar.setFont(new Font("Noto Sans", Font.BOLD, 30));
        cerrar.setBackground(colorBoton);
        cerrar.setFocusPainted(false);
        cerrar.setBorderPainted(false);

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(41, 41, 41));
        panelBoton.add(cerrar);
        panelCreditos.add(panelBoton, BorderLayout.SOUTH);

        JDialog creditos = new JDialog();
        creditos.setUndecorated(true);
        creditos.setTitle("Créditos");
        creditos.setModal(true);
        creditos.setContentPane(panelCreditos);
        creditos.pack();
        creditos.setLocationRelativeTo(null);
        creditos.setVisible(true);
    }
    public static void mostrarInformacion(int juegoSeleccionado) {
        JDialog informacionVentana = new JDialog();
        informacionVentana.setSize(800, 500);
        informacionVentana.setLocationRelativeTo(null);
        informacionVentana.setUndecorated(true);
        informacionVentana.setModal(true);
        informacionVentana.setLayout(new BorderLayout(10, 10));
        informacionVentana.getContentPane().setBackground(new Color(41, 41, 41));

        String mensaje = juegoSeleccionado == 1 ?
                "• Texas Hold'em se juega entre 2 y 10 jugadores.\n" +
                        "• Cada jugador recibe 2 cartas privadas y se revelan 5 cartas comunitarias.\n" +
                        "• Objetivo: formar la mejor mano de 5 cartas combinando tus cartas con las comunitarias.\n" +
                        "• 4 rondas de apuestas: Pre-Flop, Flop, Turn y River.\n" +
                        "• Acciones: pasar, apostar, igualar, subir o retirarte.\n" +
                        "• Al final (Showdown), se comparan manos y el mejor gana el bote.\n" :
                "• 5 Card Draw se juega entre 2 y 7 jugadores.\n" +
                        "• Cada jugador recibe 5 cartas privadas.\n" +
                        "• Primera ronda de apuestas, luego cambio de cartas (0-5).\n" +
                        "• Segunda ronda de apuestas.\n" +
                        "• Showdown: jugadores revelan cartas.\n" +
                        "• Gana quien tenga la mejor mano de póquer de 5 cartas.\n";

        JTextArea areaMensaje = new JTextArea(mensaje);
        areaMensaje.setFont(new Font("Noto Sans", Font.PLAIN, 32));
        areaMensaje.setForeground(new Color(243, 216, 140));
        areaMensaje.setBackground(new Color(41, 41, 41));
        areaMensaje.setEditable(false);
        areaMensaje.setLineWrap(true);
        areaMensaje.setWrapStyleWord(true);
        areaMensaje.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.setFont(new Font("Noto Sans", Font.BOLD, 32));
        botonCerrar.setBackground(new Color(243, 216, 140));
        botonCerrar.setFocusPainted(false);
        botonCerrar.setBorderPainted(false);
        botonCerrar.addActionListener(e -> {
            reproducirSonidoClick();
            informacionVentana.dispose();
        });

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(41, 41, 41));
        panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelBoton.add(botonCerrar);
        informacionVentana.add(areaMensaje, BorderLayout.CENTER);
        informacionVentana.add(panelBoton, BorderLayout.SOUTH);
        informacionVentana.setVisible(true);
    }
    public static void mostrarCombinaciones() {
        JDialog combinaciones = new JDialog();
        combinaciones.setSize(1200, 420);
        combinaciones.setLocation(360,0);
        combinaciones.setUndecorated(true);
        combinaciones.setModal(true);
        combinaciones.setLayout(new BorderLayout());
        combinaciones.getContentPane().setBackground(new Color(41, 41, 41));

        JLabel imagen = new JLabel(new ImageIcon(RUTA_ARCHIVOS_VISUALES + "Combinaciones.png"));
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imagen, BorderLayout.NORTH);
        imagePanel.setBackground(new Color(41, 41, 41));

        JScrollPane barraScroll = new JScrollPane(imagePanel);
        barraScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        barraScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        barraScroll.getVerticalScrollBar().setUnitIncrement(16);
        barraScroll.setBorder(BorderFactory.createEmptyBorder());
        barraScroll.getViewport().setBackground(new Color(41, 41, 41));

        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.setFont(new Font("Noto Sans", Font.BOLD, 30));
        botonCerrar.setBackground(new Color(243, 216, 140));
        botonCerrar.setFocusPainted(false);
        botonCerrar.setBorderPainted(false);

        botonCerrar.addActionListener(e -> {
            reproducirSonidoClick();
            combinaciones.dispose();
        });

        JPanel panelDeBoton = new JPanel();
        panelDeBoton.setBackground(new Color(41, 41, 41));
        panelDeBoton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelDeBoton.add(botonCerrar);

        combinaciones.add(barraScroll, BorderLayout.CENTER);
        combinaciones.add(panelDeBoton, BorderLayout.SOUTH);
        combinaciones.setVisible(true);
    }

    public static void mostrarMenuOpciones() {
        JDialog menuOpciones = new JDialog();
        menuOpciones.setSize(600, 500);
        menuOpciones.setLocationRelativeTo(null);
        menuOpciones.setUndecorated(true);
        menuOpciones.setModal(true);
        menuOpciones.setLayout(new BorderLayout(10, 10));
        menuOpciones.getContentPane().setBackground(new Color(41, 41, 41));

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelPrincipal.setBackground(new Color(41, 41, 41));

        JLabel titulo = new JLabel("⚙ Opciones", SwingConstants.CENTER);
        titulo.setFont(new Font("Noto Sans", Font.BOLD, 50));
        titulo.setForeground(new Color(243, 216, 140));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 40)));

        JToggleButton botonMusica = new JToggleButton("", SONIDO_FONDO != null && SONIDO_FONDO.isActive());
        botonMusica.setFocusPainted(false);

        if (botonMusica.isSelected()) {
            botonMusica.setText("🔊 Música: ACTIVADA");
        } else {
            botonMusica.setText("🔇 Música: DESACTIVADA");
        }

        botonMusica.addActionListener(e -> {
            reproducirSonidoClick();
            if (botonMusica.isSelected()) {
                botonMusica.setText("🔊 Música: ACTIVADA");
                reproducirSonidoFondo(RUTA_ARCHIVOS_VISUALES + "Soundtrack.wav");
            } else {
                botonMusica.setText("🔇 Música: DESACTIVADA");
                detenerSonidoFondo();
            }
        });

        panelPrincipal.add(botonMusica);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton botonAbandonar = new JButton("♠ Abandonar Mesa");
        botonAbandonar.addActionListener(e -> {
            reproducirSonidoClick();
            int respuesta = JOptionPane.showConfirmDialog(
                    menuOpciones,
                    "¿Estás seguro que quieres abandonar la mesa?",
                    "Confirmar abandono",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    new ImageIcon(RUTA_ARCHIVOS_VISUALES + "Warning.png")
            );
            if (respuesta == JOptionPane.YES_OPTION) {
                menuOpciones.dispose();
                menuInicial();
            }
        });
        panelPrincipal.add(botonAbandonar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton botonSalir = new JButton("🚪 Salir del Juego");
        botonSalir.addActionListener(e -> {
            reproducirSonidoClick();
            int respuesta = JOptionPane.showConfirmDialog(
                    menuOpciones,
                    "¿Estás seguro que quieres salir del juego?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    new ImageIcon(RUTA_ARCHIVOS_VISUALES + "Warning.png")
            );
            if (respuesta == JOptionPane.YES_OPTION) {
                detenerSonidoFondo();
                menuOpciones.dispose();
                menuInicial();
            }
        });
        panelPrincipal.add(botonSalir);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 50)));

        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.setFont(new Font("Noto Sans", Font.BOLD, 30));

        Stream.of(botonCerrar, botonSalir, botonAbandonar, botonMusica).forEach(boton -> {
            boton.setFont(new Font("Noto Sans", Font.BOLD, 40));
            boton.setBackground(new Color(243, 216, 140));
            boton.setFocusPainted(false);
            boton.setBorderPainted(false);
            boton.setAlignmentX(Component.CENTER_ALIGNMENT);
            boton.setPreferredSize(new Dimension(400, 80));
        });

        botonCerrar.addActionListener(e -> {
            reproducirSonidoClick();
            menuOpciones.dispose();
        });
        panelPrincipal.add(botonCerrar);

        menuOpciones.add(panelPrincipal, BorderLayout.CENTER);
        menuOpciones.setVisible(true);
    }




}