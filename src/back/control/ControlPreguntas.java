/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.control;

import back.conexionbd.ManejoBD;
import back.conexionbd.ManejoBDException;
import back.model.Categoria;
import back.model.Participante;
import back.model.Pregunta;
import back.model.Ronda;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author hecto
 */
public class ControlPreguntas {

    private static final int NUMERO_DE_RONDAS = 5;

    private int numeroRonda;
    private ArrayList<Categoria> categorias;
    private Participante participante;
    private ManejoBD mbd;

    private HashMap<Integer, Ronda> rondas;

    public ControlPreguntas() throws ManejoBDException {
        numeroRonda = 1;
        mbd = new ManejoBD();
        categorias = new ArrayList<>();

        rondas = new HashMap<>();
        generarRondasAleatoria();
      

    }

    public void addUsuario(String infoUsuario) {
        participante = new Participante(infoUsuario);
    }

    public void guardarCategoria(String nombre, int dificultad, int premio) throws ManejoBDException {
        Categoria categoria = new Categoria(nombre, dificultad, premio);
        categorias.add(categoria);
        mbd.insertarCategoria(categoria);

    }

    public ArrayList getCategoriasNombre() throws ManejoBDException {
        ArrayList<String> nombreCategorias = new ArrayList<>();
        for (Iterator it = mbd.obtenerCategorias().iterator(); it.hasNext();) {
            Categoria categoria = (Categoria) it.next();
            categorias.add(categoria);
            nombreCategorias.add(categoria.getNombre());
        }
        return nombreCategorias;
    }

    public void guardarPregunta(String enunciado, int respuesta, String categoria, HashMap<Integer, String> opciones) throws ManejoBDException {
        Pregunta pregunta = new Pregunta(enunciado, respuesta, opciones, obtenerCategoria(categoria));
        mbd.insertarPregunta(pregunta);
    }

    private Categoria obtenerCategoria(String nombre) {
        Categoria posible = null;
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equals(nombre)) {
                posible = categoria;
            }
        }
        return posible;
    }

    public String getNombreUsuario() {
        return participante.getNombre();
    }

    public void setGanancia(int ganacia) {

        participante.setPremioGanado(ganacia);
    }

    private void generarRondasAleatoria() throws ManejoBDException {
        rondas.clear();
        categorias.clear();
        categorias = mbd.obtenerCategorias();
        for (int i = 1; i <= 5; i++) {

            Categoria categoria = categoriaPorDificultadAleatorio(i);

            if (categoria != null) {
                Ronda r = new Ronda(categoria, preguntaAleatoriaCategoria(categoria));
                rondas.put(i, r);
            }
        }

    }

    private Categoria categoriaPorDificultadAleatorio(int dificultad) throws ManejoBDException {
        ArrayList<Categoria> nCategorias = new ArrayList<>();
        String nombrecategoria = "";
        for (Categoria categoria : categorias) {

            if (categoria.getDificultad() == dificultad) {
                nCategorias.add(categoria);
            }
        }
        int max = nCategorias.size() - 1;
        int min = 0;
        int valorEntero = (int) Math.floor(Math.random() * (min - max + 1) + max);
        if (valorEntero < 0) {
            throw new ManejoBDException("no hay categoria para esta dificultad " + dificultad);
        }
        Categoria categoria = null;
        if (!categorias.isEmpty()) {
            categoria = nCategorias.get(valorEntero);
        }
        return categoria;
    }

    private Pregunta preguntaAleatoriaCategoria(Categoria categoria) throws ManejoBDException {
        ArrayList<Pregunta> preguntas = new ArrayList<>();
        for (Pregunta pregunta : mbd.getPreguntasCategoria(categoria)) {
            preguntas.add(pregunta);
        }
        int max = preguntas.size() - 1;
        int min = 0;
        int valorEntero = (int) Math.floor(Math.random() * (min - max + 1) + max);
        if (valorEntero < 0) {
            throw new ManejoBDException("no hay preguntas para esta categoria " + categoria.getNombre());
        }
 
        return preguntas.get(valorEntero);
    }

    public void setRonda(int i) {
        numeroRonda = i;
    }

    public int getRonda() {
        return numeroRonda;
    }

  

    public String getCategoriaRonda(int i) {
      return rondas.get(i).getCategoria().getNombre();
    }
    public HashMap<Integer, Ronda> getRondas() {
        return rondas;
    }

    public ArrayList<Participante> getPuntajes() throws ManejoBDException {
        return  mbd.obtenerParticipantes();
    }

    public ArrayList<Categoria> getCategorias() throws ManejoBDException {
         ArrayList<Categoria> nombreCategorias = new ArrayList<>();
        for (Iterator it = mbd.obtenerCategorias().iterator(); it.hasNext();) {
            Categoria categoria = (Categoria) it.next();
            categorias.add(categoria);
            nombreCategorias.add(categoria);
        }
        return nombreCategorias;
    }

    public Pregunta getPreguntaRonda(int i) throws Exception {
        if(i>5)
        {
            throw new Exception("felicidades");
        }
        return rondas.get(i).getPregunta();
    }

    public int getRespuestaPreguntaRonga(int ronda) {
       return  rondas.get(ronda).getPregunta().getRespuestaCorrecta();
    }

    public void aumentarRonda() {
        numeroRonda++;
    }

    public int getPremioRonda(int ronda) {
        return  rondas.get(ronda).getCategoria().getPremio();
    }


    public void reiniciar() throws ManejoBDException {
        participante = null;
        numeroRonda = 1;
        
        generarRondasAleatoria();
        
    }

    public void guardarParticipante(int premio) throws ManejoBDException {
       participante.setPremioGanado(premio);
        mbd.insertarParticipante(participante);
    }

}
