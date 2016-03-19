package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Company;
import models.Identification;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class RestController extends Controller {

    //which map to use?
    private Map<String, Identification> current_open_identifications = new ConcurrentHashMap<>();
    private Map<String, Company> companies = new ConcurrentHashMap<>();

    public Result addIdentification() {
        //Get the parsed JSON data
        Identification identification = Form.form(Identification.class).bindFromRequest().get();
        if (!companies.containsKey(identification.getCompanyid())) {
            return notFound("no company found for: " + identification);
        }
        current_open_identifications.put(identification.getId(), identification);
        Logger.debug("added Identification: "+identification);
        Logger.debug("registered identifications: " + current_open_identifications.size());
        return redirect(routes.Application.index());
    }

    public Result startIdentification() {
        //Get the parsed JSON data
        Identification identification = Form.form(Identification.class).bindFromRequest().get();

        //Do something with the identification
        //Q: what if Identification.waiting_time > Company.sla_time? should it be removed?
        if (!companies.containsKey(identification.getCompanyid())) {
            return notFound("no company found for: " + identification);
        }
        current_open_identifications.put(identification.getId(), identification);
        Logger.debug("added Identification: "+identification);
        Logger.debug("registered identifications: " + current_open_identifications.size());

        return ok();
    }

    //test: if identification with same ID is send again


    public Result addCompany() {
        //Get the parsed JSON data
        Company company = Form.form(Company.class).bindFromRequest().get();

        //Do something with the company
        companies.put(company.getId(), company);
        Logger.debug("added Company: "+company);
        Logger.debug("registered companies: " + companies.size());

        return ok();
    }

    public Result identifications() {
        ArrayNode identifications = Json.newArray();

        //Get the current identifications
        //Compute correct order
        List<Identification> current_open_identifications_list = new ArrayList<>(current_open_identifications.values());
        Logger.debug("before sorting: " + current_open_identifications_list);
        Collections.sort(current_open_identifications_list, new SLAComparator());
        Logger.debug("after sorting: " + current_open_identifications_list);

        //Create new identification JSON with JsonNode identification = Json.newObject();
        //Add identification to identifications list
        for (Identification id : current_open_identifications_list) {
            JsonNode jsonNode = Json.toJson(id);
            identifications.add(jsonNode);
        }

        return ok(identifications);
    }

    /**
     * This Comparator considers the <code>Company.urgency</code>, which is an indicator for urgent SLAa.
     * For an explanation see code.
     * It will scale the <code>Identification.waiting_time</code> and decide on that value.
     * For more advanced comparisions have a look at <code>SLAComparatorV2</code>.
     */
    private class SLAComparator implements Comparator<Identification>{

        @Override
        public int compare(Identification id1, Identification id2) {
            Company company1 = companies.get(id1.getCompanyid());
            Company company2 = companies.get(id2.getCompanyid());

            // -----
            // this code could be removed, if we do not care about computing time
            int compareCU = Float.compare(company1.getUrgency(), company2.getUrgency());
            Logger.debug(String.format("Company%s urgency: %f; Company%s urgency: %f; => comarision: %d", id1.getCompanyid(), company1.getUrgency(), id2.getCompanyid(), company2.getUrgency(), compareCU));
            if (compareCU == 0) {
                //we can return by waiting time ratio here, because the company.urgencies are equal
                //reversed comparision, because higher waiting_times will be served first
                return Float.compare(id2.getWaiting_time().floatValue(), id1.getWaiting_time().floatValue());
            }
            // -----

            //waiting time ratio scaled with the company.urgency
            Float urgencyId1 = Float.valueOf(id1.getWaiting_time().floatValue() * company1.getUrgency().floatValue());
            Float urgencyId2 = Float.valueOf(id2.getWaiting_time().floatValue() * company2.getUrgency().floatValue());
            Logger.debug(String.format("Id%s scaled ratio: %+f Id%s scaled ratio: %+f", id1.getId(), urgencyId1, id2.getId(), urgencyId2));

            //reversed comparision, because higher waiting_times will be served first
            return Float.compare(urgencyId2, urgencyId1); }
    }

    /**
     * This Comparator considers the ratio of <code>Identification.waiting_time / Company.sla_time</code>.
     * This may lead to a different result in example 3, but is more precise in my opinion, because identifications that have more time left to wait should do so.
     * Please note, that this Comparator also considers the <code>Company.urgency</code> which covers the SLA-percentage!
     */
    private class SLAComparatorV2 implements Comparator<Identification>{

        @Override
        public int compare(Identification id1, Identification id2) {
            Company company1 = companies.get(id1.getCompanyid());
            Company company2 = companies.get(id2.getCompanyid());
            //these ratios reflect the percentage of reached SLA_time
            Float id1WTRatio = Float.valueOf(id1.getWaiting_time().floatValue() / company1.getSla_time().floatValue());
            Float id2WTRatio = Float.valueOf(id2.getWaiting_time().floatValue() / company2.getSla_time().floatValue());
            Logger.debug(String.format("Id%s wt ratio: %f Id%s wt ratio: %f", id1.getId(), id1WTRatio, id2.getId(), id2WTRatio));

            // -----
            // this code could be removed, if we do not care about computing time
            int compareCU = Float.compare(company1.getUrgency(), company2.getUrgency());
            Logger.debug(String.format("Company%s urgency: %f; Company%s urgency: %f; => comarision: %d", id1.getCompanyid(), company1.getUrgency(), id2.getCompanyid(), company2.getUrgency(), compareCU));
            if (compareCU == 0) {
                //we can return by waiting time ratio here, because the company.urgencies are equal
                //reversed comparision, because higher waiting_times will be served first
                return Float.compare(id2WTRatio, id1WTRatio);
            }
            // -----

            //waiting time ratio scaled with the company.urgency
            Float urgencyId1 = Float.valueOf(id1WTRatio * company1.getUrgency().floatValue());
            Float urgencyId2 = Float.valueOf(id2WTRatio * company2.getUrgency().floatValue());
            Logger.debug(String.format("Id%s scaled ratio: %+f Id%s scaled ratio: %+f", id1.getId(), urgencyId1, id2.getId(), urgencyId2));

            //reversed comparision, because higher waiting_times will be served first
            return Float.compare(urgencyId2, urgencyId1);
        }
    }
}
