package es.uam.eps.bmi.search.ranking.lucene;

import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import org.apache.lucene.search.ScoreDoc;
import java.util.Iterator;

public class LuceneRanking implements SearchRanking {
    ScoreDoc scoreDocs[];
    LuceneIndex index;

    public LuceneRanking(LuceneIndex index, ScoreDoc[] scoreDocs) {
        this.index = index;
        this.scoreDocs = scoreDocs;
    }

    @Override
    public int size() {
        return scoreDocs.length;
    }

    @Override
    public Iterator<SearchRankingDoc> iterator() {
        return new LuceneRankingIterator(index,scoreDocs);
    }
}
