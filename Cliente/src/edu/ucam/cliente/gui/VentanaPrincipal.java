package edu.ucam.cliente.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import edu.ucam.cliente.ClienteERP;
import edu.ucam.domain.Asignatura;
import edu.ucam.domain.Matricula;
import edu.ucam.domain.Titulacion;

public class VentanaPrincipal extends JFrame {

    private final ClienteERP cliente;
    private final JTextArea areaSalida;

    public VentanaPrincipal(ClienteERP cliente) {
        this.cliente = cliente;

        setTitle("Gestión Universidad");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        /* ================= PANEL BOTONES ================= */

        JPanel panelBotones = new JPanel(new GridLayout(0, 1, 5, 5));

        JButton btnAddTit = new JButton("Añadir Título");
        JButton btnGetTit = new JButton("Consultar Título");
        JButton btnAddAsig = new JButton("Añadir Asignatura");
        JButton btnGetAsig = new JButton("Consultar Asignatura");
        JButton btnAddMat = new JButton("Añadir Matrícula");
        JButton btnGetMat = new JButton("Consultar Matrícula");
        JButton btnDelTit = new JButton("Borrar Título");
        JButton btnDelAsig = new JButton("Borrar Asignatura");
        JButton btnDelMat = new JButton("Borrar Matrícula");
        JButton btnListTit = new JButton("Listar Títulos");
        JButton btnListAsig = new JButton("Listar Asignaturas");
        JButton btnLink = new JButton("Vincular Asignatura a Título");
        JButton btnListFromTit = new JButton("Ver Asignaturas de un Título");
        JButton btnUnlink = new JButton("Desvincular Asignatura de Título");
        JButton btnTotalTit = new JButton("Total de Títulos");
        JButton btnSesiones = new JButton("Usuarios Conectados");
        JButton btnModTit = new JButton("Modificar Título");
        JButton btnModMat = new JButton("Modificar Matrícula");
        JButton btnSalir = new JButton("Salir");

        for (JButton b : new JButton[]{
                btnAddTit, btnGetTit, btnAddAsig, btnGetAsig,
                btnAddMat, btnGetMat, btnDelTit, btnDelAsig, btnDelMat,
                btnListTit, btnListAsig, btnLink, btnListFromTit,
                btnUnlink, btnTotalTit, btnSesiones, btnModTit,
                btnModMat, btnSalir
        }) {
            panelBotones.add(b);
        }

        /* ================= ÁREA SALIDA ================= */

        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        areaSalida.setFont(new Font("Monospaced", Font.PLAIN, 12));

        add(new JScrollPane(panelBotones), BorderLayout.WEST);
        add(new JScrollPane(areaSalida), BorderLayout.CENTER);

        /* ================= ACCIONES ================= */

        btnAddTit.addActionListener(e -> addTitulo());
        btnGetTit.addActionListener(e -> getTitulo());
        btnAddAsig.addActionListener(e -> addAsignatura());
        btnGetAsig.addActionListener(e -> getAsignatura());
        btnAddMat.addActionListener(e -> addMatricula());
        btnGetMat.addActionListener(e -> getMatricula());
        btnDelTit.addActionListener(e -> borrarTitulo());
        btnDelAsig.addActionListener(e -> borrarAsignatura());
        btnDelMat.addActionListener(e -> borrarMatricula());
        btnListTit.addActionListener(e -> listarTitulos());
        btnListAsig.addActionListener(e -> listarAsignaturas());
        btnLink.addActionListener(e -> vincularAsignatura());
        btnListFromTit.addActionListener(e -> listarAsignaturasDeTitulo());
        btnUnlink.addActionListener(e -> desvincularAsignatura());
        btnTotalTit.addActionListener(e -> totalTitulos());
        btnSesiones.addActionListener(e -> sesionesActivas());
        btnModTit.addActionListener(e -> modificarTitulo());
        btnModMat.addActionListener(e -> modificarMatricula());
        btnSalir.addActionListener(e -> salir());

        setVisible(true);
    }

    /* ================= IMPLEMENTACIÓN (1:1 con consola) ================= */

    private void addTitulo() {
        String id = pedir("ID del título");
        String nombre = pedir("Nombre");
        try {
            Titulacion t = new Titulacion();
            t.setId(id); t.setNombre(nombre);
            cliente.insertarTitulo(t);
            log("Título añadido");
        } catch (Exception e) { error(e); }
    }

    private void getTitulo() {
        String id = pedir("ID del título");
        if (id == null || id.isBlank()) return;

        try {
            Titulacion t = cliente.obtenerTitulo(id);

            if (t != null) {
                areaSalida.append(
                    "ID:     " + t.getId() + "\n" +
                    "Nombre: " + t.getNombre() + "\n\n"
                );
            } else {
                areaSalida.append("No encontrado.\n\n");
            }

        } catch (Exception e) {
            error(e);
        }
    }


