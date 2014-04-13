/*
 * EditorTPApp.java
 */
package editortp;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class EditorTPApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        EditorTPView principal = new EditorTPView(this);
        new Inicio(principal, true).setVisible(true);
        show(principal);

    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of EditorTPApp
     */
    public static EditorTPApp getApplication() {
        return Application.getInstance(EditorTPApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(EditorTPApp.class, args);
    }
}
