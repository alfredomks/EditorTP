package utilidades;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class LecturaDeArchivos {
	private static DataInputStream dis=null;
	private static String aux=null;
	@SuppressWarnings("deprecation")
	public static void leerArchivoyCargarLista(String dirFile,DefaultListModel lista){
		//lectura de archivos
		
				try {
				     dis=new DataInputStream(new BufferedInputStream(new FileInputStream(dirFile)));
					try {
						  while((aux=dis.readLine())!=null){
							  lista.addElement(aux); 
						  }
						  dis.close();
						} catch (IOException e) {
								// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 } catch (FileNotFoundException e) {
						e.printStackTrace();
				}
	}
	public static String leerArchivo(String dirFile){
		//lectura de archivos
        String texto="";
				try {
				     dis=new DataInputStream(new BufferedInputStream(new FileInputStream(dirFile)));
					try {
						  while((aux=dis.readLine())!=null){
							  texto=texto+aux+"\n";
						  }
						  dis.close();
						} catch (IOException e) {
								// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 } catch (FileNotFoundException e) {
						e.printStackTrace();
				}
        return texto;
	}

    public static HashMap<String,String>cargarElememtos(String dirFile){
		HashMap<String ,String> datos=new HashMap<String ,String>();
		//lectura de archivo
        String clave="";
        String elemento="";
        int s=-1;
        
		int i=0;
				try {
				     dis=new DataInputStream(new BufferedInputStream(new FileInputStream(dirFile)));
					try {
						  while((aux=dis.readLine())!=null){
                              s=aux.indexOf('|');
							  clave=aux.substring(0,s);
                              elemento=aux.substring(s+1,aux.length());
                              datos.put(clave, elemento);
                              //System.out.println(clave+" "+elemento);
						  }
						} catch (IOException e) {
								// TODO Auto-generated catch block
							System.exit(0);
							e.printStackTrace();
						}
				 } catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
					 	try {
							dis.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					 	JOptionPane.showMessageDialog(null,"Herror no se puede cargar los albumes direcciones incorrectas, se limpiara la lista para evitar herrores");

				}
		return datos;
	}

    public static Vector<Vector<String>>cargarSimbolos(String dirFile){
		Vector<Vector<String>> datos=new Vector<Vector<String>>();
		//lectura de archivo
        String clave="";
        String elemento="";
        int s=-1;

		int i=0;
				try {
				     dis=new DataInputStream(new BufferedInputStream(new FileInputStream(dirFile)));
					try {
						  while((aux=dis.readLine())!=null){
                              s=aux.indexOf('|');
							  clave=aux.substring(0,s);
                              elemento=aux.substring(s+1,aux.length());
                              Vector<String> fila=new Vector<String>();
                              fila.add(clave);
                              fila.add(elemento);
                              datos.add(fila);
                              //System.out.println(clave+" "+elemento);
						  }
						} catch (IOException e) {
								// TODO Auto-generated catch block
							System.exit(0);
							e.printStackTrace();
						}
				 } catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
					 	try {
							dis.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					 	JOptionPane.showMessageDialog(null,"Herror no se puede cargar los albumes direcciones incorrectas, se limpiara la lista para evitar herrores");

				}
		return datos;
	}
}
