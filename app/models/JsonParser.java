package models;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * Created by Florian Schmidt on 07.11.2017.
 */
public class JsonParser {
    public static Company parseCompany(JsonNode json) throws ValidationErrorException {
        CompanyJSON companyJSON = Json.fromJson(json, CompanyJSON.class);
        return parseCompany(companyJSON);
    }

    public static Company parseCompany (CompanyJSON companyJSON) throws ValidationErrorException {
        if(companyJSON.getId() == null) {
            throw new ValidationErrorException("Id missing, a company requires a unique-id.");
        }

        if(companyJSON.getName() == null) {
            throw new ValidationErrorException("Name missing, a company requires a name.");
        }

        if(companyJSON.getSlaTime() < 0) {
            throw new ValidationErrorException("SLA time cannot be negative.");
        }

        if(companyJSON.getSlaPercentage() < 0) {
            throw new ValidationErrorException("SLA percentage cannot be negative.");
        } else if(companyJSON.getSlaPercentage() > 1) {
            throw new ValidationErrorException("SLA percentage cannot exceed 1.");
        }

        if(companyJSON.getCurrentSlaPercentage() < 0) {
            throw new ValidationErrorException("Current SLA percentage cannot be negative.");
        } else if(companyJSON.getCurrentSlaPercentage() > 1) {
            throw new ValidationErrorException("Current SLA percentage cannot be negative.");
        }

        UUID id;

        try {
            id = UUID.fromString(companyJSON.getId());
        } catch(IllegalArgumentException ex) {
            throw new ValidationErrorException(String.join("\n",
                    "Id is not a unique id, a company requires a unique id with the format",
                    "'XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX', valid values for digits are 0-9 and a-f."));
        }

        String name = companyJSON.getName();
        int slaTime = companyJSON.getSlaTime();
        float slaPercentage = companyJSON.getSlaPercentage();
        float currentSlaPercentage = companyJSON.getCurrentSlaPercentage();

        Company company = new Company();
        company.setId(id);
        company.setName(name);
        company.setSlaTime(slaTime);
        company.setSlaPercentage(slaPercentage);
        company.setCurrentSlaPercentage(currentSlaPercentage);

        return company;
    }

    public static Identification parseIdentification(IdentificationJSON identificationJSON)
            throws ValidationErrorException {
        if(identificationJSON.getId() == null) {
            throw new ValidationErrorException("Id missing, an identification requires a unique-id.");
        }

        if(identificationJSON.getName() == null) {
            throw new ValidationErrorException("Name missing, an identification requires a name.");
        }

        if(identificationJSON.getTime() < 0) {
            throw new ValidationErrorException( "Epoch time cannot be negative.");
        }

        if(identificationJSON.getWaitingTime() < 0) {
            throw new ValidationErrorException("Waiting time cannot be negative.");
        }

        if(identificationJSON.getCompanyId() == null) {
            throw new ValidationErrorException("Company id missing, an identification requires a company id.");
        }

        UUID id;

        try {
            id = UUID.fromString(identificationJSON.getId());
        } catch(IllegalArgumentException ex) {
            throw new ValidationErrorException(
                    String.join("\n",
                                "Id is not a unique id, a company requires a unique id with the format",
                                "'XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX', valid values for digits are 0-9 and a-f."));
        }

        UUID companyId;

        try {
            companyId = UUID.fromString(identificationJSON.getCompanyId());
        } catch(IllegalArgumentException ex) {
            throw new ValidationErrorException(
                    String.join("\n",
                                "CompanyId is not a unique id, a company requires a unique id with the" +
                                        " format",
                                "'XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX', valid values for digits are 0-9 and a-f."));
        }

        LocalDateTime time;
        try {
            time = LocalDateTime.ofInstant(Instant.ofEpochMilli(identificationJSON.getTime()), ZoneId.systemDefault());
        } catch(DateTimeException ex) {
            throw new ValidationErrorException("Time could not be parsed, an identification requires a valid unix time.");
        }

        String name = identificationJSON.getName();
        int waitingTime = identificationJSON.getWaitingTime();

        Identification identification = new Identification();
        identification.setId(id);
        identification.setName(name);
        identification.setTime(time);
        identification.setWaitingTime(waitingTime);
        identification.setCompanyId(companyId);

        return identification;
    }
 }
