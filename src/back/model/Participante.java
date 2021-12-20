/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.model;

import java.util.Date;

/**
 *
 * @author hecto
 */
public class Participante {

    private String nombre;
    private String fecha;
 
    private int PremioGanado;

    public Participante(String nombre) {
        this.nombre = nombre;
    }

    public Participante(String nombre, String fecha, int PremioGanado) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.PremioGanado = PremioGanado;
    }

    public String getFecha() {
        return fecha;
    }
    
    
    public String toString(){
        return nombre+"  "+fecha+" "+PremioGanado;
    }


    
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }



    /**
     * @return the PremioGanado
     */
    public int getPremioGanado() {
        return PremioGanado;
    }

    /**
     * @param PremioGanado the PremioGanado to set
     */
    public void setPremioGanado(int PremioGanado) {
        this.PremioGanado = PremioGanado;
    }

}
