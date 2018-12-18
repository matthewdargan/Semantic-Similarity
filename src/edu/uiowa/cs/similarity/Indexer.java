package edu.uiowa.cs.similarity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import javafx.util.Pair;
import opennlp.tools.stemmer.*;

public class Indexer {
    
    List<List<String>> list;
    Vector vector;
    List<Pair<String, Double>> topJ;
    
    public Indexer(String filename) throws FileNotFoundException {
        list = indexFile(filename);
        vector = new Vector(list);
    }
    
    public Indexer(String filename, Indexer temp) throws FileNotFoundException {
        list = indexFile(filename);
        vector = new Vector(list, temp.vector.mapping);
        List<List<String>> newSentences = new LinkedList<>(list);
        vector.newSentences = newSentences;
        addSentences(temp.list);
        vector.updateVector(list);
    }
    
    private void addSentences(List<List<String>> temp) {
        List<List<String>> newList = this.list;
        
        for (List<String> el : temp) {
            newList.add(el);
        }
    }
    
    private static List<List<String>> indexFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner input = new Scanner(file).useDelimiter("[.?!]");
        List<List<String>> words = new LinkedList<>();

        while (input.hasNext()) {
            String sentence = input.next().toLowerCase().replaceAll("\\,|\\.|\\--|\\!|\\?|\\'|\\:|\\;", "");
            String subsentence = sentence.replaceAll("\"","");
            Scanner wordList = new Scanner(subsentence);
            List<String> subsentence2 = new LinkedList<>();

            while (wordList.hasNext()) {
                String cleanedWord = wordList.next();
                if (!cleanedWord.equals("")) {
                    subsentence2.add(cleanedWord);
                }
            }
            if (!subsentence2.isEmpty()) {
                words.add(subsentence2);
            }
        }
        input.close();
        
        List<List<String>> cleanup = toRootWords(removeStopWords(words));
        return cleanup;
    }
    
    private static List<List<String>> toRootWords(List<List<String>> words) {
        PorterStemmer stemmer = new PorterStemmer();
        List<List<String>> result = new LinkedList<>();
        
        for (List<String> subsentence : words) {
            List<String> dummy = new LinkedList<>();
            
            for (String word : subsentence) {
                String rootWord = stemmer.stem(word);
                dummy.add(rootWord);
            }
            result.add(dummy);
        }
        
        return result;
    }
    
    private static List<List<String>> removeStopWords(List<List<String>> words) throws FileNotFoundException {
        File file = new File("stopwords.txt");
        Scanner input = new Scanner(file);
        Scanner input2 = new Scanner(file);
        Set<String> stopwords = new HashSet<>();
        Set<String> stopwordsEdited = new HashSet<>();
        
        /* these two stopwords hashsets catches words such as dont and don't
           depending on how the indexFile method edits the input
        */
        while (input.hasNext()) {
            String word = input.nextLine();
            stopwords.add(word);
        }
        input.close();
        
        while (input2.hasNext()) {
            String word = input2.nextLine().replaceAll("\\,|\\.|\\--|\\!|\\?|\\'", "");
            stopwordsEdited.add(word);
        }
        input2.close();
        
        for (List<String> subList : words) {
            subList.removeAll(stopwords);
            subList.removeAll(stopwordsEdited);
        }
        
        return words;
    }
    
    public List<Pair<String, Double>> topj(String q, int j, Similarity calculation) {
        PorterStemmer stemmer = new PorterStemmer();
        q = stemmer.stem(q);
        List<Pair<String, Double>> total = new LinkedList<>();
        List<Pair<String, Double>> jPairs = new LinkedList<>();
        
        for (String key : this.vector.mapping.keySet()) {
            if (!key.equals(q)) {
                double sim = calculation.apply(this.vector.mapping.get(q), this.vector.mapping.get(key));
                Pair<String, Double> pair = new Pair<>(key, sim);
                total.add(pair);
            }
        }
        
        boolean ifNegative = false;
        
        for (Pair<String, Double> pair : total) {
            if (pair.getValue() < 0.0) {
                ifNegative = true;
            }
        }
        
        if (ifNegative) {
            Collections.sort(total, Comparator.comparing(p -> p.getValue()));
        } else {
            Collections.sort(total, Comparator.comparing(p -> -p.getValue()));
        }
        
        for (int i = 0; i < j; i++) {
            jPairs.add(total.get(i));
        }
        return jPairs;
    }
}
