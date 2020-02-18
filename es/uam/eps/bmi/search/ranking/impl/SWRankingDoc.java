/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.io.IOException;

/**
 *
 * @author Wendy Sosa
 */
public class SWRankingDoc extends SearchRankingDoc {
    Index index;
    SWScoreDoc rankedDoc;
    
    public SWRankingDoc(Index idx, SWScoreDoc r) {
        index = idx;
        rankedDoc = r;
    }
    @Override
    public double getScore() {
        return rankedDoc.score;
    }

    public int getDocID() {
        return rankedDoc.docID;
    }

    @Override
    public String getPath() throws IOException {
        return index.getDocPath(rankedDoc.docID);
    }
}
