/*
 * EditorTPView.java
 */
package editortp;

import EditorComponentes.MarcadorDePalabras;
import EditorComponentes.MyUndoableEditListener;
import java.awt.Color;
import javax.swing.JTabbedPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import utilidades.EjecutadorDeComandos;
import utilidades.Impresora;
import utilidades.Lee_Escribe_Objetos;

/**
 * The application's main frame.
 */
public class EditorTPView extends FrameView {

    private Vector<Object> comandoCompilar = new Vector<Object>();
    private Vector<Object> comandoEjecutar = new Vector<Object>();
    private Lee_Escribe_Objetos lector;
    private UndoManager undo;
    private MyUndoableEditListener escuchaUndo;
    private DefaultStyledDocument documento;
    private Impresora impresora;

    public EditorTPView(SingleFrameApplication app) {
        super(app);
        lector = new Lee_Escribe_Objetos("config/estyloTxt.xml");
        ///---------------------------Iniciar txtSalida-----------------------------
        documento = new DefaultStyledDocument();
        ///----------------------------------------------------------------------------
        initComponents();
        //---------------Iniciar ahacer y deshacer--------------------------------------
        undo = new UndoManager();
        escuchaUndo = new MyUndoableEditListener(undo);

        //-------------------------Cargando configuraciones------------------------------------
        Object objeto = lector.leeObjeto();
        if (objeto != null) {
            MarcadorDePalabras.getConfigTxt().setTipoEstilos((Object[][]) objeto);
        }
        lector = new Lee_Escribe_Objetos("config/syntaxisConfig.xml");
        objeto = lector.leeObjeto();
        if (objeto != null) {
            Vector<String[]> sintaxis = (Vector<String[]>) objeto;
            MarcadorDePalabras.setPalabrasClave1(sintaxis.elementAt(0));
            MarcadorDePalabras.setPalabrasClave2(sintaxis.elementAt(1));
            MarcadorDePalabras.setLimitadores(sintaxis.elementAt(2));
            MarcadorDePalabras.setSimbolosPorDefecto(sintaxis.elementAt(3));
        }
        //------------------------------Iniciar Comandos--------------------------------------------
        inicializarComandoCompilar();
        inicializarComandoEjecutar();
        //-----------------------------------------------------------------------
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        //System.out.println("resourceMap "+resourceMap);
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = EditorTPApp.getApplication().getMainFrame();
            aboutBox = new EditorTPAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        EditorTPApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane(documento);
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        barraDeHerramientas = new javax.swing.JToolBar();
        btCopiar = new javax.swing.JButton();
        btCortar = new javax.swing.JButton();
        btPegar = new javax.swing.JButton();
        btDesAcer = new javax.swing.JButton();
        btAcer = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jtxtBuscar = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        miNuevo = new javax.swing.JMenuItem();
        miAbrir = new javax.swing.JMenuItem();
        miImprimir = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        mEdicion = new javax.swing.JMenu();
        miDesacer = new javax.swing.JMenuItem();
        miRehacer = new javax.swing.JMenuItem();
        miCopiar = new javax.swing.JMenuItem();
        miCortar = new javax.swing.JMenuItem();
        miPegar = new javax.swing.JMenuItem();
        mHerramientas = new javax.swing.JMenu();
        miCompilar = new javax.swing.JMenuItem();
        miEjecutar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        miEstiloCodigo = new javax.swing.JMenuItem();
        miPalabrasclave = new javax.swing.JMenuItem();
        miConfigComandos = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(200, 100));
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(500, 400));
        mainPanel.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(editortp.EditorTPApp.class).getContext().getResourceMap(EditorTPView.class);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), resourceMap.getString("jPanel1.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP)); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setMaximumSize(new java.awt.Dimension(1024, 300));
        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 150));

        jTextPane1.setBackground(resourceMap.getColor("jTextPane1.background")); // NOI18N
        jTextPane1.setForeground(resourceMap.getColor("jTextPane1.foreground")); // NOI18N
        jTextPane1.setMaximumSize(new java.awt.Dimension(1024, 400));
        jTextPane1.setName("jTextPane1"); // NOI18N
        jTextPane1.setPreferredSize(new java.awt.Dimension(6, 150));
        jScrollPane1.setViewportView(jTextPane1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        mainPanel.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel2.setName("jPanel2"); // NOI18N
        mainPanel.add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), resourceMap.getString("jPanel3.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(120, 169));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        mainPanel.add(jPanel3, java.awt.BorderLayout.LINE_START);

        barraDeHerramientas.setRollover(true);
        barraDeHerramientas.setName("barraDeHerramientas"); // NOI18N

        btCopiar.setIcon(resourceMap.getIcon("btCopiar.icon")); // NOI18N
        btCopiar.setText(resourceMap.getString("btCopiar.text")); // NOI18N
        btCopiar.setFocusable(false);
        btCopiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btCopiar.setName("btCopiar"); // NOI18N
        btCopiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCopiarActionPerformed(evt);
            }
        });
        barraDeHerramientas.add(btCopiar);

        btCortar.setIcon(resourceMap.getIcon("btCortar.icon")); // NOI18N
        btCortar.setText(resourceMap.getString("btCortar.text")); // NOI18N
        btCortar.setFocusable(false);
        btCortar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btCortar.setName("btCortar"); // NOI18N
        btCortar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btCortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCortarActionPerformed(evt);
            }
        });
        barraDeHerramientas.add(btCortar);

        btPegar.setIcon(resourceMap.getIcon("btPegar.icon")); // NOI18N
        btPegar.setText(resourceMap.getString("btPegar.text")); // NOI18N
        btPegar.setFocusable(false);
        btPegar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btPegar.setName("btPegar"); // NOI18N
        btPegar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPegarActionPerformed(evt);
            }
        });
        barraDeHerramientas.add(btPegar);

        btDesAcer.setIcon(resourceMap.getIcon("btDesAcer.icon")); // NOI18N
        btDesAcer.setText(resourceMap.getString("btDesAcer.text")); // NOI18N
        btDesAcer.setFocusable(false);
        btDesAcer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btDesAcer.setName("btDesAcer"); // NOI18N
        btDesAcer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btDesAcer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDesAcerActionPerformed(evt);
            }
        });
        barraDeHerramientas.add(btDesAcer);

        btAcer.setIcon(resourceMap.getIcon("btAcer.icon")); // NOI18N
        btAcer.setText(resourceMap.getString("btAcer.text")); // NOI18N
        btAcer.setFocusable(false);
        btAcer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btAcer.setName("btAcer"); // NOI18N
        btAcer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btAcer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAcerActionPerformed(evt);
            }
        });
        barraDeHerramientas.add(btAcer);

        jLabel1.setIcon(resourceMap.getIcon("jLabel1.icon")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setToolTipText(resourceMap.getString("jLabel1.toolTipText")); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraDeHerramientas.add(jLabel1);

        jtxtBuscar.setText(resourceMap.getString("jtxtBuscar.text")); // NOI18N
        jtxtBuscar.setName("jtxtBuscar"); // NOI18N
        jtxtBuscar.setPreferredSize(new java.awt.Dimension(200, 25));
        jtxtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtBuscarKeyReleased(evt);
            }
        });
        barraDeHerramientas.add(jtxtBuscar);

        mainPanel.add(barraDeHerramientas, java.awt.BorderLayout.NORTH);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setIcon(resourceMap.getIcon("fileMenu.icon")); // NOI18N
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        miNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        miNuevo.setIcon(resourceMap.getIcon("miNuevo.icon")); // NOI18N
        miNuevo.setText(resourceMap.getString("miNuevo.text")); // NOI18N
        miNuevo.setName("miNuevo"); // NOI18N
        miNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNuevoActionPerformed(evt);
            }
        });
        fileMenu.add(miNuevo);

        miAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        miAbrir.setIcon(resourceMap.getIcon("miAbrir.icon")); // NOI18N
        miAbrir.setText(resourceMap.getString("miAbrir.text")); // NOI18N
        miAbrir.setName("miAbrir"); // NOI18N
        miAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAbrirActionPerformed(evt);
            }
        });
        fileMenu.add(miAbrir);

        miImprimir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        miImprimir.setIcon(resourceMap.getIcon("miImprimir.icon")); // NOI18N
        miImprimir.setText(resourceMap.getString("miImprimir.text")); // NOI18N
        miImprimir.setName("miImprimir"); // NOI18N
        miImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miImprimirActionPerformed(evt);
            }
        });
        fileMenu.add(miImprimir);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(editortp.EditorTPApp.class).getContext().getActionMap(EditorTPView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setIcon(resourceMap.getIcon("exitMenuItem.icon")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        mEdicion.setIcon(resourceMap.getIcon("mEdicion.icon")); // NOI18N
        mEdicion.setText(resourceMap.getString("mEdicion.text")); // NOI18N
        mEdicion.setName("mEdicion"); // NOI18N

        miDesacer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        miDesacer.setIcon(resourceMap.getIcon("miDesacer.icon")); // NOI18N
        miDesacer.setText(resourceMap.getString("miDesacer.text")); // NOI18N
        miDesacer.setName("miDesacer"); // NOI18N
        miDesacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDesacerActionPerformed(evt);
            }
        });
        mEdicion.add(miDesacer);

        miRehacer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        miRehacer.setIcon(resourceMap.getIcon("miRehacer.icon")); // NOI18N
        miRehacer.setText(resourceMap.getString("miRehacer.text")); // NOI18N
        miRehacer.setName("miRehacer"); // NOI18N
        miRehacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRehacerActionPerformed(evt);
            }
        });
        mEdicion.add(miRehacer);

        miCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        miCopiar.setIcon(resourceMap.getIcon("miCopiar.icon")); // NOI18N
        miCopiar.setText(resourceMap.getString("miCopiar.text")); // NOI18N
        miCopiar.setName("miCopiar"); // NOI18N
        miCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCopiarActionPerformed(evt);
            }
        });
        mEdicion.add(miCopiar);

        miCortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        miCortar.setIcon(resourceMap.getIcon("miCortar.icon")); // NOI18N
        miCortar.setText(resourceMap.getString("miCortar.text")); // NOI18N
        miCortar.setName("miCortar"); // NOI18N
        miCortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCortarActionPerformed(evt);
            }
        });
        mEdicion.add(miCortar);

        miPegar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        miPegar.setIcon(resourceMap.getIcon("miPegar.icon")); // NOI18N
        miPegar.setText(resourceMap.getString("miPegar.text")); // NOI18N
        miPegar.setName("miPegar"); // NOI18N
        miPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPegarActionPerformed(evt);
            }
        });
        mEdicion.add(miPegar);

        menuBar.add(mEdicion);

        mHerramientas.setIcon(resourceMap.getIcon("mHerramientas.icon")); // NOI18N
        mHerramientas.setText(resourceMap.getString("mHerramientas.text")); // NOI18N
        mHerramientas.setName("mHerramientas"); // NOI18N

        miCompilar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        miCompilar.setIcon(resourceMap.getIcon("miCompilar.icon")); // NOI18N
        miCompilar.setText(resourceMap.getString("miCompilar.text")); // NOI18N
        miCompilar.setName("miCompilar"); // NOI18N
        miCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCompilarActionPerformed(evt);
            }
        });
        mHerramientas.add(miCompilar);

        miEjecutar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        miEjecutar.setIcon(resourceMap.getIcon("miEjecutar.icon")); // NOI18N
        miEjecutar.setText(resourceMap.getString("miEjecutar.text")); // NOI18N
        miEjecutar.setName("miEjecutar"); // NOI18N
        miEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEjecutarActionPerformed(evt);
            }
        });
        mHerramientas.add(miEjecutar);

        menuBar.add(mHerramientas);

        jMenu2.setIcon(resourceMap.getIcon("jMenu2.icon")); // NOI18N
        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N

        miEstiloCodigo.setIcon(resourceMap.getIcon("miEstiloCodigo.icon")); // NOI18N
        miEstiloCodigo.setText(resourceMap.getString("miEstiloCodigo.text")); // NOI18N
        miEstiloCodigo.setName("miEstiloCodigo"); // NOI18N
        miEstiloCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEstiloCodigoActionPerformed(evt);
            }
        });
        jMenu2.add(miEstiloCodigo);

        miPalabrasclave.setIcon(resourceMap.getIcon("miPalabrasclave.icon")); // NOI18N
        miPalabrasclave.setText(resourceMap.getString("miPalabrasclave.text")); // NOI18N
        miPalabrasclave.setName("miPalabrasclave"); // NOI18N
        miPalabrasclave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPalabrasclaveActionPerformed(evt);
            }
        });
        jMenu2.add(miPalabrasclave);

        miConfigComandos.setIcon(resourceMap.getIcon("miConfigComandos.icon")); // NOI18N
        miConfigComandos.setText(resourceMap.getString("miConfigComandos.text")); // NOI18N
        miConfigComandos.setName("miConfigComandos"); // NOI18N
        miConfigComandos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miConfigComandosActionPerformed(evt);
            }
        });
        jMenu2.add(miConfigComandos);

        menuBar.add(jMenu2);

        helpMenu.setIcon(resourceMap.getIcon("helpMenu.icon")); // NOI18N
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setIcon(resourceMap.getIcon("aboutMenuItem.icon")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N
        progressBar.setStringPainted(true);

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 716, Short.MAX_VALUE)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap(522, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void miNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNuevoActionPerformed
        // Abrir archivo  String direccion = "src/Archivos/" + nombreFile + ".tp";
        JFileChooser fcG = new JFileChooser();
        fcG.setSelectedFile(new File("Nuevo.txt"));
        int aprovado = fcG.showSaveDialog(jTabbedPane1);
        if (aprovado == JFileChooser.APPROVE_OPTION) {
            crearNuevoArchivo("" + fcG.getCurrentDirectory(), fcG.getSelectedFile().getName());
        }

}//GEN-LAST:event_miNuevoActionPerformed

    public JTabbedPane getJTabbedPane1() {
        return jTabbedPane1;
    }

    private void miCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCompilarActionPerformed
        // TODO add your handling code here:
        progressBar.setIndeterminate(true);
        jTextPane1.setText("");
        if (jTabbedPane1.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "No existen archivos para compilar");
        } else {
            //GUARDA DATOS
            guardarCambios();
            mensaje();
            // Compilar archivo:
            String dirFile = jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex()).getName();
            File d = new File(dirFile);
            String r = d.getAbsolutePath();
            jTextPane1.setText("URL archivo : " + r + "\n \t");
            String[] args = {r};
            //Para tomar las salidads del sistema
            compilar();

        }
    }//GEN-LAST:event_miCompilarActionPerformed

    private void miAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAbrirActionPerformed
        // TODO add your handling code here:
        SelectorArchivos bf = new SelectorArchivos(this);
        bf.setVisible(true);
    }//GEN-LAST:event_miAbrirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (jTabbedPane1.getSelectedIndex() > -1) {
            int r = JOptionPane.showConfirmDialog(jTabbedPane1, "Desea guardar los cambios:");
            if (r == 0) {
                guardarCambios();
            }
            if (r==1|| r==0) {
                jTabbedPane1.removeTabAt(jTabbedPane1.getSelectedIndex());
            }
        } else {
            JOptionPane.showMessageDialog(jTabbedPane1, "Necesita tener un documento abierto para poder cerrarlo");
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here://guardar los cambios
        if (jTabbedPane1.getSelectedIndex() > -1) {
            guardarCambios();
        } else {
            JOptionPane.showMessageDialog(jTabbedPane1, "Necesita tener un documento abierto para poder guardar los cambios");
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void miEstiloCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEstiloCodigoActionPerformed
        // TODO add your handling code here:
        new DialogoEstiloTxt(new JFrame(), true).setVisible(true);
        if (jTabbedPane1.getTabCount() > 0) {
            MarcadorDePalabras.marcarElTexto(getPanelCodigo(), escuchaUndo);
        }
    }//GEN-LAST:event_miEstiloCodigoActionPerformed

    private void miPalabrasclaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPalabrasclaveActionPerformed
        // TODO add your handling code here:
        new DialogoSintaxisLenguaje(new JFrame(), true).setVisible(true);
        if (jTabbedPane1.getTabCount() > 0) {
            MarcadorDePalabras.marcarElTexto(getPanelCodigo(), escuchaUndo);
        }
    }//GEN-LAST:event_miPalabrasclaveActionPerformed

    private void miConfigComandosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miConfigComandosActionPerformed
        // TODO add your handling code here:
        ConfiguraCmd config = new ConfiguraCmd(EditorTPView.this, true);
}//GEN-LAST:event_miConfigComandosActionPerformed

    private void miEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEjecutarActionPerformed
        // TODO add your handling code here:
        ejecutar();
    }//GEN-LAST:event_miEjecutarActionPerformed

    private void btCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCopiarActionPerformed
        // TODO add your handling code here:
        if (jTabbedPane1.getTabCount() > 0) {
            getPanelCodigo().copy();
        }
    }//GEN-LAST:event_btCopiarActionPerformed

    private void btCortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCortarActionPerformed
        // TODO add your handling code here:
        if (jTabbedPane1.getTabCount() > 0) {
            getPanelCodigo().cut();
        }
    }//GEN-LAST:event_btCortarActionPerformed

    private void btPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPegarActionPerformed
        // TODO add your handling code here:
        if (jTabbedPane1.getTabCount() > 0) {
            getPanelCodigo().paste();
        }
    }//GEN-LAST:event_btPegarActionPerformed

    private void jtxtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBuscarKeyReleased
        // TODO add your handling code here:
        if (jTabbedPane1.getTabCount() > 0) {
            String textoBuscar = jtxtBuscar.getText();
            if (textoBuscar != null) {
                if (!textoBuscar.trim().equals("")) {
//                    if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                        buscarTexto(textoBuscar, getPanelCodigo(), escuchaUndo);                   
//                    }
                }
            }
        }
}//GEN-LAST:event_jtxtBuscarKeyReleased

    private void btDesAcerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDesAcerActionPerformed
        // TODO add your handling code here:
        if (undo.canUndo()) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                System.out.println("Unable to undo: " + ex);
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_btDesAcerActionPerformed

    private void btAcerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAcerActionPerformed
        // TODO add your handling code here:
        if (undo.canRedo()) {
            try {
                undo.redo();
            } catch (CannotUndoException ex) {
                System.out.println("Unable to redo: " + ex);
            }
        }

    }//GEN-LAST:event_btAcerActionPerformed

    private void miDesacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDesacerActionPerformed
        // TODO add your handling code here:
        btDesAcerActionPerformed(evt);
    }//GEN-LAST:event_miDesacerActionPerformed

    private void miRehacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRehacerActionPerformed
        // TODO add your handling code here:
        btAcerActionPerformed(evt);
    }//GEN-LAST:event_miRehacerActionPerformed

    private void miCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCopiarActionPerformed
        // TODO add your handling code here:
        btCopiarActionPerformed(evt);
    }//GEN-LAST:event_miCopiarActionPerformed

    private void miCortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCortarActionPerformed
        // TODO add your handling code here:
        btCortarActionPerformed(evt);
    }//GEN-LAST:event_miCortarActionPerformed

    private void miPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPegarActionPerformed
        // TODO add your handling code here:
        btPegarActionPerformed(evt);
    }//GEN-LAST:event_miPegarActionPerformed

    private void miImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miImprimirActionPerformed
        // TODO add your handling code here:
        imprimeDocumento();
    }//GEN-LAST:event_miImprimirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraDeHerramientas;
    private javax.swing.JButton btAcer;
    private javax.swing.JButton btCopiar;
    private javax.swing.JButton btCortar;
    private javax.swing.JButton btDesAcer;
    private javax.swing.JButton btPegar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextField jtxtBuscar;
    private javax.swing.JMenu mEdicion;
    private javax.swing.JMenu mHerramientas;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem miAbrir;
    private javax.swing.JMenuItem miCompilar;
    private javax.swing.JMenuItem miConfigComandos;
    private javax.swing.JMenuItem miCopiar;
    private javax.swing.JMenuItem miCortar;
    private javax.swing.JMenuItem miDesacer;
    private javax.swing.JMenuItem miEjecutar;
    private javax.swing.JMenuItem miEstiloCodigo;
    private javax.swing.JMenuItem miImprimir;
    private javax.swing.JMenuItem miNuevo;
    private javax.swing.JMenuItem miPalabrasclave;
    private javax.swing.JMenuItem miPegar;
    private javax.swing.JMenuItem miRehacer;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;

