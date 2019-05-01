package utils;

import model.Identification;
import model.IdentificationSorter;

import java.util.List;

public class SortUtils {
    public static List<Identification> sortIdentList(List<Identification> list) {
        list.sort(new IdentificationSorter());
        return list;
    }
}
