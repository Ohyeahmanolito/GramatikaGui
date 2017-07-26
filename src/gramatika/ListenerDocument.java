/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gramatika;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Highlighter;
import spellCheck.SpellChecker;

/**
 *
 * @author Ichi
 */
public class ListenerDocument
        implements javax.swing.event.DocumentListener {

    int interval = 10000;
    Highlighter highlighter;
    SwingWorker<Void, String> worker;
    MyHighlighter highligh = new MyHighlighter();

    private DocumentEvent documentEvent = null;

    ListenerDocument(Highlighter highlighter) {
        this.highlighter = highlighter;

        worker = new SwingWorker<Void, String>() {

            @Override
            protected Void doInBackground() throws Exception {

                while (true) {
                    // Initialized the documentEvent variable.
                    if (documentEvent != null) {
                        String document = documentEvent.getDocument().getText(0, documentEvent.getDocument().getLength()).replaceAll("\\n", "");

                        //pass the tokenized sentences
                        String[] sentences = document.split("[.?!]");

                        spellCheck(sentences);
                        // Put grammar checker here
                    }
                }

            }
        };

        worker.execute();
    }

    // This variable is used as temporary storage. Used to compare new generated suggestion for highlighting.
    LinkedHashMap<String, LinkedHashSet<String>> oldSuggestion = new LinkedHashMap<>();

    private void spellCheck(String[] sentences) throws Exception {

        LinkedHashMap<String, LinkedHashSet<String>> currentSuggestion = new LinkedHashMap<>();
        spellCheck.SpellChecker checkSpelling = SpellChecker.getInstance();

        for (String eachSentece : sentences) {
            // always add "." for every end of sentence to ensure that the spell checker run.
            currentSuggestion.putAll(checkSpelling.checkSentence(eachSentece.trim() + "."));
        }

        if (!oldSuggestion.equals(currentSuggestion)) {
            System.out.println("new suggestion");
            oldSuggestion = new LinkedHashMap<>(currentSuggestion);

            // Highlighting the detected errors in the sentences.
            highligh.highligh(sentences, oldSuggestion, highlighter);
        } else {
            // Do nothing if the suggestions is the same.
            System.out.println("same suggestion");
        }

        Thread.sleep(interval);
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        if (documentEvent == null) {
            documentEvent = e;
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

}