///----------------------------**********--------------------------------------
    public void buscarTexto(String palabraBuscada, JTextPane areaDeTexto, MyUndoableEditListener escuchaDesacer) {
        int firstOffset = -1;
        String content = areaDeTexto.getText();
        int lastIndex = 0;
        int wordSize = palabraBuscada.length();
        MarcadorDePalabras.desmarcarPalabrasEncontrada(areaDeTexto, escuchaDesacer);
        while ((lastIndex = content.indexOf(palabraBuscada, lastIndex)) != -1) {
            int endIndex = lastIndex + wordSize;
            try {
                MarcadorDePalabras.marcarPalabraEncontrada(areaDeTexto, palabraBuscada, lastIndex, escuchaDesacer);
            } catch (Exception e) {
                // no hacer nada
            }
            if (firstOffset == -1) {
                firstOffset = lastIndex;
            }
            lastIndex = endIndex;
        }
    }
///---------------------------********************-----------------------------

    public void setNumeracionFila(JPanel panel, JTextPane areaTexto) {
        for (int i = 1; i <= areaTexto.getHeight(); i++) {
            JLabel et = new JLabel("" + i);
            et.setFont(areaTexto.getFont());
            panel.add(et);
        }
    }
    //--------------------IniciarComandos------------------------------------

    public void inicializarComandoCompilar() {
        comandoCompilar.removeAllElements();
        Lee_Escribe_Objetos loc = new Lee_Escribe_Objetos("config/listaCompiladores.xml");
        Vector<Vector> listaCompiladores = (Vector<Vector>) loc.leeObjeto();
        for (int i = 0; i < listaCompiladores.size(); i++) {
            Vector<Object> fila = listaCompiladores.get(i);
            if ((Boolean) fila.get(0)) {
                comandoCompilar.add(fila.get(0));
                comandoCompilar.add(fila.get(1));
                comandoCompilar.add(fila.get(2));
                comandoCompilar.add(fila.get(3));
                //System.out.println( comandoCompilar.get(1)+" "+ comandoCompilar.get(2)+" "+ comandoCompilar.get(3));
                break;
            }
        }
    }

    public void inicializarComandoEjecutar() {
        comandoEjecutar.removeAllElements();
        Lee_Escribe_Objetos loe = new Lee_Escribe_Objetos("config/listaEjecutores.xml");
        Vector<Vector> listaEjecutores = (Vector<Vector>) loe.leeObjeto();

        for (int i = 0; i < listaEjecutores.size(); i++) {
            Vector<Object> fila = listaEjecutores.get(i);
            if ((Boolean) fila.get(0)) {
                comandoEjecutar.add(fila.get(0));
                comandoEjecutar.add(fila.get(1));
                comandoEjecutar.add(fila.get(2));
                comandoEjecutar.add(fila.get(3));
                //System.out.println(  comandoEjecutar.get(1)+" "+  comandoEjecutar.get(2)+" "+  comandoEjecutar.get(3));
                break;
            }
        }

    }
