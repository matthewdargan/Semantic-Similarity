package edu.uiowa.cs.similarity;

import java.util.Map;

public class Similarity {
    private final SimilarityFunction f;
    
    public Similarity(SimilarityFunction f) {
        this.f = f;
    }
    
    public double apply(Map<String, Double> main, Map<String, Double> other) {
        return f.similarityCalc(main, other);
    }
}