    private void addAsignatura() {
        try {
            Asignatura a = new Asignatura();
            a.setId(pedir("ID asignatura"));
            a.setNombre(pedir("Nombre"));
            cliente.insertarAsignatura(a);
            log("Asignatura añadida");
        } catch (Exception e) { error(e); }
    }

    private void getAsignatura() {
        String id = pedir("ID asignatura");
        if (id == null || id.isBlank()) return;

        try {
            Asignatura a = cliente.obtenerAsignatura(id);

            if (a != null) {
                areaSalida.append(
                    "ID:     " + a.getId() + "\n" +
                    "Nombre: " + a.getNombre() + "\n\n"
                );
            } else {
                areaSalida.append("No encontrada.\n\n");
            }

        } catch (Exception e) {
            error(e);
        }
    }


    private void addMatricula() {
        try {
            Matricula m = new Matricula();
            m.setId(pedir("ID matrícula"));
            cliente.insertarMatricula(m);
            log("Matrícula añadida");
        } catch (Exception e) { error(e); }
    }

    private void getMatricula() {
        String id = pedir("ID matrícula");
        if (id == null || id.isBlank()) return;

        try {
            Matricula m = cliente.obtenerMatricula(id);

            if (m != null) {
                areaSalida.append(
                    "ID: " + m.getId() + "\n\n"
                );
            } else {
                areaSalida.append("No encontrada.\n\n");
            }

        } catch (Exception e) {
            error(e);
        }
    }


    private void borrarTitulo() {
        try {
            cliente.borrarTitulo(pedir("ID título"));
            log("Título borrado");
        } catch (Exception e) {
            error(e);
        }
    }

    private void borrarAsignatura() {
        try {
            cliente.borrarAsignatura(pedir("ID asignatura"));
            log("Asignatura borrada");
        } catch (Exception e) {
            error(e);
        }
    }

    private void borrarMatricula() {
        try {
            cliente.borrarMatricula(pedir("ID matrícula"));
            log("Matrícula borrada");
        } catch (Exception e) {
            error(e);
        }
    }


    private void listarTitulos() {
        try {
            List<Titulacion> lista = cliente.listarTitulos();
            lista.forEach(t -> log("- [" + t.getId() + "] " + t.getNombre()));
        } catch (Exception e) { error(e); }
    }

    private void listarAsignaturas() {
        try {
            List<Asignatura> lista = cliente.listarAsignaturas();
            lista.forEach(a -> log("- [" + a.getId() + "] " + a.getNombre()));
        } catch (Exception e) { error(e); }
    }

    private void vincularAsignatura() {
        try {
            cliente.vincularAsignatura(
                pedir("ID asignatura"),
                pedir("ID título")
            );
            log("Asignatura vinculada");
        } catch (Exception e) {
            error(e);
        }
    }


    private void listarAsignaturasDeTitulo() {
        try {
            cliente.listarAsignaturasDeTitulo(pedir("ID título"))
                    .forEach(a -> log("- " + a.getNombre()));
        } catch (Exception e) { error(e); }
    }

    private void desvincularAsignatura() {
        try {
            cliente.desvincularAsignatura(
                pedir("ID asignatura"),
                pedir("ID título")
            );
            log("Asignatura desvinculada");
        } catch (Exception e) {
            error(e);
        }
    }


    private void totalTitulos() {
        log("Total títulos: " + cliente.obtenerTotalTitulos());
    }

    private void sesionesActivas() {
        try {
            log("Usuarios conectados: " + cliente.obtenerSesionesActivas());
        } catch (Exception e) { error(e); }
    }

    private void modificarTitulo() {
        try {
            String id = pedir("ID título");
            String nuevo = pedir("Nuevo nombre");
            cliente.actualizarTitulo(id, nuevo);
            log("Título modificado");
        } catch (Exception e) { error(e); }
    }

    private void modificarMatricula() {
        try {
            String id = pedir("ID matrícula");
            Matricula m = new Matricula();
            m.setId(id);
            cliente.actualizarMatricula(id, m);
            log("Matrícula modificada");
        } catch (Exception e) { error(e); }
    }

    private void salir() {
        try { cliente.cerrarSesion(); } catch (Exception ignored) {}
        System.exit(0);
    }

    /* ================= UTILIDADES ================= */

    private String pedir(String msg) {
        return JOptionPane.showInputDialog(this, msg);
    }

    private void log(String msg) {
        areaSalida.append(msg + "\n");
    }

    private void error(Exception e) {
        log("ERROR: " + e.getMessage());
    }

    private void ejecutar(Runnable r) {
        try { r.run(); log("Operación realizada"); }
        catch (Exception e) { error(e); }
    }
}
