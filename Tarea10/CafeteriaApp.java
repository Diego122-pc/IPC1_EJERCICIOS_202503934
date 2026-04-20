import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class CafeteriaApp extends JFrame {

    // ---- Nodo ----
    static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;
        Nodo(T dato) { this.dato = dato; }
    }

    // ---- Cola con nodos enlazados ----
    static class Cola<T> {
        private Nodo<T> cabeza, cola;
        private int tamano;

        public synchronized void encolar(T elemento) {
            Nodo<T> nuevo = new Nodo<>(elemento);
            if (cola == null) { cabeza = cola = nuevo; }
            else { cola.siguiente = nuevo; cola = nuevo; }
            tamano++;
        }

        // synchronized obligatorio para evitar condiciones de carrera
        public synchronized T desencolar() {
            if (estaVacia()) return null;
            T dato = cabeza.dato;
            cabeza = cabeza.siguiente;
            if (cabeza == null) cola = null;
            tamano--;
            return dato;
        }

        public synchronized T frente() {
            return estaVacia() ? null : cabeza.dato;
        }

        public synchronized boolean estaVacia() { return tamano == 0; }

        public synchronized int tamano() { return tamano; }

        public synchronized void forEach(Consumer<T> accion) {
            Nodo<T> actual = cabeza;
            while (actual != null) {
                accion.accept(actual.dato);
                actual = actual.siguiente;
            }
        }
    }

    // ---- Cliente ----
    static class Cliente {
        private static final AtomicInteger contador = new AtomicInteger(1);
        private static final String[] PEDIDOS = {
            "Cafe", "Cappuccino", "Latte", "Espresso", "Mocha", "Americano"
        };

        final int id;
        final String nombre;
        final String tipoPedido;
        final int tiempoPreparacion; // segundos (2-5)
        final long tiempoIngreso;

        Cliente(String nombre) {
            Random r = new Random();
            this.id = contador.getAndIncrement();
            this.nombre = nombre;
            this.tipoPedido = PEDIDOS[r.nextInt(PEDIDOS.length)];
            this.tiempoPreparacion = 2 + r.nextInt(4);
            this.tiempoIngreso = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            return "#" + id + " " + nombre + " [" + tipoPedido + "]";
        }
    }

    // ---- Barista (hilo independiente) ----
    class Barista extends Thread {
        private final int idx;

        Barista(int idx, String nombre) {
            super(nombre);
            this.idx = idx;
            setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                Cliente cliente = cola.desencolar();

                if (cliente == null) {
                    try { Thread.sleep(200); } catch (InterruptedException e) { break; }
                    continue;
                }

                long espera = System.currentTimeMillis() - cliente.tiempoIngreso;
                setEstado(idx, "Atendiendo: " + cliente.nombre, Color.RED);
                refrescarCola();

                // Simular preparacion del pedido
                int pasos = cliente.tiempoPreparacion * 10;
                for (int i = 0; i <= pasos; i++) {
                    final int p = (i * 100) / pasos;
                    SwingUtilities.invokeLater(() -> barras[idx].setValue(p));
                    try { Thread.sleep(100); } catch (InterruptedException e) { break; }
                }

                contadoresBarista[idx]++;
                registrarAtencion(espera, contadoresBarista[idx]);

                SwingUtilities.invokeLater(() -> {
                    barras[idx].setValue(0);
                    labelsContador[idx].setText("Atendidos: " + contadoresBarista[idx]);
                });
                setEstado(idx, "Libre", new Color(0, 150, 0));
                refrescarCola();
            }
        }
    }

    // ---- Campos ----
    private final Cola<Cliente> cola = new Cola<>();
    private final AtomicInteger totalAtendidos = new AtomicInteger(0);
    private long sumaEsperas = 0;
    private final Object statsLock = new Object();

    private JTextField campoNombre;
    private DefaultListModel<String> modeloCola;
    private final JLabel[] labelsEstado = new JLabel[3];
    private final JProgressBar[] barras = new JProgressBar[3];
    private final JLabel[] labelsContador = new JLabel[3];
    private final int[] contadoresBarista = new int[3];
    private JLabel labelTotal, labelPromedio;

    // ---- Constructor ----
    public CafeteriaApp() {
        setTitle("Cafeteria - Sistema de Atencion");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(980, 520);
        setLayout(new BorderLayout(8, 8));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        add(buildPanelIngreso(), BorderLayout.NORTH);
        add(buildPanelCentral(), BorderLayout.CENTER);
        add(buildPanelEstadisticas(), BorderLayout.SOUTH);

        for (int i = 0; i < 3; i++) new Barista(i, "Barista-" + (i + 1)).start();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ---- Construccion de UI ----
    private JPanel buildPanelIngreso() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        p.setBorder(BorderFactory.createTitledBorder("Ingresar Cliente"));
        campoNombre = new JTextField(24);
        JButton btn = new JButton("Agregar a Cola");
        btn.addActionListener(e -> agregarCliente());
        campoNombre.addActionListener(e -> agregarCliente());
        p.add(new JLabel("Nombre del cliente:"));
        p.add(campoNombre);
        p.add(btn);
        return p;
    }

    private JPanel buildPanelCentral() {
        JPanel p = new JPanel(new BorderLayout(8, 8));

        modeloCola = new DefaultListModel<>();
        JList<String> lista = new JList<>(modeloCola);
        lista.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createTitledBorder("Cola de Espera"));
        scroll.setPreferredSize(new Dimension(280, 0));
        p.add(scroll, BorderLayout.WEST);

        JPanel panelBaristas = new JPanel(new GridLayout(1, 3, 10, 10));
        panelBaristas.setBorder(BorderFactory.createTitledBorder("Estaciones de Servicio"));
        for (int i = 0; i < 3; i++) panelBaristas.add(buildPanelBarista(i));
        p.add(panelBaristas, BorderLayout.CENTER);

        return p;
    }

    private JPanel buildPanelBarista(int i) {
        JPanel p = new JPanel(new GridLayout(3, 1, 5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Barista " + (i + 1)));
        p.setBackground(new Color(245, 245, 255));

        labelsEstado[i] = new JLabel("Libre", SwingConstants.CENTER);
        labelsEstado[i].setForeground(new Color(0, 150, 0));
        labelsEstado[i].setFont(labelsEstado[i].getFont().deriveFont(Font.BOLD, 13f));

        barras[i] = new JProgressBar(0, 100);
        barras[i].setStringPainted(true);
        barras[i].setString("En espera");

        labelsContador[i] = new JLabel("Atendidos: 0", SwingConstants.CENTER);

        p.add(labelsEstado[i]);
        p.add(barras[i]);
        p.add(labelsContador[i]);
        return p;
    }

    private JPanel buildPanelEstadisticas() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        p.setBorder(BorderFactory.createTitledBorder("Estadisticas Globales"));
        labelTotal = new JLabel("Total atendidos: 0");
        labelTotal.setFont(labelTotal.getFont().deriveFont(Font.BOLD));
        labelPromedio = new JLabel("Espera promedio: 0.0 seg");
        labelPromedio.setFont(labelPromedio.getFont().deriveFont(Font.BOLD));
        p.add(labelTotal);
        p.add(new JLabel("|"));
        p.add(labelPromedio);
        return p;
    }

    // ---- Logica ----
    private void agregarCliente() {
        String nombre = campoNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre de cliente.");
            return;
        }
        cola.encolar(new Cliente(nombre));
        refrescarCola();
        campoNombre.setText("");
        campoNombre.requestFocus();
    }

    private void refrescarCola() {
        SwingUtilities.invokeLater(() -> {
            modeloCola.clear();
            cola.forEach(c -> modeloCola.addElement(c.toString()));
        });
    }

    private void setEstado(int idx, String texto, Color color) {
        SwingUtilities.invokeLater(() -> {
            labelsEstado[idx].setText(texto);
            labelsEstado[idx].setForeground(color);
        });
    }

    private void registrarAtencion(long esperaMs, int contadorLocal) {
        int total;
        double promedio;
        synchronized (statsLock) {
            sumaEsperas += esperaMs;
            total = totalAtendidos.incrementAndGet();
            promedio = (sumaEsperas / 1000.0) / total;
        }
        final int t = total;
        final double pr = promedio;
        SwingUtilities.invokeLater(() -> {
            labelTotal.setText("Total atendidos: " + t);
            labelPromedio.setText(String.format("Espera promedio: %.1f seg", pr));
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CafeteriaApp::new);
    }
}
