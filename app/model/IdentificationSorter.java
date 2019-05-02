package model;

import java.util.Comparator;


public class IdentificationSorter implements Comparator<Scorable> {
    @Override
    public int compare(Scorable id1, Scorable id2) {
        // calculate everything based on the delta and weight
        double id1score = id1.score();
        double id2score = id2.score();

        if ( (id1score - id2score) < 0) {
            return -1;
        } else if ( (id1score - id2score) > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}


