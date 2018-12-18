import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.uiowa.cs.similarity.*;

public class SimilarityTest {
    
    @Test
    public void testEuc() {
        HashMap<String, Double> test1 = new HashMap<>();
        HashMap<String, Double> test2 = new HashMap<>();
        Similarity similarity = new Similarity(new EucSimilarity());
        
        test1.put("one", 1.0);
        test1.put("two", 4.0);
        test1.put("three", 1.0);
        test1.put("four", 0.0);
        test1.put("five", 0.0);
        test1.put("six", 0.0);
        
        test2.put("one", 3.0);
        test2.put("two", 0.0);
        test2.put("three", 0.0);
        test2.put("four", 1.0);
        test2.put("five", 1.0);
        test2.put("six", 2.0);
        
        double expected = -5.1961524227;
        
        double actual = similarity.apply(test1, test2);
        
        assertEquals(expected, actual, 0.00001);
    }
    
    @Test
    public void testEucNorm() {
        HashMap<String, Double> test1 = new HashMap<>();
        HashMap<String, Double> test2 = new HashMap<>();
        Similarity similarity = new Similarity(new EucNormSimilarity());
        
        test1.put("one", 1.0);
        test1.put("two", 4.0);
        test1.put("three", 1.0);
        test1.put("four", 0.0);
        test1.put("five", 0.0);
        test1.put("six", 0.0);
        
        test2.put("one", 3.0);
        test2.put("two", 0.0);
        test2.put("three", 0.0);
        test2.put("four", 1.0);
        test2.put("five", 1.0);
        test2.put("six", 2.0);
        
        double expected = -1.27861316602;
        
        double actual = similarity.apply(test1, test2);
        
        assertEquals(expected, actual, 0.00001);
    }
}
