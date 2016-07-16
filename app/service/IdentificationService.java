package service;

import com.avaje.ebean.Ebean;
import com.google.inject.Singleton;
import models.Company;
import models.Identification;
import service.dto.IdentificationDTO;
import service.ifaces.IIdentificationService;
import utils.ErrorCodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sreenath on 15.07.16.
 */
@Singleton
public class IdentificationService implements IIdentificationService {

    @Override
    public IdentificationDTO getById(String id) {
        return null;
    }

    /**
     * Gets the list of identifications from the persistence layer, sorts them based on their urgencies
     * and translates them to their DTO objects.
     *
     * @return
     */
    @Override
    public List getAll() {
        List<Identification> identifications = Ebean.find(Identification.class).findList();
        identifications.sort(new IdComparator());
        Collections.reverse(identifications);
        List<IdentificationDTO> identificationDTOs = new ArrayList<>();
        identifications.stream().forEach((id) -> identificationDTOs.add(getDTO(id)));
        return identificationDTOs;
    }

    /**
     * Creates an identification
     *
     * @throws RuntimeException: if the company id is invalid
     * @param obj
     */
    @Override
    public void create(IdentificationDTO obj) {
        Company c = Ebean.find(Company.class, obj.getCompanyId());
        if (c == null) {
            throw new RuntimeException(ErrorCodes.ID_COMPANY_NOT_FOUND);
        }
        Ebean.save(getModel(obj, c));
    }

    @Override
    public IdentificationDTO update(IdentificationDTO obj) {
        return null;
    }

    @Override
    public IdentificationDTO delete(IdentificationDTO obj) {
        return null;
    }

    private Identification getModel(IdentificationDTO dto, Company c) {
        Identification i = new Identification();
        i.setNameOfUser(dto.getNameOfUser());
        i.setId(dto.getId());
        i.setCompany(c);
        i.setStartTime(dto.getStartTime());
        i.setWaitingTime(dto.getWaitTimeSeconds());
        return i;
    }

    private IdentificationDTO getDTO(Identification identification) {
        IdentificationDTO i = new IdentificationDTO();
        i.setNameOfUser(identification.getNameOfUser());
        i.setId(identification.getId());
        i.setCompanyId(identification.getCompany().getId());
        i.setStartTime(identification.getStartTime());
        i.setWaitTimeSeconds(identification.getWaitingTime());
        return i;
    }

    private class IdComparator implements Comparator<Identification> {

        private long currentTime = System.currentTimeMillis()/1000;

        /**
         * Compares two identifications based on the value of urgency.
         * urgency = (slaPercentage * waitingTime)/(currentSlaPercentage * slaTime)
         *
         * @param id1
         * @param id2
         * @return
         */
        @Override
        public int compare(Identification id1, Identification id2) {
            double id1Urgency = (id1.getCompany().getSlaPercent() * id1.getWaitingTime()) / (id1.getCompany().getCurrentSlaPercent() * id1.getCompany().getSlaTimeSeconds());
            double id2Urgency = (id2.getCompany().getSlaPercent() * id2.getWaitingTime()) / (id2.getCompany().getCurrentSlaPercent() * id2.getCompany().getSlaTimeSeconds());
            return Double.compare(id1Urgency, id2Urgency);
        }
    }
}
