/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConfiguraCmd.java
 *
 * Created on 06/02/2009, 12:16:44 AM
 */

package editortp;

import utilidades.Lee_Escribe_Objetos;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author root
 */
public class ConfiguraCmd extends javax.swing.JDialog {

    /** Creates new form ConfiguraCmd */
    private EditorTPView principal;
    public ConfiguraCmd(EditorTPView parent, boolean modal) {
        super(new JFrame(), modal);
        principal=parent;
        initComponents();

         Lee_Escribe_Objetos eys=new Lee_Escribe_Objetos("config/listaCompiladores.xml");
         Vector<Vector> listaCompiladores=(Vector<Vector>)eys.leeObjeto();
         if(listaCompiladores!=null)
            tablaDDatos1.setFilas(listaCompiladores);

          Lee_Escribe_Objetos eys2=new Lee_Escribe_Objetos("config/listaEjecutores.xml");
         Vector<Vector> listaEjecutores=(Vector<Vector>)eys2.leeObjeto();
         if(listaEjecutores!=null)
            tablaDDatos2.setFilas(listaEjecutores);

        setSize(750, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        tablaDDatos1 = new editortp.TablaDDatos();
        tablaDDatos2 = new editortp.TablaDDatos();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Comandos para compilar");

        jLabel2.setText("Comandos para ejecutar");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editortp/resources/busyicons/document-save-all.png"))); // NOI18N
        jButton1.setText("Guardar ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editortp/resources/busyicons/opinion-no.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editortp/resources/busyicons/list-add.png"))); // NOI18N
        jButton3.setText("Agregar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editortp/resources/busyicons/list-remove_1.png"))); // NOI18N
        jButton4.setText("Quitar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editortp/resources/busyicons/list-add.png"))); // NOI18N
        jButton5.setText("Agregar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editortp/resources/busyicons/list-remove_1.png"))); // NOI18N
        jButton6.setText("Quitar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(170, 170, 170))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(208, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tablaDDatos2, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                                    .addComponent(tablaDDatos1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4))
                    .addComponent(tablaDDatos1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(26, 26, 26)
                        .addComponent(jButton6))
                    .addComponent(tablaDDatos2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // quitar comando compilador
          if(tablaDDatos1.getFilas().size()>0){
                try {
                    int nf= tablaDDatos1.getJTable1().getSelectedRow();
                    tablaDDatos1.removerFila(nf);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(ConfiguraCmd.this, "No hay niguna fila seleccionada por favor seleccione una");
            }

            }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // quitar comando ejecutar
         if(tablaDDatos2.getFilas().size()>0){
                try {
                    int nf= tablaDDatos2.getJTable1().getSelectedRow();
                    tablaDDatos2.removerFila(nf);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(ConfiguraCmd.this, "No hay niguna fila seleccionada por favor seleccione una");
            }

            }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Agregar comando compilador


        Vector<Object> fila=new Vector<Object>();
        fila.add(false);
        fila.add("");
        fila.add("");
        fila.add("");

        tablaDDatos1.addNuevaFila(fila);
        tablaDDatos1.getJTable1().revalidate();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // Agregar comando ejecutar
        Vector<Object> fila=new Vector<Object>();
        fila.add(false);
        fila.add("");
        fila.add("");
        fila.add("");

        tablaDDatos2.addNuevaFila(fila);
        tablaDDatos2.getJTable1().revalidate();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        Lee_Escribe_Objetos eys=new Lee_Escribe_Objetos("config/listaCompiladores.xml");
        eys.escribeOjeto(tablaDDatos1.getFilas());

         Lee_Escribe_Objetos eys2=new Lee_Escribe_Objetos("config/listaEjecutores.xml");
        eys2.escribeOjeto(tablaDDatos2.getFilas());

        principal.inicializarComandoCompilar();
        principal.inicializarComandoEjecutar();
        dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private editortp.TablaDDatos tablaDDatos1;
    private editortp.TablaDDatos tablaDDatos2;
    // End of variables declaration//GEN-END:variables
        
}
