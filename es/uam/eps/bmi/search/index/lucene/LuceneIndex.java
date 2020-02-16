/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.index.lucene;


import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.freq.FreqVector;
import es.uam.eps.bmi.search.index.freq.lucene.LuceneFreqVector;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiTerms;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


/**
 *
 * @author Wendy Sosa
 */
public class LuceneIndex implements Index {

    IndexReader index;
    TermsEnum allTerms;
    
    public LuceneIndex(String indexPath) throws IOException {
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        index = DirectoryReader.open(directory);
      
    }
    
    @Override
    public List<String> getAllTerms() {
        List<String> termList = new ArrayList<String>();
        try {           
            TermsEnum terms = MultiTerms.getTerms(index, "content").iterator();
            while (terms.next() != null) {
                termList.add(terms.term().utf8ToString());
            }
            
        } catch (IOException ex) {
            System.out.println("Error al buscar términos");
        }
        return termList;
    }

    @Override
    public double getTotalFreq(String term) throws IOException {               
        Terms terms = MultiTerms.getTerms(index, "content");
        LuceneFreqVector freqVector = new LuceneFreqVector(terms);
        return freqVector.getFreq(term);     
    }

    @Override
    public FreqVector getDocVector(int docID) {
        LuceneFreqVector terms = null;
        try {
            terms = new LuceneFreqVector(index.getTermVector(docID, "content"));
        } catch (IOException ex) {System.out.println("Error en el FreqVector");
        }
        return terms;
    }

    @Override
    public String getDocPath(int docID) {
        String path = "";
        try {
            path = index.document(docID).getField("path").toString();
        } catch (IOException ex) {
            System.out.println("Error en el doc Path");
        }
        return path;
    }

    @Override
    public String getTermFreq(String word, int docID) {
        String termFreq = "0";
        try {            
            Terms docVector = index.getTermVector(docID, "content");
            TermsEnum termsIterator = docVector.iterator();
            termsIterator.next();
            for (int i = 0; i < docVector.size(); i++) {
                String termString = termsIterator.term().utf8ToString();
                if (termString.equalsIgnoreCase(word)) {
                    return Long.toString(termsIterator.totalTermFreq());
                }else {
                    termsIterator.next();
                } 
            }
        } catch (IOException ex) {
            System.out.println("Error en el TermFreq");
        } 
        return termFreq;
    }

    @Override
    public String getDocFreq(String word) {
        try {
            Terms terms = MultiTerms.getTerms(index, "content");
            TermsEnum iterator = terms.iterator();
            iterator.next();
            for (int i = 0; i < terms.size(); i++) {
                String termString = iterator.term().utf8ToString();
                if (termString.equalsIgnoreCase(word)) {
                    return Long.toString(index.docFreq(new Term("content", termString)));
                }else {
                    iterator.next();
                } 
            }
        } catch (IOException ex) {
            Logger.getLogger(LuceneIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "0";
    }

    public IndexReader getIndexReader() {
        return index;
    }
}
