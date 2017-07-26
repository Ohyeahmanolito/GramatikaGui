/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gramatika;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 *
 * @author Ichi
 */
public class ListenerMouse implements MouseListener {

    JTextPane jTextPane1 = null;
    JPopupMenu jPopupMenu1 = null;
    JScrollPane jScrollPane1 = null;
    //MyHighlighter myHighlighter = new MyHighlighter();

    ListenerMouse(JTextPane jTextPane1, JScrollPane jScrollPane1, JPopupMenu jPopupMenu1) {
        this.jTextPane1 = jTextPane1;
        this.jPopupMenu1 = jPopupMenu1;
        this.jScrollPane1 = jScrollPane1;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (jTextPane1.getSelectedText() != null && jTextPane1.getSelectedText().length() > 2) {
            String selectionPosition = jTextPane1.getSelectionStart() + "_" + jTextPane1.getSelectionEnd();

            LinkedHashMap<String, Integer> wordCharPosition = MyHighlighter.getWordCharPosition();
            LinkedHashMap<String, LinkedHashSet<String>> suggestions = MyHighlighter.getSuggestions();

            if (wordCharPosition.containsKey(selectionPosition)) {
                String error = wordCharPosition.get(selectionPosition) + "_" + jTextPane1.getSelectedText();

                if (suggestions.containsKey(error)) {
                    LinkedHashSet<String> listOfSuggestions = suggestions.get(error);

                    jPopupMenu1.removeAll();
                    JMenuItem item = null;
                    int suggestionCount = 0;
                    if (listOfSuggestions.isEmpty()) {
                        item = new JCheckBoxMenuItem("Detected as erroneous");
                        item.setActionCommand(selectionPosition + "_" + "Detected_as_erroneous");
                        //item.setActionCommand("Detected_as_erroneous");
                        //item.addActionListener(new ListenerAction(jTextPane1));
                        jPopupMenu1.add(item);
                    } else {
                        for (String eachSuggestion : listOfSuggestions) {
                            if (suggestionCount != 5) {
                                item = new JCheckBoxMenuItem(eachSuggestion);
                                item.setActionCommand(selectionPosition + "_" + eachSuggestion);
                                //item.setActionCommand(eachSuggestion);
                                item.addActionListener(new ListenerAction(jTextPane1));
                                jPopupMenu1.add(item);
                                suggestionCount++;
                            }
                        }
                    }
                    Point p = jTextPane1.getParent().getMousePosition();
                    int verticalScrollPane = jScrollPane1.getVerticalScrollBar().getValue();
                    int y_axis = p.y + verticalScrollPane;

                    jPopupMenu1.show(jTextPane1, p.x, y_axis);
                }
            }
        }

        jTextPane1.setSelectionEnd(0);
    }

    @Override
    public void mouseClicked(MouseEvent e
    ) {
    }

    @Override
    public void mousePressed(MouseEvent e
    ) {
    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {
    }

    @Override
    public void mouseExited(MouseEvent e
    ) {
    }

}
