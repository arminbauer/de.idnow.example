package repositories;

import com.avaje.ebean.Ebean;
import repositories.models.Identification;

import java.util.List;

/**
 * Created by ebajrami on 4/23/16.
 */
public class IdentificationRepository implements Repository<Identification> {

    @Override
    public Identification findById(int id) {
        return Ebean.find(Identification.class, id);
    }

    @Override
    public void add(Identification object) {
        Ebean.save(object);
    }

    @Override
    public void update(Identification object) {
        Ebean.update(object);
    }

    @Override
    public void delete(Identification object) {
        Ebean.delete(object);
    }

    @Override
    public void deleteById(Identification object) {
        Identification identification = findById(object.getId());
        if (identification != null) {
            Ebean.delete(identification);
        }
    }

    @Override
    public List<Identification> findAll() {
        return Ebean.find(Identification.class).findList();
    }


    public List<Identification> getSorted() {
        return Ebean.find(Identification.class)
                .fetch("company")
                .orderBy("company.slaPercentage DESC, waitingTime ASC, company.currentSlaPercentage ASC")
                .findList();
    }
}
