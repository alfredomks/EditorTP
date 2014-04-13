/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorComponentes;

import java.util.HashMap;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;

public class MarcadorDePalabras {

    //private static TextoColor textoColor = new TextoColor();
    private static ConfigTexto configTxt = new ConfigTexto();
    private static HashMap<Integer, Integer> posLinea = new HashMap<Integer, Integer>();
    private static String[] palabrasClave1 = {
        "Grafica",
        "Datos",
        "Linea",
        "Area",
        "Barra"};
    private static String[] palabrasClave2 = {
        "inicio",
        "calcular",
        "agregar",
        "modificar",
        "muestra",
        "representar",
        "eliminar",
        "fin"};
    private static String[] limitadores = {
        ":",
        ")",
        "(",
        "]",
        "[",};
    private static String[] simbolosPorDefecto = {
        "*",
        "/",
        "+",
        "-",
        "%",
        "=",
        ","};

    public static void marcarElTexto(JTextPane jt, MyUndoableEditListener escuchaDesacer) {
        String texto = jt.getText();
        posLinea.clear();
        String token = "";
        DefaultStyledDocument documento = (DefaultStyledDocument) jt.getStyledDocument();
        documento.removeUndoableEditListener(escuchaDesacer);
        int linea = 0;
        int posicion=0;
        for (int i = 0; i < texto.length(); i++) {
            char x = texto.charAt(i);  
            if (!(x == ' ' || x == '\n' || x == '\t')) {
                token = token + x;
            ///**---------------------------------------------------
             ///**---------------------------------------------------
            } else if (x == '\n') {
                linea = linea + 1;
                posLinea.put(linea,posicion);
                posicion=i+1;
            }else {
                token = "";
            }

            if (x == (char) '"') {
                int aux = verificarCadena(texto, i);
                if (aux > -1) {
                    documento.setCharacterAttributes(i, aux, configTxt.getAtributo("texto"), false);//textoColor.texto
                    i = aux;
                    token = "";
                } else {
                    documento.setCharacterAttributes(i, texto.length(), configTxt.getAtributo("texto"), false);//textoColor.texto
                    return;
                }

            } else if (verificarSimbolo("" + x)) {
                //System.out.println("simbolo--> " + x);
                documento.setCharacterAttributes(i, (x + "").length(), configTxt.getAtributo("operador"), false);//textoColor.operador
                token = "";
            } else if (verificarLimitadores("" + x)) {
                //System.out.println("simbolo--> " + x);
                documento.setCharacterAttributes(i, (x + "").length(), configTxt.getAtributo("limitador"), false);//textoColor.operador
                token = "";
            } else if (verificarPalabraReservada1(token)) {
                // System.out.println("Token R1--> " + token);
                documento.setCharacterAttributes(1 + i - token.length(), token.length(), configTxt.getAtributo("reservada1"), false);//textoColor.reservada
                token = "";
            } else if (verificarPalabraReservada2(token)) {
                //System.out.println("Token R2--> " + token);
                documento.setCharacterAttributes(1 + i - token.length(), token.length(), configTxt.getAtributo("reservada2"), false);//textoColor.reservada
                token = "";
            } else if (verificarNumero(token)) {
                //System.out.println("Numero--> " + token);
                documento.setCharacterAttributes(1 + i - token.length(), token.length(), configTxt.getAtributo("numero"), false);
                token = "";
            } else {
                //System.out.println("Error--> " + token);
                documento.setCharacterAttributes(1 + i - token.length(), token.length(), configTxt.getAtributo("error"), false);
            }
        }
        documento.addUndoableEditListener(escuchaDesacer);
    }

    public static void marcarPalabraEncontrada(JTextPane jt, String token, int indice, MyUndoableEditListener escuchaDesacer) {
        StyledDocument documento = jt.getStyledDocument();//token.length(),configTxt.getAtributo("numero")
        documento.removeUndoableEditListener(escuchaDesacer);
        documento.setCharacterAttributes(indice, token.length(), configTxt.atributoFondoBusqueda, false);
        documento.addUndoableEditListener(escuchaDesacer);
    }

    public static void marcarErrorEncontrado(JTextPane jt, int inicio, int fin) {
        StyledDocument documento = jt.getStyledDocument();//token.length(),configTxt.getAtributo("numero")
        documento.setCharacterAttributes(inicio, fin, configTxt.getAtributo("error"), false);
    }
    //----------------------------------------------------------------------------------------------------

