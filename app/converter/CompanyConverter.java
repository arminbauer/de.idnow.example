package converter;

import com.fasterxml.jackson.databind.JsonNode;
import model.Company;
import model.Identification;
import play.libs.Json;

/**
 * Created by sleski on 30.06.2015.
 */
public class CompanyConverter {

    public static Company jsonConvert(JsonNode json) {
        Company company = Json.fromJson(json, Company.class);
        return company;
    }

    public static JsonNode objConverter(Company company) {
        JsonNode jsonNode = Json.toJson(company);
        return jsonNode;
    }
}
