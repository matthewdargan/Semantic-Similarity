package edu.uiowa.cs.similarity;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Vector {
    HashMap<String, HashMap<String, Double>> mapping;
    List<List<String>> newSentences;
    
    public Vector(List<List<String>> list) {
        mapping = createVector(list);
    }
    
    public Vector(List<List<String>> list,  HashMap<String, HashMap<String, Double>> oldMap){
        mapping = oldMap;
        newSentences = list;
    }
    
    private static HashMap<String, HashMap<String, Double>> createVector(List<List<String>> allSentences) {
        HashMap<String, HashMap<String, Double>> map = new HashMap<>();
        List<String> allWords = new LinkedList<>();
        
        for (List<String> sentence : allSentences) {
            for (String sentenceWord : sentence) {
                allWords.add(sentenceWord);
            }
        }
        
        for (List<String> sentence : allSentences) {
            
            for (String sentenceWord : sentence) {
                HashMap<String, Double> newVector = new HashMap<>();
                
                for (String word : allWords) {
                    if (!sentenceWord.equals(word)) {
                        if (sentence.contains(word)) {
                            newVector.put(word, 1.0);
                        }
                    }
                }
                if (map.containsKey(sentenceWord)) {
                    HashMap<String, Double> combinedVector = new HashMap<>();
                    combinedVector.putAll(newVector);
                    combinedVector.putAll(map.get(sentenceWord));
                    map.put(sentenceWord, combinedVector);
                } else {
                    map.put(sentenceWord, newVector);
                }
            }
        }
        
        return map;
    }
    
    public HashMap<String, HashMap<String, Double>> updateVector(List<List<String>> allSentences) {
        HashMap<String, HashMap<String, Double>> map = mapping;
        List<String> allWords = new LinkedList<>();
        
        // Remove duplicate inner lists in the outer list
        allSentences = allSentences.stream().distinct().collect(Collectors.toList());

        /*Gets a list of all the unique words after adding new sentences*/
        for (List<String> sentence : allSentences) {
            for (String sentenceWord : sentence) {
                if (!allWords.contains(sentenceWord)) {
                    allWords.add(sentenceWord);
                }
            }
        }

        /*For each sentence of all the sentences*/
        for (List<String> sentence : newSentences) {
            /*For each word in the sentence being analyzed*/
            for (String sentenceWord : sentence) {
                for (String word : allWords) {
                    /*Word being analyzed is in sentence and is not equal to the vector word*/
                    if (sentence.contains(word) && !word.equals(sentenceWord)) {
                        /*If sentenceWord is already in map*/
                        HashMap<String, Double> oldVector =  map.get(sentenceWord);
                        
                        /*If the word already is in the vector word*/
                        try {
                            if (oldVector.containsKey(word)) {
                                oldVector.replace(word, (oldVector.get(word) + 1.0));
                                map.replace(sentenceWord, oldVector);
                            } else {
                               /*Word didn't previously exist inside of oldVector, initialize as 1.0*/
                               oldVector.put(word, 1.0);
                               map.put(sentenceWord, oldVector);
                           }
                        } catch (NullPointerException ee) {
                            /*Word didn't previously exist inside of oldVector, initialize as 1.0*/
                            try {
                                oldVector.put(word, 1.0);
                                map.put(sentenceWord, oldVector);
                            } catch (NullPointerException e) {
                                HashMap<String, Double> newVector = new HashMap<String, Double>();
                                newVector.put(word, 1.0);
                                map.put(sentenceWord, newVector);
                            }
                        }
                    }
                    /*Words are not in same sentence.*/
                    else {}
                }
            }
        }
        return map;
    }
    
    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("{");
        
        for (String vector : mapping.keySet()) {
            toPrint.append(vector + "=" + mapping.get(vector) + ",\n");
        }
        
        toPrint.append("}");
        
        return toPrint.toString();
    }
}
