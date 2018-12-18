package edu.uiowa.cs.similarity;

import java.util.Map;

public interface SimilarityFunction {
    public double similarityCalc(Map<String, Double> main, Map<String, Double> other);
}
