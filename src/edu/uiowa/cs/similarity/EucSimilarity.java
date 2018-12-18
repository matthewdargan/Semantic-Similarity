package edu.uiowa.cs.similarity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EucSimilarity implements SimilarityFunction {
    
    @Override
    public double similarityCalc(Map<String, Double> main, Map<String, Double> other) {
        double total = 0.0;
        
        Set<String> sharedWords = new HashSet(main.keySet());
        sharedWords.retainAll(other.keySet());
        
        for (String key : sharedWords) {
            total += Math.pow((main.get(key) - other.get(key)), 2);
        }
        
        return -1.0 * (Math.sqrt(total));
    }
}
