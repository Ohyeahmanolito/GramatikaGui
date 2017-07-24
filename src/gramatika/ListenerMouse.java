/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gramatika;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;
import javax.swing.JTextPane;

/**
 *
 * @author Ichi
 */
public class ListenerMouse implements MouseListener {

    JTextPane jTextPane1 = null;
    //MyHighlighter myHighlighter = new MyHighlighter();

    ListenerMouse(JTextPane jTextPane1) {
        this.jTextPane1 = jTextPane1;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (jTextPane1.getSelectedText() != null && jTextPane1.getSelectedText().length() > 2) {
            System.out.println("selected text: " + jTextPane1.getSelectedText() + " " + jTextPane1.getSelectionStart()
                    + " " + jTextPane1.getSelectionEnd());
            LinkedHashMap<Integer, String> WordCharPosition = MyHighlighter.getWordCharPosition();

            // For debugging: to see the exact positions of all erroneous words. 
            //for (int x : WordCharPosition.keySet()) {
            //    System.out.println(x + " -> " + WordCharPosition.get(x));
            //}
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
