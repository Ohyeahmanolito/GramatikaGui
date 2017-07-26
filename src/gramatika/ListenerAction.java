/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gramatika;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Ichi
 */
public class ListenerAction implements ActionListener {

    JTextPane jTextPane1 = null;

    ListenerAction(JTextPane jTextPane1) {
        this.jTextPane1 = jTextPane1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem jmi = (JMenuItem) e.getSource();
        String[] selection = jmi.getActionCommand().split("_");
        int start = Integer.parseInt(selection[0]);
        int end = Integer.parseInt(selection[1]);
        String suggestion = selection[2];
        int suggestionLength = suggestion.length();

        try {
            jTextPane1.replaceSelection(suggestion);
            jTextPane1.getDocument().remove(start + suggestionLength, (end - start));
        } catch (BadLocationException ex) {
            Logger.getLogger(ListenerAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("selecction again: " + start + " " + end);
    }
}
