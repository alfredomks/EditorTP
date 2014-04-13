/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import editortp.*;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Hilo extends Thread {
    private JProgressBar barra;
    private JLabel etIndicador;
    private Field[] atributos;
    private Inicio wi;
    EditorTPView p;

    public Hilo(Inicio wi,EditorTPView principal) {
        this.wi = wi;
        this.barra = wi.getBarra();
        this.p = principal;
        atributos = p.getClass().getDeclaredFields();//getDeclaredFields()
        barra.setMaximum(atributos.length);
        this.etIndicador = wi.getEtIndicador();

    }

    public void run() {
        for (int i = 0; i < atributos.length; i++) {
            try {
                sleep(100);
                barra.setValue(i);
                etIndicador.setText("Inicializando: " + atributos[i].getName());
            } catch (InterruptedException ex) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        wi.dispose();
        super.run();
    }
}