//------------------Compilar Archivo-----------------------------------

    public void compilar() {
        // Compilar archivo:
        String dirFile = jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex()).getName();
        File file2 = new File(dirFile);
        String comando;
        if (comandoCompilar != null) {
            if (comandoCompilar.get(3).toString().equalsIgnoreCase("Nombre")) {
                String classpath = file2.getAbsolutePath().substring(0, file2.getAbsolutePath().lastIndexOf(File.separator) + 1);
                String classname = file2.getName().substring(0, file2.getName().lastIndexOf("."));
                comando = "\"" + classpath + "\"" + " " + classname;
            } else {
                comando = "\"" + file2.getAbsolutePath() + "\"";
            }
            comando = comandoCompilar.get(1) + " " + comandoCompilar.get(2) + "  " + comando;
            if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
                comando = comando.replace(File.separatorChar, '/');
            }

            System.out.println(comando);
            EjecutadorDeComandos cmEjecutar = new EjecutadorDeComandos(comando);
            cmEjecutar.setDaemon(true);
            cmEjecutar.setAreaDeTextoSalida(jTextPane1);
            cmEjecutar.setAreaDeTextoCodigo(getPanelCodigo());
            cmEjecutar.start(); 
        }
    }
    //------------------ejecutar Archivo-----------------------------------

    public void ejecutar() {
        if (jTabbedPane1.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "No existen archivos para ejecutar");
        } else {
            //txt = BuscarFile.jt.getText();
            //ejecutar programa
            String dirFile = jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex()).getName();
            File file = new File(dirFile);
            String comando;
            if (comandoEjecutar != null) {
                if (comandoEjecutar.get(3).toString().equalsIgnoreCase("Nombre")) {
                    String classpath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator) + 1);
                    String classname = file.getName().substring(0, file.getName().lastIndexOf("."));
                    comando = "\"" + classpath + "\"" + " " + classname;
                } else {
                    comando = "\"" + file.getAbsolutePath() + "\"";
                }
                comando = comandoEjecutar.get(1) + " " + comandoEjecutar.get(2) + "  " + comando;
                if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
                    comando = comando.replace(File.separatorChar, '/');
                } else {
                    comando = comando.replaceAll("\"", "");
                }
                System.out.println(comando);
                EjecutadorDeComandos cmEjecutar = new EjecutadorDeComandos(comando);
                cmEjecutar.setDaemon(true);
                cmEjecutar.setAreaDeTextoSalida(jTextPane1);
                cmEjecutar.setAreaDeTextoCodigo(getPanelCodigo());
                cmEjecutar.start();

            }
        }
    }

    public JTextPane getPanelCodigo() {
        JPanel panel = (JPanel) jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex());
        // jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex())
        JScrollPane spane = (JScrollPane) panel.getComponent(0);
        JViewport jv = (JViewport) spane.getComponent(0);
        JTextPane jtpane = (JTextPane) jv.getComponent(0);
        return jtpane;
    }

    public void mensaje() {
        jTextPane1.setText("");

        jTextPane1.setText("\t \t \t TP compiler 1.0 " +
                "\n\nINICIANDO:......" +
                "\nGuardado: " + jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()) +
                "\nCompilando-" + jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()) +
                "\nEjecutando: " + jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()) + "\n");
    }

    public void guardarCambios() {
        if (jTabbedPane1.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "No existen archivos abiertos para guardar");
        } else {
            //txt = BuscarFile.jt.getText();

            FileOutputStream file;

            try {
                //System.out.println(jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex()).getName());
                file = new FileOutputStream(jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex()).getName());

                DataOutputStream da = new DataOutputStream(file);

                try {
                    JTextPane jtpane = getPanelCodigo();
                    da.writeBytes(jtpane.getText());


                } catch (IOException ex) {
                    Logger.getLogger(EditorTPView.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(EditorTPView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //--------------Crear un nuevo archivo--------------------------------------------

    public void crearNuevoArchivo(String direccion, String nombre) {
        FileOutputStream f;
        direccion = direccion + File.separator + nombre;
        try {
            f = new FileOutputStream(new File(direccion));
            DataOutputStream da = new DataOutputStream(f);
            try {

                da.writeBytes("inicio[\n\n]fin");
                JPanel jp = new JPanel();
                final DefaultStyledDocument documento = new DefaultStyledDocument();
                final JTextPane jt = new JTextPane(documento);
                jt.setBackground(Color.WHITE);
                JScrollPane sp = new JScrollPane();
                //Crear la regla vertical--------------------
                final JPanel reglaVertical = new JPanel();
                reglaVertical.setBackground(Color.GREEN);
                reglaVertical.setLayout(new BoxLayout(reglaVertical, BoxLayout.Y_AXIS));
                sp.setRowHeaderView(reglaVertical);
                jt.addKeyListener(new KeyAdapter() {

                    public void keyReleased(KeyEvent e) {
//-------------------------***---------------------------------------------
                        if (e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == 8) {
                            reglaVertical.removeAll();
                            for (int i = 1; i <= jt.getHeight() / jt.getFont().getSize(); i++) {// areaTexto.getHeight()
                                JLabel et = new JLabel("" + i);
                                et.setFont(jt.getFont());
                                reglaVertical.add(et);
                            }
                            reglaVertical.revalidate();
                        }
                    }

                    @Override
                    public void keyTyped(KeyEvent e) {
                        MarcadorDePalabras.marcarElTexto(jt, escuchaUndo);
                    }
                });
                //-----------------------------------------------
                jTabbedPane1.addTab("" + nombre, new ImageIcon(getClass().getResource("/editortp/resources/busyicons/glipper.png")), jp);//se ubica el nombre de la pestaña

                jp.setName(direccion);
                jp.setLayout(new java.awt.CardLayout());
                jp.add(sp, java.awt.BorderLayout.CENTER);
                sp.setViewportView(jt);
                try {
                    String fx = SelectorArchivos.leeArchivo(direccion);//
                    jt.setText(fx);//fija el texto del archivo en el TextPane

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SelectorArchivos.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {
                Logger.getLogger(EditorTPView.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
        }
    }
///------------------***--------------------------***------------------------------

    public MyUndoableEditListener getEscuchaUndo() {
        return escuchaUndo;
    }

    public UndoManager getUndo() {
        return undo;
    }
///------------------***--------------------------***------------------------------
    public void imprimeDocumento() {
        JTextPane texAUX=getPanelCodigo();
        int pos=texAUX.getCaretPosition();
        try {
            String cadena = "";
            cadena = String.valueOf(texAUX.getText());
            if (!cadena.equals("")) {
                impresora = new Impresora();
                impresora.imprimir(cadena);
            } else {
                JOptionPane.showMessageDialog(jMenu2,"Documento en blanco..");
            }
            texAUX.requestFocus();
            texAUX.select(0, cadena.length());

        } catch (Exception e) {
            System.out.print("ERROR " + e);
            JOptionPane.showMessageDialog(null, " No hay archivos para imprimir");
        }
        if (pos>-1) {
         texAUX.setCaretPosition(pos);
        }else{
            texAUX.setCaretPosition(0);
        }
        
    }
}
