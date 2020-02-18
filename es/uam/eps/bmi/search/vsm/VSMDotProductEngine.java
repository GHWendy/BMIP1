/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.vsm;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.impl.SWRanking;

import java.io.IOException;
import es.uam.eps.bmi.search.ranking.impl.SWScoreDoc;
import es.uam.eps.bmi.search.ranking.impl.TreeSorter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.lucene.index.PostingsEnum;
/**
 *
 * @author Wendy Sosa
 */
public class VSMDotProductEngine extends AbstractEngine{
    
   Hashtable<String, Double> termsIdf  = new Hashtable<>();
   Hashtable<String, Integer> qVector  = new Hashtable<>();
   LuceneIndex newIndex;
 
    public VSMDotProductEngine(LuceneIndex ind ) throws IOException {
        super(ind);
        newIndex = ind;
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        Parser parser =  new Parser();
        String[] queryTerms = parser.parse(query);        
       for (String term : queryTerms) {
           qVector.put(term,calculateQueryTf(term));
           termsIdf.put(term,calculateIdf(term));
       }
        SWScoreDoc result[] = MultiplicacionVectorial(queryTerms, cutoff);
        SearchRanking searchRanking = new SWRanking((LuceneIndex) index,result);
        return searchRanking;
    }
  
    public SWScoreDoc[] MultiplicacionVectorial(String[] queryTerms, int cutoff) throws IOException{
        TreeMap<Integer,Double> scores = new TreeMap<>(); 
       for (String term : queryTerms) {
           PostingsEnum postings = newIndex.getPostings(term);
           if (postings != null) { 
            int docID;
            int docFreq;
            while ((docID = postings.nextDoc()) != PostingsEnum.NO_MORE_DOCS) {
                docFreq = postings.freq();
                double tfIdf = calculateTf(term,docFreq) * termsIdf.get(term);
                scores.putIfAbsent(docID,0.0);
                scores.put(docID, scores.get(docID)+ tfIdf);
            }
           }
       }
      Map sortedScores = TreeSorter.sortByValues(scores.descendingMap());
      Set scoreSet = sortedScores.entrySet();
      Iterator iterator = scoreSet.iterator();
      
      int size = (scores.size()>cutoff)? cutoff : scores.size();     
      SWScoreDoc topDocs[] = new SWScoreDoc[size]; 
      
       for (int i=0; i < size;i++){
           Map.Entry mapEntry = (Map.Entry) iterator.next();
           topDocs[i] = new SWScoreDoc((Integer)mapEntry.getKey(),(Double)mapEntry.getValue());
       }
       return topDocs;
    }
    private double calculateTf(String term,int termFreq){
        double tf = 0;
         if (termFreq>0) {
             tf= 1+ (Math.log(termFreq)/Math.log(2));
         }
         return tf;
    }
    private int calculateQueryTf(String query){
         List<String> allTerms = newIndex.getAllTerms();
         for (int i = 0; i< allTerms.size();i++){
             if (allTerms.get(i).equalsIgnoreCase(query)){
                 return 1;
             }
         }
         return 0;
    }
    
    private double calculateIdf(String term){ 
        int docFreq = Integer.parseInt(newIndex.getDocFreq(term));
        double division = (newIndex.getNumDocs()+ 1)/(docFreq + 0.5);
        double idf = Math.log(division)/Math.log(2);
        return idf;
    }
}
