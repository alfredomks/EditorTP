/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author root
 */
public class Lee_Escribe_Objetos {

    private String direccion;

    public Lee_Escribe_Objetos(String direccion) {
        this.direccion = direccion;
    }

    public void escribeOjeto(Object objeto) {
        try {
            FileOutputStream salida = new FileOutputStream(direccion);
            XMLEncoder encoder = new XMLEncoder(salida);
            encoder.writeObject(objeto);
            encoder.close();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public Object leeObjeto() {
        Object obj = null;
        try {
            FileInputStream entrada = new FileInputStream(direccion);
            XMLDecoder decoder = new XMLDecoder(entrada);
            obj = decoder.readObject();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return obj;

    }
}
