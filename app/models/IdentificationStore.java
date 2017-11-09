package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Florian Schmidt on 07.11.2017.
 */
public class IdentificationStore {
    private static List<Identification> identifications = new ArrayList<>();

    private static boolean exists(UUID id) {
        return identifications.stream().anyMatch(existingIdentification -> existingIdentification.getId().equals(id));
    }

    public static void add(Identification identification) throws DataStoreException {
        if(exists(identification.getId())) {
            throw new DataStoreException(
                    String.format("Cannot add identification, because a identification with id '%s' already exists.",
                                  identification.getId()));
        } else if(!CompanyStore.exists(identification.getCompanyId())) {
            throw new DataStoreException(
                    String.format("Cannot add identification, because company with id '%s' does not exist.",
                                  identification.getCompanyId()));
        }
        identifications.add(identification);
    }

    public static List<Identification> getPrioritizedIdentifications() {
        List<Identification> sorted = identifications.stream().sorted(new SlaComparator()).collect(Collectors.toList());
        return Collections.unmodifiableList(sorted);
    }

    public static void clear() {
        identifications.clear();
    }
}
