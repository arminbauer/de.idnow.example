package models;

import internal.calculation.PriorityCalculator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sebastian.walter on 02.02.2016.
 */
public class IdentificationService {

    @Inject
    private IdentificationHandler identificationHandler;

    private List<Identification> identifications;
    private int identificationCounter;

    public IdentificationService() {
        identifications = new ArrayList<>();
        identificationCounter = 0;
    }

    public int nextIdentificationId() {
        return identificationCounter++;
    }

    public void addIdentification(Identification identification) {
        identifications.add(identification);
    }

    public List<Identification> getIdentifications() {
        sortIdentifications();
        return identifications;
    }

    private void sortIdentifications() {
        for (Identification identification : identifications) {
            identification.setPriority(identificationHandler.calcPriority(identification));
        }
        Collections.sort(identifications);
    }
}