    public static void marcarFondoError(JTextPane jt, int numeroLinea, int columna) {
        StyledDocument documento = jt.getStyledDocument();
        documento.setCharacterAttributes(posLinea.get(numeroLinea), columna, configTxt.atributoFondoLineaError, false);
   }
    public static void desMarcarFondoError(JTextPane jt) {
        StyledDocument documento = jt.getStyledDocument();//Desmarcando errores
        documento.setCharacterAttributes(0, jt.getText().length(), configTxt.atributoFondoNormal, false);
    }
//----------------------------------------------------------------------------------------------------

    public static void desmarcarPalabrasEncontrada(JTextPane jt, MyUndoableEditListener escuchaDesacer) {
        StyledDocument documento = jt.getStyledDocument();//token.length(),configTxt.getAtributo("numero")
        documento.removeUndoableEditListener(escuchaDesacer);
        documento.setCharacterAttributes(0, jt.getText().length(), configTxt.atributoFondoNormal, false);
        documento.addUndoableEditListener(escuchaDesacer);
    }

    public static boolean verificarPalabraReservada1(String palabra) {
        for (int i = 0; i < palabrasClave1.length; i++) {
            String reservada = palabrasClave1[i];
            if (reservada.equals(palabra.trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean verificarPalabraReservada2(String palabra) {
        for (int i = 0; i < palabrasClave2.length; i++) {
            String reservada = palabrasClave2[i];
            if (reservada.equals(palabra.trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean verificarLimitadores(String palabra) {
        for (int i = 0; i < limitadores.length; i++) {
            String string = limitadores[i];
            if (string.equals(palabra)) {
                return true;
            }
        }
        return false;
    }

    public static boolean verificarSimbolo(String palabra) {
        for (int i = 0; i < simbolosPorDefecto.length; i++) {
            String string = simbolosPorDefecto[i];
            if (string.equals(palabra)) {
                return true;
            }
        }
        return false;
    }

    public static int verificarCadena(String texto, int index) {
        int i = -1;
        if (index + 1 < texto.length()) {
            String auxtext = texto.substring(index + 1, texto.length());
            i = auxtext.indexOf('"');
            if (i > -1) {
                i = i + 1 + index;//2
            }
        }
        return i;
    }

    public static boolean verificarNumero(String texto) {
        boolean esNumero = false;
        try {
            Double.parseDouble(texto);
            esNumero = true;
        } catch (Exception e) {
            esNumero = false;
        }
        return esNumero;
    }

    public static ConfigTexto getConfigTxt() {
        return configTxt;
    }

    public static void setConfigTxt(ConfigTexto configTxt) {
        MarcadorDePalabras.configTxt = configTxt;
    }
///---------------------------------------------------------

    public static String[] getLimitadores() {
        return limitadores;
    }

    public static void setLimitadores(String[] limitadores) {
        MarcadorDePalabras.limitadores = limitadores;
    }

    public static String[] getPalabrasClave1() {
        return palabrasClave1;
    }

    public static void setPalabrasClave1(String[] palabrasClave1) {
        MarcadorDePalabras.palabrasClave1 = palabrasClave1;
    }

    public static String[] getPalabrasClave2() {
        return palabrasClave2;
    }

    public static void setPalabrasClave2(String[] palabrasClave2) {
        MarcadorDePalabras.palabrasClave2 = palabrasClave2;
    }

    public static String[] getSimbolosPorDefecto() {
        return simbolosPorDefecto;
    }

    public static void setSimbolosPorDefecto(String[] simbolosPorDefecto) {
        MarcadorDePalabras.simbolosPorDefecto = simbolosPorDefecto;
    }
///--------------------------------------------------------------------

    public static void revalidar() {
        configTxt.iniciarEstilos();
        configTxt.iniciarEstiloCodigo();
    }
//----------------------------------------------------------------------------
    //-**-------------------------------------------------------------------

    //    public static void marcarElTexto2(DefaultStyledDocument documento, JTextPane jt) {
//        int offset = 0;
//        for (StringTokenizer stringTokenizer = new StringTokenizer(jt.getText()); stringTokenizer.hasMoreTokens();) {
//            String token = stringTokenizer.nextToken();
//            if (verificarPalabraReservada1(token)) { // verificar palabra clave
//                //Localizar offset es incorrecto por las espacios
//                documento.setCharacterAttributes(offset, token.length(), textoColor.reservada, false);
//            }
//            offset += token.length() + 1;
//        }
//    }
}
