/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.freq.FreqVector;
import org.apache.lucene.index.IndexReader;

import java.io.IOException;

/**
 *
 * @author Wendy Sosa
 */
public class LuceneIndex implements Index {
    IndexReader indexReader;

    public LuceneIndex(String indexPath) {
    }

    @Override
    public int getAllTerms() {
        return 0;
    }

    @Override
    public double getTotalFreq(String t1) throws IOException {
        return 0;
    }

    @Override
    public FreqVector getDocVector(int docID) {
        return null;
    }

    @Override
    public String getDocPath(int docID) {
        return null;
    }

    @Override
    public String getTermFreq(String word, int docID) {
        return null;
    }

    @Override
    public String getDocFreq(String word) {
        return null;
    }

    public IndexReader getIndexReader() {
        return indexReader;
    }
}
