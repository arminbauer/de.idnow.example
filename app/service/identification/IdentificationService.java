package service.identification;

import com.google.inject.ImplementedBy;
import com.google.inject.Singleton;
import model.Identification;
import model.IdentificationSorter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ImplementedBy(IdentificationServiceImpl.class)
public interface IdentificationService {
    void addIdentification(Identification i);
    Map<Integer, Identification> getIdentMap();
    List<Identification> getSortedIdentList();
}

@Singleton
class IdentificationServiceImpl implements IdentificationService {
    Map<Integer, Identification> identMap = new HashMap();

    @Override
    public void addIdentification(Identification i) {
        identMap.put(i.getId(), i);
    }

    @Override
    public Map<Integer, Identification> getIdentMap() {
        return identMap;
    }

    @Override
    public List<Identification> getSortedIdentList() {
        List<Identification> identList = new ArrayList<>(this.getIdentMap().values());

        // calling custom sort will calculate the score of each identification
        // then sort by the scores
        identList.sort(new IdentificationSorter());
        return identList;
    }
}