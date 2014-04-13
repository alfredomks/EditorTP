/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorComponentes;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class ConfigTexto {          

    private Object[][] tipoEstilos = {
        //Tipo color negrita cursiva subrrayado
        {"reservada1", Color.BLUE, true, false, false},
        {"reservada2", Color.BLUE, false, false, false},
        {"operador", Color.ORANGE, false, false, false},
        {"limitador", Color.MAGENTA, false, false, false},
        {"numero", Color.GREEN, false, false, false},
        {"texto", Color.GRAY, false, true, false},
        {"error", Color.RED, false, false, false},
        {"variable", Color.PINK, false, false, false},
        {"normal", Color.BLACK, false, false, false}};
    
    private Vector configEstilos;
    private HashMap<String, SimpleAttributeSet> estiloCodigo;
    public SimpleAttributeSet atributoFondoBusqueda = new SimpleAttributeSet();
    public SimpleAttributeSet atributoFondoLineaError = new SimpleAttributeSet();
    public SimpleAttributeSet atributoFondoNormal = new SimpleAttributeSet();
    public ConfigTexto() {
        configEstilos = new Vector();
        estiloCodigo = new HashMap<String, SimpleAttributeSet>();
        iniciarEstilos();
        iniciarEstiloCodigo();
    }

    public ConfigTexto(Object[][] tipoEstilos) {
        this.tipoEstilos = tipoEstilos;
        configEstilos = new Vector();
        estiloCodigo = new HashMap<String, SimpleAttributeSet>();
        iniciarEstilos();
        iniciarEstiloCodigo();

    }
    ///----------------------------------------------------------------------------------------

    public void iniciarEstilos() {
        for (int i = 0; i < tipoEstilos.length; i++) {
            configEstilos.add(new MyEstilo(tipoEstilos[i][0].toString(), (Color) tipoEstilos[i][1], (Boolean) tipoEstilos[i][2], (Boolean) tipoEstilos[i][3], (Boolean) tipoEstilos[i][4]));
        }
    }

    public void iniciarEstiloCodigo() {
        for (Iterator it = configEstilos.iterator(); it.hasNext();) {
            SimpleAttributeSet atrib = new SimpleAttributeSet();
            MyEstilo estilo = (MyEstilo) it.next();
            setColorLetra(atrib, estilo.getColor());
            setLetraNegrita(atrib, estilo.isNegrita());
            setLetraCursiva(atrib, estilo.isCursiva());
            setLetraSubrrayado(atrib, estilo.isSubrrayado());
            estiloCodigo.put(estilo.getNombre(), atrib);
        }
        setColorFondo(atributoFondoBusqueda,Color.YELLOW);
        setColorFondo(atributoFondoLineaError,Color.PINK);
        setColorFondo(atributoFondoNormal,Color.WHITE);
    }
    ///----------------------------------------------------------------------------------------------

    public SimpleAttributeSet setColorLetra(SimpleAttributeSet atributo, Color color) {
        StyleConstants.setForeground(atributo, color);
        return atributo;
    }

    public SimpleAttributeSet setLetraNegrita(SimpleAttributeSet atributo, boolean activar) {
        StyleConstants.setBold(atributo, activar);
        return atributo;
    }

    public SimpleAttributeSet setLetraCursiva(SimpleAttributeSet atributo, boolean activar) {
        StyleConstants.setItalic(atributo, activar);
        return atributo;
    }

    public SimpleAttributeSet setLetraSubrrayado(SimpleAttributeSet atributo, boolean activar) {
        StyleConstants.setUnderline(atributo, activar);
        return atributo;
    }
    //---------------------------------------------------------------------------------
    public SimpleAttributeSet setColorFondo(SimpleAttributeSet atributo,Color color) {
        StyleConstants.setBackground(atributo,color);
        return atributo;
    }
    ///----------------------------------------------------------------------------------------------

    public SimpleAttributeSet getAtributo(String tipo) {
        return estiloCodigo.get(tipo);
    }
    //----------------------------------------------------------------------------------------------

    public ConfigTexto(Vector configEstilo) {
        this.configEstilos = configEstilo;
    }

    public Vector getConfigEstilos() {
        return configEstilos;
    }

    public void setConfigEstilos(Vector configEstilo) {
        this.configEstilos = configEstilo;
    }

    public HashMap<String, SimpleAttributeSet> getEstiloCodigo() {
        return estiloCodigo;
    }

    public void setEstiloCodigo(HashMap<String, SimpleAttributeSet> estiloCodigo) {
        this.estiloCodigo = estiloCodigo;
    }
//-------------------------------------------------------------------------------------------------

    public Object[][] getTipoEstilos() {
        return tipoEstilos;
    }

    public void setTipoEstilos(Object[][] tipoEstilos) {
        this.tipoEstilos = tipoEstilos;
        configEstilos.removeAllElements();
        estiloCodigo.clear();
        iniciarEstilos();
        iniciarEstiloCodigo();
    }
    //--------------------------------------------------------------------------------------------
}
