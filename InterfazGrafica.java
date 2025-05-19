import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.stream.*;

public class InterfazGrafica {
    private static JFrame ventanaPrincipal;

    static Clip SONIDO_FONDO;
    static String RUTA_ARCHIVOS_VISUALES = "C:\\Users\\PC OSTRICH\\Proyecto-Final\\RecursosVisuales\\";
    static String RUTA_ARCHIVOS_FICHAS = "C:\\Users\\PC OSTRICH\\Proyecto-Final\\RecursosVisuales\\Fichas\\";
    // static String RUTA_ARCHIVOS_VISUALES = "C:\\Users\\14321\\IdeaProjects\\Proyecto-Final\\RecursosVisuales\\";
    // static String RUTA_ARCHIVOS_FICHAS = "C:\\Users\\14321\\IdeaProjects\\Proyecto-Final\\RecursosVisuales\\Fichas\\";

    public static void main(String[] args) {
        ventanaPrincipal = new JFrame("Golden Dynasty");
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventanaPrincipal.setResizable(false);
        ventanaPrincipal.setUndecorated(true);
        intro();
    }

    public static void intro() {
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

    public static void mostrarJuegos() {
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
            reproducirSonidoClick();
            tableroTexas();
        });

        botonFiveDraw.addActionListener(e -> {
            reproducirSonidoClick();
            tableroFiveDraw();
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

    public static void tableroTexas() {
        JLabel fondo = new JLabel(new ImageIcon(RUTA_ARCHIVOS_VISUALES + "TableroTexas.png"));
        ventanaPrincipal.setLayout(null);

        ventanaPrincipal.setContentPane(fondo);
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
        ventanaPrincipal.setVisible(true);
    }

    public static void tableroFiveDraw() {
        JLabel fondo = new JLabel(new ImageIcon(RUTA_ARCHIVOS_VISUALES + "TableroFiveCard.png"));
        fondo.setBounds(0, 0, 1920, 1080);

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

        JButton botonAllIn = new JButton("");
        botonAllIn.setIcon(new ImageIcon(RUTA_ARCHIVOS_FICHAS + "FichaAllIn.png"));
        botonAllIn.setContentAreaFilled(false);
        botonAllIn.setFocusPainted(false);
        botonAllIn.setBorderPainted(false);
        botonAllIn.setBounds(1525, 575, 250, 100);
        fondo.add(botonAllIn);

        JButton botonInformacion = new JButton(" Informacion");
        JButton botonCombinaciones = new JButton(" Combinaciones");
        JButton botonConfiguraciones = new JButton(" Configuraciones");

        Stream.of(botonInformacion, botonCombinaciones,botonConfiguraciones).forEach(boton -> {
            boton.setFont(new Font("Noto Sans", Font.BOLD, 40));
            boton.setBackground(new Color(243, 216, 140));
            boton.setFocusPainted(false);
            boton.setBorderPainted(false);
            fondo.add(boton);
        });




        ventanaPrincipal.setContentPane(fondo);
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
        ventanaPrincipal.setVisible(true);
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


}
