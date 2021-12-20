/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.model;

import java.util.HashMap;

/**
 *
 * @author hecto
 */
public class Pregunta {
    private String encunciado;
    private int respuestaCorrecta;
    private HashMap<Integer,String> respuestas;
    private Categoria categoria;

    public Pregunta(String encunciado, int respuestaCorrecta, HashMap<Integer, String> respuestas, Categoria categoria) {
        this.encunciado = encunciado;
        this.respuestaCorrecta = respuestaCorrecta;
        this.respuestas = respuestas;
        this.categoria = categoria;
    }



    public String getEncunciado() {
        return encunciado;
    }

    public int getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public HashMap<Integer, String> getRespuestas() {
        return respuestas;
    }

    public Categoria getCategoria() {
        return categoria;
    }
    
    
    
}
