import javax.swing.*;
import java.awt.*;

public class CampusUI extends JFrame {
    private JButton Negocios;
    private JButton Artes;
    private JButton Ciencias;
    private JButton Salud;
    

    public CampusUI() {
        setTitle("Campus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon houseIcon = new ImageIcon("campus.jpg");
        JLabel houseLabel = new JLabel(houseIcon);
        houseLabel.setBounds(0, 0, houseIcon.getIconWidth(), houseIcon.getIconHeight());
        add(houseLabel);

        // Botones del Campus
        Negocios = new JButton("Negocios");
        Negocios.setBounds(410, 120, 100, 30);
        add(Negocios);
        Artes = new JButton("Artes");
        Artes.setBounds(100, 120, 100, 30);
        add(Artes);
        Ciencias = new JButton("Ciencias");
        Ciencias.setBounds(180, 270, 100, 30);
        add(Ciencias);
        Salud = new JButton("Salud");
        Salud.setBounds(520, 310, 100, 30);
        add(Salud);
        
        setLayout(null);
        add(houseLabel);

        pack();
        setLocationRelativeTo(null);
        setTitle("Campus");
        setSize(600, 450); // Tamaño de la ventana
        setLocationRelativeTo(null); 

       
    }

    // Métodos para actualizar el estado de los botones en el campus
    public void setNegociosButton(boolean state) {
        Negocios.setBackground(state ? Color.GREEN : null);
    }

    public void setArtesButton(boolean state) {
        Artes.setBackground(state ? Color.GREEN : null);
    }

    public void setCienciasButton(boolean state) {
        Ciencias.setBackground(state ? Color.GREEN : null);
    }

    public void setSaludButton(boolean state) {
        Salud.setBackground(state ? Color.GREEN : null);
    }

    

    
}
