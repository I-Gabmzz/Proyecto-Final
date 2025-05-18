import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.stream.*;

public class InterfazGrafica {
    private static boolean juegoIniciado = false;
    static Clip sonidoFondo;

    public static void main(String[] args) {
        intro();
    }

    public static void intro() {
        JFrame ventana = new JFrame();
        ventana.setUndecorated(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);

        ImageIcon gifIcon = new ImageIcon("C:\\Users\\PC OSTRICH\\Proyecto-Final\\RecursosVisuales\\intro.gif");

        JLabel gifLabel = new JLabel(gifIcon);
        ventana.setContentPane(gifLabel);

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        reproducirSonidoFondo("C:\\Users\\PC OSTRICH\\Proyecto-Final\\RecursosVisuales\\Intro.wav");
        new Timer(6000, e -> {
            ((Timer) e.getSource()).stop();
            detenerSonidoFondo();
            ventana.dispose();
            new Thread(() -> menuInicial()).start();
        }).start();

    }
    public static void menuInicial() {
        JFrame ventana = new JFrame("Golden Dynasty");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(new BorderLayout());
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setResizable(false);
        ventana.setUndecorated(true);

        JLabel fondo = new JLabel(new ImageIcon("C:\\Users\\PC OSTRICH\\Proyecto-Final\\RecursosVisuales\\PantallaInicial.png"));
        fondo.setLayout(new GridBagLayout());
        ventana.setContentPane(fondo);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new GridLayout(3, 1, 30, 20));

        JButton botonJugar = new JButton("▶ Jugar");
        JButton botonCreditos = new JButton("👤 Créditos");
        JButton botonSalir = new JButton("🚪 Salir");

        Font fuenteBotones = new Font("Noto Sans", Font.BOLD, 60);
        Color colorBoton = new Color(243, 216, 140);

        Stream.of(botonJugar, botonCreditos, botonSalir).forEach(boton -> {
            boton.setFont(fuenteBotones);
            boton.setBackground(colorBoton);
            boton.setFocusPainted(false);
            boton.setBorderPainted(false);
            boton.setPreferredSize(new Dimension(400, 125));
            panelBotones.add(boton);
        });

        GridBagConstraints posicionBotones = new GridBagConstraints();
        posicionBotones.insets = new Insets(350, 0, 0, 0);
        fondo.add(panelBotones, posicionBotones);
        ventana.setVisible(true);

       reproducirSonidoFondo("C:\\Users\\PC OSTRICH\\Proyecto-Final\\RecursosVisuales\\Soundtrack.wav");

        botonJugar.addActionListener(e -> {
            detenerSonidoFondo();
            reproducirSonidoClick();
            juegoIniciado = true;
            ventana.dispose();
        });

        botonCreditos.addActionListener(e -> {
            reproducirSonidoClick();
            mostrarCreditos();
        });

        botonSalir.addActionListener(e -> {
            detenerSonidoFondo();
            reproducirSonidoClick();
            int respuesta = JOptionPane.showConfirmDialog(
                    ventana,
                    "¿Estás seguro que quieres salir del juego?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                reproducirSonidoFondo("C:\\Users\\PC OSTRICH\\Proyecto-Final\\RecursosVisuales\\Soundtrack.wav");
            }
        });
        ventana.pack();
        while (!juegoIniciado) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void mostrarCreditos() {
        JPanel panelCreditos = new JPanel(new BorderLayout(10, 10));
        panelCreditos.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelCreditos.setBackground(new Color(41, 41, 41));

        JLabel titulo = new JLabel("Créditos", SwingConstants.CENTER);
        titulo.setFont(new Font("Noto Sans", Font.BOLD, 24));
        titulo.setForeground(new Color(243, 216, 140));
        panelCreditos.add(titulo, BorderLayout.NORTH);

        JTextArea contenido = new JTextArea(
                "Desarrollado por:\n" +
                        "• Diego Erik Alfonso Montoya (1198520)\n" +
                        "• Angel Gabriel Manjarrez Moreno (1197503)\n\n" +
                        "Versión: 10/05/2025\n" +
                        "© Todos los derechos reservados"
        );
        contenido.setFont(new Font("Noto Sans", Font.PLAIN, 14));
        contenido.setEditable(false);
        contenido.setBackground(new Color(243, 216, 140));
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelCreditos.add(new JScrollPane(contenido), BorderLayout.CENTER);

        Font fuenteBotones = new Font("Noto Sans", Font.BOLD, 14);
        Color colorBoton = new Color(243, 216, 140);

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> {
            reproducirSonidoClick();
            ((Window) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
        });
        cerrar.setFont(fuenteBotones);
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
        creditos.setResizable(true);
        creditos.setContentPane(panelCreditos);
        creditos.pack();
        creditos.setLocationRelativeTo(null);
        creditos.setVisible(true);
    }




    public static void detenerSonidoFondo() {
        sonidoFondo.stop();
        sonidoFondo.close();
    }
    public static void reproducirSonidoFondo(String rutaArchivo) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(rutaArchivo));
            sonidoFondo = AudioSystem.getClip();
            sonidoFondo.open(audioInputStream);
            sonidoFondo.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void reproducirSonidoClick() {
       try {
           AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\PC OSTRICH\\Proyecto-Final\\RecursosVisuales\\SonidoBoton.wav"));
           Clip clip = AudioSystem.getClip();
           clip.open(audioInputStream);
           clip.start();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }


}
