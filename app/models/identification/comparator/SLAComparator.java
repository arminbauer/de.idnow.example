package models.identification.comparator;

import com.google.inject.ImplementedBy;
import models.identification.entities.Identification;

import java.util.Comparator;

@ImplementedBy(StoreSLAComparator.class)
public interface SLAComparator extends Comparator<Identification> {
}
