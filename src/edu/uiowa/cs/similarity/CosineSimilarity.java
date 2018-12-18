package edu.uiowa.cs.similarity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CosineSimilarity implements SimilarityFunction {
    
    @Override
    public double similarityCalc(Map<String, Double> main, Map<String, Double> other) {
        double num = 0.0;
        double demU = 0.0;
        double demV = 0.0;
        double denominator = 0.0;
        
        Set<String> sharedWords = new HashSet(main.keySet());
        sharedWords.retainAll(other.keySet());
        
        for (String key : sharedWords) {
            num += (main.get(key) * other.get(key));
        }
        for (String key: main.keySet()){
            demU += ((double)main.get(key) * (double)main.get(key));
        }
        for (String key: other.keySet()){
            demV += ((double)other.get(key) * (double)other.get(key));
        }
        
        denominator = Math.sqrt(demU * demV);
        
        if (denominator != 0){
            return (num / denominator);
        } else {
            return 0.0;
        }
    }
}
