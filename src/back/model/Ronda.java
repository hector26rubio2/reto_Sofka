/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.model;

/**
 *
 * @author hecto
 */
public class Ronda {
   private Categoria  categoria;
   private Pregunta pregunta;

    public Ronda(Categoria categoria, Pregunta pregunta) {
        this.categoria = categoria;
        this.pregunta = pregunta;
    }

    /**
     * @return the categoria
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @return the pregunta
     */
    public Pregunta getPregunta() {
        return pregunta;
    }
  
}
