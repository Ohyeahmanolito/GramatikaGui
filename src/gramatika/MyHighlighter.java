/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gramatika;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 *
 * @author Ichi
 */
public class MyHighlighter {

    private final Highlighter.HighlightPainter redPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.red);
    static LinkedHashMap<Integer, String> wordCharPosition = null;

    /**
     * This method highlights the detected erroneous words of the spell checking
     * system.
     *
     * @param suggestions - the returned list of errorWord_candidateSuggestion
     * of the spell checking system.
     * @param highlighter - the highlighter color that will be used.
     *
     */
    public void highligh(String[] sentences, LinkedHashMap<String, LinkedHashSet<String>> suggestions, Highlighter highlighter) throws BadLocationException {
        LinkedHashSet<String> x = getCharacterPosition(sentences, suggestions);

        for (String temp : x) {
            String[] splitter = temp.split("_");
            highlighter.addHighlight(Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1]), redPainter);
        }

    }

    /**
     * This method get the exact start and end character positions of the
     * erroneous words detected by the spell checking system.
     *
     * @param sentences - Tokenized sentences of the document.
     * @param suggestions - List of detected erroneous words and suggestions
     * detected by the spell checking system.
     * @return - The start and end character positions of erroneous word in the
     * text
     */
    private LinkedHashSet<String> getCharacterPosition(String[] sentences, LinkedHashMap<String, LinkedHashSet<String>> suggestions) {

        int wordCounter = 0;
        int characterCount = 0;
        int extraStart = 0;

        LinkedHashSet<Integer> suggestionKey = new LinkedHashSet<>();
        wordCharPosition = new LinkedHashMap<>();

        // spell checker output: WordCount_ErrorWord; This part only need to store the WordCount
        for (String temp : suggestions.keySet()) {
            String wordCount = temp.split("_")[0];
            String errorWord = temp.split("_")[1];
            System.out.println("spelling erorr: " + wordCount + " " + errorWord);
            suggestionKey.add(Integer.parseInt(wordCount));

            // Only gets the wordCount and update its value once the character positions are identified.
            wordCharPosition.put(Integer.parseInt(wordCount), "");
        }

        LinkedHashSet<String> highlighPos = new LinkedHashSet<>();
        for (String sentence : sentences) {
            for (String word : sentence.trim().split(" ")) {

                wordCounter++;
                // Use the WordCount to detect the specific location of the word in the document and get its character position.
                if (suggestionKey.contains(wordCounter)) {
                    int start = (characterCount + wordCounter) - 1 + extraStart;
                    int end = start + word.length();

                    highlighPos.add(start + "_" + end);

                    // update the positions of the word.
                    wordCharPosition.replace(wordCounter, start + "_" + end);
                }
                characterCount += word.length();
            }
            extraStart++;

        }
        return highlighPos;
    }

    /**
     * This method is used by the ListenerMouse to give the suggestion of the
     * selected text in the document.
     *
     * @return the word with its start and end character position in the
     * document.
     */
    public static LinkedHashMap<Integer, String> getWordCharPosition() {
        return wordCharPosition;
    }
}
