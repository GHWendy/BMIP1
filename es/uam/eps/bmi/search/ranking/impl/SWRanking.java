/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.util.Iterator;

/**
 *
 * @author Wendy Sosa
 */
public class SWRanking implements SearchRanking {
    SWScoreDoc scoreDocs[];
    LuceneIndex index;

    public SWRanking(LuceneIndex index, SWScoreDoc[] scoreDocs) {
        this.index = index;
        this.scoreDocs = scoreDocs;
    }

    @Override
    public int size() {
        return scoreDocs.length;
    }

    @Override
    public Iterator<SearchRankingDoc> iterator() {
        return new SWRankingIterator(index,scoreDocs);
    }
}
