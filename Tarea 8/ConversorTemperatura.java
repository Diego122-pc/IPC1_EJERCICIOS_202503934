import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConversorTemperatura extends JFrame {

    private JTextField campoCelsius;
    private JLabel labelResultado;

    public ConversorTemperatura() {
        setTitle("Conversor de Temperaturas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel labelTitulo = new JLabel("Ingrese temperatura en Celsius:", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 14));

        campoCelsius = new JTextField();
        campoCelsius.setFont(new Font("Arial", Font.PLAIN, 14));
        campoCelsius.setHorizontalAlignment(JTextField.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton btnFahrenheit = new JButton("Convertir a Fahrenheit");
        JButton btnKelvin = new JButton("Convertir a Kelvin");
        panelBotones.add(btnFahrenheit);
        panelBotones.add(btnKelvin);

        labelResultado = new JLabel("Resultado: ", SwingConstants.CENTER);
        labelResultado.setFont(new Font("Arial", Font.BOLD, 16));
        labelResultado.setForeground(new Color(0, 100, 180));

        panel.add(labelTitulo);
        panel.add(campoCelsius);
        panel.add(panelBotones);
        panel.add(labelResultado);

        add(panel);

        btnFahrenheit.addActionListener(e -> convertir("F"));
        btnKelvin.addActionListener(e -> convertir("K"));
    }

    private void convertir(String unidad) {
        String texto = campoCelsius.getText().trim();
        try {
            double celsius = Double.parseDouble(texto);
            if (unidad.equals("F")) {
                double fahrenheit = celsius * 9.0 / 5.0 + 32;
                labelResultado.setText(String.format("Resultado: %.2f °F", fahrenheit));
            } else {
                double kelvin = celsius + 273.15;
                labelResultado.setText(String.format("Resultado: %.2f K", kelvin));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Por favor ingrese un valor numérico válido.",
                "Error de entrada",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ConversorTemperatura().setVisible(true);
        });
    }
}
