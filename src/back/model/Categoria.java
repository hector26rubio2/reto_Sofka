/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.model;

import java.util.ArrayList;

/**
 *
 * @author hecto
 */
public class Categoria {
    private String nombre;
    private int Dificultad;
    private int premio;
  

    public Categoria(String nombre, int Dificultad, int premio) {
        this.nombre = nombre;
        this.Dificultad = Dificultad;
        this.premio = premio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDificultad() {
        return Dificultad;
    }

    public int getPremio() {
        return premio;
    }
    
    

    
    
    
}
