import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

class BackgroundPanel extends JPanel {
    private Image background;

   BackgroundPanel() {
        try {
            background = ImageIO.read(new File("fondo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class MainGUI extends JFrame {
    private final JTextField txtCapacidad;
    private final JTextField txtDetalles;
    private final JComboBox<String> cmbTipoAula;
    private final JList<String> lstRecursos;
    private final JTextField txtBuscarCapacidad;
    private final JComboBox<String> cmbBuscarTipoAula;
    private final JList<String> lstBuscarRecursos;
    private final JTextArea txtAreaResultados;
    private final ABB abb;
    private CampusUI campusUI;

    public MainGUI(CampusUI campusUI) {
        this.setTitle("Buscador de Aulas");
        this.campusUI = campusUI;
        abb = new ABB();
        txtCapacidad = new JTextField(10);
        txtDetalles = new JTextField(20);

        String[] tiposAula = {"Negocios", "Ciencias", "Artes", "Salud"};
        cmbTipoAula = new JComboBox<>(tiposAula);

        String[] recursos = {"Proyector", "Computadora", "Pizarra", "Laboratorio"};
        lstRecursos = new JList<>(recursos);
        lstRecursos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        txtBuscarCapacidad = new JTextField(10);
        cmbBuscarTipoAula = new JComboBox<>(tiposAula);
        lstBuscarRecursos = new JList<>(recursos);
        lstBuscarRecursos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        txtAreaResultados = new JTextArea(5, 30);
        JButton btnInsertar = new JButton("Insertar");
        JButton btnBuscar = new JButton("Buscar y Reducir");
        JButton borrarTexto = new JButton("Borrar");

        // Contenedor para la izquierda (Agregar aulas)
        JPanel leftPanel = new JPanel(); 
        Color customColor = new Color(234,116,42,255); 
        leftPanel.setBackground(customColor);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(new JLabel("Capacidad:"));
        leftPanel.add(txtCapacidad);
        leftPanel.add(new JLabel("Detalles:"));
        leftPanel.add(txtDetalles);
        leftPanel.add(new JLabel("Área:"));
        leftPanel.add(cmbTipoAula);
        leftPanel.add(new JLabel("Complemento:"));
        leftPanel.add(new JScrollPane(lstRecursos));
        leftPanel.add(btnInsertar);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Contenedor para la derecha (Buscar aulas)
        JPanel rightPanel = new JPanel();
        Color customColor2 = new Color(234,116,42,255); 
        rightPanel.setBackground(customColor2);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(new JLabel("Buscar Capacidad:"));
        rightPanel.add(txtBuscarCapacidad);
        rightPanel.add(new JLabel("Área:"));
        rightPanel.add(cmbBuscarTipoAula);
        rightPanel.add(new JLabel("Complemento:"));
        rightPanel.add(new JScrollPane(lstBuscarRecursos));
        rightPanel.add(btnBuscar);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Contenedor para la parte inferior (Resultados)
        JPanel bottomPanel = new JPanel();
        Color customColor3 = new Color(1,80,43,255); 
        bottomPanel.setBackground(customColor3);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(new JScrollPane(txtAreaResultados));
        bottomPanel.add(borrarTexto);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Contenedor principal
        BackgroundPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para agregar aulas
                try {
                    int capacidad = Integer.parseInt(txtCapacidad.getText());
                    String detalles = txtDetalles.getText();
                    String tipoAula = (String) cmbTipoAula.getSelectedItem();
                    List<String> recursosSeleccionados = lstRecursos.getSelectedValuesList();
                    String[] recursosArray = recursosSeleccionados.toArray(new String[0]);

                    abb.insert(capacidad, detalles, tipoAula, recursosArray);
                    txtAreaResultados.append("Aula insertada correctamente.\n");

                } catch (NumberFormatException ex) {
                    txtAreaResultados.append("Por favor, ingrese un número válido para la capacidad.\n");
                } catch (Exception ex) {
                    txtAreaResultados.append("Error al insertar aula: " + ex.getMessage() + "\n");
                }
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para buscar aulas
                try {
                    int capacidadBusqueda = Integer.parseInt(txtBuscarCapacidad.getText());
                    String tipoAulaBusqueda = (String) cmbBuscarTipoAula.getSelectedItem();
                    List<String> recursosBusqueda = lstBuscarRecursos.getSelectedValuesList();
                    String[] recursosBusquedaArray = recursosBusqueda.toArray(new String[0]);

                    Aula aulaAsignada = abb.findAndReduce(capacidadBusqueda, tipoAulaBusqueda, recursosBusquedaArray);

                    if (aulaAsignada != null) {
                        txtAreaResultados.append("Aula asignada: " + aulaAsignada.detalles + "\n");
                        if (tipoAulaBusqueda.equals("Negocios")) {
                            updateNegocios(true);
                        }
                        if (tipoAulaBusqueda.equals("Artes")) {
                            updateArtes(true);
                        }
                        if (tipoAulaBusqueda.equals("Ciencias")) {
                            updateCiencias(true);
                        }
                        if (tipoAulaBusqueda.equals("Salud")) {
                            updateSalud(true);
                        }
                    } else {
                        txtAreaResultados.append("No se encontró un aula adecuada.\n");
                    }

                } catch (NumberFormatException ex) {
                    txtAreaResultados.append("Por favor, ingrese un número válido para la capacidad de búsqueda.\n");
                } catch (Exception ex) {
                    txtAreaResultados.append("Error al buscar aula: " + ex.getMessage() + "\n");
                }
            }
        });

        borrarTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para borrar el texto y limpiar la GUI
                String borrar = "";
                txtAreaResultados.setText(borrar);
                txtBuscarCapacidad.setText(borrar);
                txtCapacidad.setText(borrar);
                txtDetalles.setText(borrar);
                updateNegocios(false);
                updateArtes(false);
                updateCiencias(false);
                updateSalud(false);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(800, 600); // Ajusta el tamaño según tus necesidades
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CampusUI campusUI = new CampusUI();
            MainGUI mainGUI = new MainGUI(campusUI);
            mainGUI.setVisible(true);
            campusUI.setVisible(true);
        });
    }

    public void updateNegocios(boolean state) {
        campusUI.setNegociosButton(state);
    }

    public void updateArtes(boolean state) {
        campusUI.setArtesButton(state);
    }

    public void updateCiencias(boolean state) {
        campusUI.setCienciasButton(state);
    }

    public void updateSalud(boolean state) {
        campusUI.setSaludButton(state);
    }
}
