/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.index;
import es.uam.eps.bmi.search.index.freq.FreqVector;
import java.io.IOException;

/**
 *
 * @author Wendy Sosa
 */

public interface Index {
    
   public int getAllTerms();
   
   public double getTotalFreq(String t1) throws IOException;
   
   public FreqVector getDocVector(int docID);

   public String getDocPath(int docID);

    public String getTermFreq(String word, int docID);

    public String getDocFreq(String word);
   
   
}
