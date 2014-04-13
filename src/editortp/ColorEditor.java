/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editortp;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

class ColorEditor extends DefaultCellEditor {

    Color currentColor = null;

    public ColorEditor(JButton b) {
        super(new JCheckBox()); //Unfortunately, the constructor
        //expects a check box, combo box,
        //or text field.
        editorComponent = b;
        setClickCountToStart(1); //This is usually 1 or 2.

        //Must do this so that editing stops when appropriate.
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    public Object getCellEditorValue() {
        return currentColor;
    }

    public Component getTableCellEditorComponent(JTable table,
            Object value,
            boolean isSelected,
            int row,
            int column) {
        ((JButton) editorComponent).setText(value.toString());
        currentColor = (Color) value;
        return editorComponent;
    }
}
