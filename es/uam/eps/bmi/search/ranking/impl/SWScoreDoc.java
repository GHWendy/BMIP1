/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.ranking.impl;

/**
 *
 * @author Wendy Sosa
 */
public class SWScoreDoc {
    int docID;
    double score;
    
    public SWScoreDoc(int id, double docScore){
        docID = id;
        score = docScore;
    }  

    public int getDocID() {
        return docID;
    }

    public void setScore(double score) {
        this.score = score;
    }
    
    public double getScore() {
        return score;
    }
}
