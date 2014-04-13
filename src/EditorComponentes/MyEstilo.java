/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EditorComponentes;

import java.awt.Color;

public class MyEstilo {
    private String nombre;
    private Color color;
    private boolean negrita;
    private boolean cursiva;
    private boolean subrrayado;

    public MyEstilo() {
        this.nombre="Todo";
        this.color=Color.BLACK;
        this.negrita=false;
        this.cursiva=false;
        this.subrrayado=false;
    }
    public MyEstilo(String nombre, Color color, boolean negrita, boolean cursiva, boolean subrrayado) {
        this.nombre = nombre;
        this.color = color;
        this.negrita = negrita;
        this.cursiva = cursiva;
        this.subrrayado = subrrayado;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isCursiva() {
        return cursiva;
    }

    public void setCursiva(boolean cursiva) {
        this.cursiva = cursiva;
    }

    public boolean isNegrita() {
        return negrita;
    }

    public void setNegrita(boolean negrita) {
        this.negrita = negrita;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isSubrrayado() {
        return subrrayado;
    }

    public void setSubrrayado(boolean subrrayado) {
        this.subrrayado = subrrayado;
    }
    

}
