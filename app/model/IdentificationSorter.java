package model;

import java.util.Comparator;


public class IdentificationSorter implements Comparator<Identification> {
    @Override
    public int compare(Identification id1, Identification id2) {
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


