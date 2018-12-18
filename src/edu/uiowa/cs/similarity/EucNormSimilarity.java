package edu.uiowa.cs.similarity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EucNormSimilarity implements SimilarityFunction {
    
    @Override
    public double similarityCalc(Map<String, Double> main, Map<String, Double> other) {
        double magU = 0.0;
        double magV = 0.0;
        double total = 0.0;
        Map<String, Double> temp = new HashMap<>();
        Map<String, Double> temp2 = new HashMap<>();
        
        for (String key : main.keySet()) {
            magU += (main.get(key) * main.get(key));
        }
        
        magU = Math.sqrt(magU);
        
        for (String key : main.keySet()) {
            temp.put(key, main.get(key) / magU);
        }
        
        for (String key : other.keySet()) {
            magV += (other.get(key) * other.get(key));
        }
        
        magV = Math.sqrt(magV);
        
        for (String key : other.keySet()) {
            temp2.put(key, other.get(key) / magV);
        }
        
        Set<String> sharedWords = new HashSet(main.keySet());
        sharedWords.retainAll(other.keySet());
        
        for (String key : sharedWords) {
            total += Math.pow((temp.get(key) - temp2.get(key)), 2);
        }
        
        return -1.0 * (Math.sqrt(total));
    }
}
