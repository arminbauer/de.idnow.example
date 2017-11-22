package models.identification.factories;

import models.identification.entities.Identification;
import models.identification.jsons.IdentificationJson;

public class IdentificationJsonFactory {
    private static IdentificationJsonFactory instance;

    public IdentificationJsonFactory() {
        if (instance!=null) {
            throw new ExceptionInInitializerError();
        }
    }

    public static IdentificationJsonFactory getInstance() {
        return instance == null ? instance = new IdentificationJsonFactory() : instance;
    }

    public IdentificationJson fromIdentification(Identification identification) {
        return identification == null ? null :
                new IdentificationJson(identification.getId(), identification.getName(), identification.getTime(),
                        identification.getWaitingTime(), identification.getCompanyId());
    }
}
