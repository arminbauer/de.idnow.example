package utils.sorter;

import models.Identification;
import utils.comparators.IdentificationComparator;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Wolfgang Ostermeier on 09.10.2017.
 *
 * Sorter class that sorts a given List of Identification-Objects according to the order that the
 * IdentificationComparator specifies
 */
public class IdentificationSorter {

    /**
     * Sorts the specified List of Identification-Objects using the IdentificationComparator
     * @param identificationList List of Identification-Objects to be sorted
     * @return a Stream of the sorted Identifications
     */
    public static Stream<Identification> sortIdentifications(List<Identification> identificationList){

        return identificationList.stream().sorted(new IdentificationComparator());
    }


}
