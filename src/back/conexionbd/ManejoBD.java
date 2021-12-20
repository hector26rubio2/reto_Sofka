/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.conexionbd;

import back.model.Categoria;
import back.model.Participante;
import back.model.Pregunta;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hecto
 */
public class ManejoBD {

    private final String URL = "jdbc:mysql://db4free.net/bancopreguntasbd";
    private final String NAME = "com.mysql.cj.jdbc.Driver";
    private final String USUARIO = "usuariobancop";
    private final String PASSWORD = "CA666ekKH!9tsPW";

    public ManejoBD() {

    }

    private Connection abrirConexion() throws ManejoBDException {
        Connection conexion = null;
        try {

            Class.forName(NAME);

            conexion = (Connection) DriverManager.getConnection(URL, USUARIO, PASSWORD);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ManejoBDException(
                    "no se conectar ala bd"
            );
        }
        return conexion;

    }

    private boolean cerrarConexion(Connection conexion) {
        try {
            if (conexion != null) {
                conexion.close();
                return true;
            }
        } catch (Exception e) {
        }

        return false;

    }

    public void insertarCategoria(Categoria categoria) throws ManejoBDException {

        try {
            Connection conexion = abrirConexion();
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO categoria "
                    + "(nombre,dificultad,premio) VALUES (?,?,?)");
            stmt.setString(1, categoria.getNombre());
            stmt.setInt(2, categoria.getDificultad());
            stmt.setInt(3, categoria.getPremio());
            stmt.executeUpdate();

            stmt.close();
            cerrarConexion(conexion);
        } catch (SQLException ex) {
            throw new ManejoBDException(
                    "no se pudo guardar la categoria"
            );
        }

    }

    public void insertarPregunta(Pregunta pregunta) throws ManejoBDException {

        try {
            Connection conexion = abrirConexion();
            PreparedStatement stmt = conexion.prepareStatement("insert into"
                    + " pregunta (descripcion,respuesta,opcion1,opcion2,opcion3,"
                    + "opcion4,idCategoria) values (?,?,?,?,?,?,?)");
            stmt.setString(1, pregunta.getEncunciado());
            stmt.setInt(2, pregunta.getRespuestaCorrecta());
            for (Map.Entry<Integer, String> opcion : pregunta.getRespuestas().entrySet()) {
                stmt.setString(opcion.getKey() + 2, opcion.getValue());
            }
            int idCategoria = obtenerIdCategoria(pregunta.getCategoria().getNombre(), conexion);
            stmt.setInt(7, idCategoria);

            stmt.executeUpdate();

            stmt.close();
            cerrarConexion(conexion);
        } catch (SQLException ex) {
            throw new ManejoBDException(
                    "no se pudo guardar la categoria"
            );
        }

    }

    private int obtenerIdCategoria(String nombre, Connection conexion) throws ManejoBDException {
        int id = 0;
        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("select id from categoria where nombre='" + nombre + "'");

            if (rs.next()) {
                id = Integer.parseInt(rs.getString("id"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            throw new ManejoBDException(
                    "no se pudo obtener el id" + " de la categoria"
            );
        }
        return id;
    }

    public void insertarParticipante(Participante participante) throws ManejoBDException {

        try {
            Connection conexion = abrirConexion();
            PreparedStatement stmt = conexion.prepareStatement("insert into "
                    + "participante (nombre,fecha,premio)"
                    + " values (?,DATE_FORMAT(?,'%Y/%m/%d'),?)");
            stmt.setString(1, participante.getNombre());
            stmt.setString(2, obtenerFecha());
            stmt.setInt(3, participante.getPremioGanado());
            System.out.println(stmt.toString());
            stmt.executeUpdate();

            stmt.close();
            cerrarConexion(conexion);
        } catch (SQLException ex) {
            throw new ManejoBDException(
                    "no se pudo guardar el participante"
            );
        }

    }

    private String obtenerFecha() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public ArrayList obtenerCategorias() throws ManejoBDException {
        ArrayList<Categoria> categorias = new ArrayList<>();
        try {
            Connection conexion = abrirConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("select nombre,dificultad,premio from categoria");

            while (rs.next()) {
                categorias.add(new Categoria(rs.getString("nombre"),
                        rs.getInt("dificultad"), rs.getInt("premio")));

            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            throw new ManejoBDException(
                    "no se pudo obtener el id" + " de la categoria"
            );
        }
        return categorias;
    }

    public ArrayList<Pregunta> getPreguntasCategoria(Categoria categoria) throws ManejoBDException {
        ArrayList<Pregunta> preguntas = new ArrayList<>();
        try {
            Connection conexion = abrirConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("select p.descripcion,"
                    + "p.respuesta,p.opcion1,p.opcion2,p.opcion3,p.opcion4"
                    + " from pregunta p join categoria c on p.idCategoria = c.id"
                    + "  where c.nombre ='" + categoria.getNombre() + "'");

            while (rs.next()) {
                HashMap<Integer, String> respuestas = new HashMap<>();
                for (int i = 1; i <= 4; i++) {
                    respuestas.put(i, rs.getString("opcion" + i));
                }                
                preguntas.add(new Pregunta(rs.getString("descripcion"),
                        rs.getInt("respuesta"), respuestas, categoria));

            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            throw new ManejoBDException(
                    "no se pudo obtener el id" + " de la categoria"
            );
        }
        return preguntas;
    }

    public ArrayList<Participante> obtenerParticipantes() throws ManejoBDException {
         ArrayList<Participante> participantes = new ArrayList<>();
        try {
            Connection conexion = abrirConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("select nombre,fecha,premio "
                    + "from participante order by premio desc");

            while (rs.next()) {
                participantes.add(new Participante(rs.getString("nombre")
                ,rs.getString("fecha"),rs.getInt("premio")));

            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            throw new ManejoBDException(
                    "no se pudo obtener participantes"
            );
        }
        return participantes;
    }

}
