/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorComponentes;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public  class MyUndoableEditListener implements UndoableEditListener {
    private UndoManager undo;

    public MyUndoableEditListener(UndoManager undo) {
       this.undo = undo;
    }
    public void undoableEditHappened(UndoableEditEvent e) {
        //Remember the edit and update the menus.
      undo.addEdit(e.getEdit());
    }
}
