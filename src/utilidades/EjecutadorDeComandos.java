/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import EditorComponentes.MarcadorDePalabras;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JTextPane;

/**
 *
 * @author root
 */
public class EjecutadorDeComandos extends Thread {

    String pathCompilador = "";
    String pathEjecutador = "";
    public static int compilarJava = 0;
    public static int ejecutarJava = 1;
    /**
    needed to get streams from JDK.
     */
    Process compile;
    /**
    used to retrieve errors when compiling a file.
     */
    String errors;
    /**
    used for getting output from the jdk
     */
    BufferedReader br;
    /**
    command for thread to run
     */
    String comando;
    String classname, classpath;
    private String resultado = " ";
    private JTextPane areaDeTextoSalida = null;
    private JTextPane areaDeTextoCodigo = null;
    private Vector<Integer[]> indicesDeMarcar;

    public EjecutadorDeComandos(String comando) {
        this.comando = comando;
        this.indicesDeMarcar = new Vector<Integer[]>();
//            System.out.println(comando);
    }

    public void run() {

        try {
            compile = Runtime.getRuntime().exec(comando);// Revisar en caso de utilizar un lenguje diferente a java
        } catch (Exception e) {
            resultado = ("\n Fallo la ejecucion del comando  " + " revisa bien el comando que quieres ejecutar:");
            return;
        }
        indicesDeMarcar.removeAllElements();
        resultado = recogerSalidas(compile.getInputStream());
        resultado = recogerErrores(compile.getErrorStream());
        //resultado=resultado+recogerEntradas(compile.getOutputStream());
        resultado = resultado + ('\n' + "Proceso terminado : ");
        areaDeTextoSalida.setText(resultado);
        marcarErrores( indicesDeMarcar);
        return;
    }

    public JTextPane getAreaDeTextoSalida() {
        return areaDeTextoSalida;
    }

    public void setAreaDeTextoSalida(JTextPane areaDeTexto) {
        this.areaDeTextoSalida = areaDeTexto;
    }
///-----------------------------------------------------------------------
    public JTextPane getAreaDeTextoCodigo() {
        return areaDeTextoCodigo;
    }

    public void setAreaDeTextoCodigo(JTextPane areaDeTextoCodigo) {
        this.areaDeTextoCodigo = areaDeTextoCodigo;
    }
 ///------------------------------------------------------------------------
    public String getResultado() {
        return resultado;
    }

    protected String recogerSalidas(InputStream entrada) {//compile.getInputStream()
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(entrada));
            String line = "";
            while (true) {
                line = br.readLine();
                if (line != null) {
                    resultado = resultado + line + '\n';
                    buscarIndicesError(line);
                } else {
                    br.close();
                    break;
                }
            }
        } catch (Exception e) {
            resultado = resultado + "\nError" + e;
            System.out.println(e);
        }
        return resultado;
    }

    protected void marcarErrores(Vector<Integer[]> indices) {
        MarcadorDePalabras.desMarcarFondoError(areaDeTextoCodigo);
        for (Iterator<Integer[]> it = indices.iterator(); it.hasNext();) {
            Integer[] limites = it.next();
            MarcadorDePalabras.marcarErrorEncontrado(areaDeTextoSalida, limites[0], limites[1]);
            MarcadorDePalabras.marcarFondoError(areaDeTextoCodigo,limites[2],limites[3]);
        }
    }

    protected void buscarIndicesError(String line) {
        int index = line.toLowerCase().indexOf("error");
        if (index > -1) {
            index = resultado.length()-1-line.length() + index;
            //System.out.println("Index "+index+" Linea "+line);
            Integer[] indices = {index, line.length(),getLineaError(line),getColumnaError(line)};
            indicesDeMarcar.add(indices);
        }
    }
    protected int getLineaError(String line) {
        int nl=-1;
        String aux="linea:";
        int index = line.indexOf(aux);
        int endIndex=line.indexOf(",");
        if (index > -1) {
            index = index+aux.length();
            String snl=line.substring(index, endIndex);
            //System.out.println("Numero Linea:"+snl);
            nl=Integer.parseInt(snl);
        }
        return nl;
    }
    protected int getColumnaError(String line) {
        int nc=2;
        String aux="columna:";
        int index = line.indexOf(aux);
        int endIndex=line.length();
        if (index > -1) {
            index = index+aux.length();
            String snl=line.substring(index, endIndex).trim();
           // System.out.println("Numero Linea:"+snl);
            nc=Integer.parseInt(snl);
        }
        return nc;
    }
    protected String recogerErrores(InputStream entrada) {//compile.getInputStream()
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(entrada));
            String line = "";

            while (true) {
                line = br.readLine();
                if (line != null) {
                    resultado = resultado + line + '\n';
                    buscarIndicesError(line);
                } else {
                    br.close();
                    break;
                }
            }
        } catch (Exception e) {
            resultado = resultado + "\nError" + e;
            System.out.println(e);
        }
        return resultado;
    }
    public Vector<Integer[]> getIndicesDeMarcar() {
        return indicesDeMarcar;
    }

}
