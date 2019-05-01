package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Comparator;


public class IdentificationSorter implements Comparator<Identification> {
    Logger log = LoggerFactory.getLogger(IdentificationSorter.class);
    @Override
    public int compare(Identification id1, Identification id2) {
        // calculate everything based on the delta and weight
        double id1score = id1.score();
        double id2score = id2.score();

        log.info("id1score " + id1.getId() + " = " + id1score + ", id2score " + id2.getId() + " = " + id2score);

        if ( (id1score - id2score) < 0) {
            return -1;
        } else if ( (id1score - id2score) > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}


