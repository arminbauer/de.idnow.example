package utils.sorter;

import models.Identification;
import utils.comparators.IdentificationComparator;

import java.util.List;
import java.util.stream.Stream;

public class IdentificationSorter {

    public static Stream<Identification> sortIdentifications(List<Identification> identificationList){

        return identificationList.stream().sorted(new IdentificationComparator());
    }


}
