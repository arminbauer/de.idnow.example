package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Company;
import models.Identification;
import play.Logger;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static play.libs.Json.toJson;

public class RestController extends Controller {


    public Result addCompany() {
        //Get the parsed JSON data
        Company company = Form.form(Company.class).bindFromRequest().get();
        Query<Company> companyQuery = Ebean.find(Company.class);

        //Do something with the company
        company.save();
        Logger.debug("added Company: " + company);

        return redirect(routes.Application.index());
    }

    public Result getCompanies() {
        List<Company> allCompanies = new Model.Finder<>(String.class, Company.class).all();
        return ok(toJson(allCompanies));
    }

    public Result addIdentification() {
        //Get the parsed JSON data
        Identification identification = Form.form(Identification.class).bindFromRequest().get();
        if (Ebean.find(Company.class, identification.getCompanyid()) == null) {
            return notFound("no company found for: " + identification);
        }
        identification.save();
        Logger.debug("added Identification: " + identification);

        return redirect(routes.Application.index());
    }

    public Result getIdentifications() {
        List<Identification> allIdentifications = new Model.Finder<>(String.class, Identification.class).all();
        return ok(toJson(allIdentifications));
    }

    public Result identifications() {
        ArrayNode identifications = Json.newArray();

        //Get the current identifications
        List<Identification> current_open_identifications_list = Ebean.find(Identification.class).findList();
        //Compute correct order
        Logger.debug("before sorting: " + current_open_identifications_list);
        Collections.sort(current_open_identifications_list, new SLAComparator());
        Logger.debug("after sorting: " + current_open_identifications_list);

        //Create new identification JSON with JsonNode identification = Json.newObject();
        //Add identification to identifications list
        for (Identification id : current_open_identifications_list) {
            JsonNode jsonNode = toJson(id);
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
    private class SLAComparator implements Comparator<Identification> {

        @Override
        public int compare(Identification id1, Identification id2) {
            Company company1 = Ebean.find(Company.class, id1.getCompanyid());
            Company company2 = Ebean.find(Company.class, id2.getCompanyid());

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
            return Float.compare(urgencyId2, urgencyId1);
        }
    }

    /**
     * This Comparator considers the ratio of <code>Identification.waiting_time / Company.sla_time</code>.
     * This may lead to a different result in example 3, but is more precise in my opinion, because identifications that have more time left to wait should do so.
     * Please note, that this Comparator also considers the <code>Company.urgency</code> which covers the SLA-percentage!
     */
    private class SLAComparatorV2 implements Comparator<Identification> {

        @Override
        public int compare(Identification id1, Identification id2) {
            Company company1 = Ebean.find(Company.class, id1.getCompanyid());
            Company company2 = Ebean.find(Company.class, id2.getCompanyid());
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
