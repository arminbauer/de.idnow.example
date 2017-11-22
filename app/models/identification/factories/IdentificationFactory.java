package models.identification.factories;

import models.identification.entities.Identification;
import models.identification.jsons.IdentificationJson;

public class IdentificationFactory {
    private static IdentificationFactory instance;

    public IdentificationFactory() {
        if (instance!=null) {
            throw new ExceptionInInitializerError();
        }
    }

    public static IdentificationFactory getInstance() {
        return instance == null ? instance = new IdentificationFactory() : instance;
    }

    public Identification fromIdentificationJson(IdentificationJson json) {
        return json == null ? null :
                new Identification(json.id, json.name, json.time, json.waitingTime, json.companyId);
    }
}
