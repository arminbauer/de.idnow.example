import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Company;
import models.Identification;
import org.junit.Test;
import play.libs.Json;
import play.twirl.api.Content;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.contentType;


/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
    }

    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertEquals("text/html", contentType(html));
        //assertTrue(contentAsString(html).contains("Your new application is ready."));
    }

    @Test
    public void orderIdentification2() {
        try {
            JsonNode companyJson1 = Json.parse("{\"id\": 1, \"name\": \"Test Bank 1\", \"slatime\": 60, \"slapercentage\": 0.9, \"currentSLAPercentage\": 0.95}");
            JsonNode companyJson2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank 2\", \"slatime\": 60, \"slapercentage\": 0.9, \"currentSLAPercentage\": 0.90}");
            JsonNode identificationJson1 = Json.parse("{\"id\": 1, \"name\": \"Test Iden 1\", \"waitingTime\": 30, \"companyId\": 1}");
            JsonNode identificationJson2 = Json.parse("{\"id\": 2, \"name\": \"Test Iden 2\", \"waitingTime\": 30, \"companyId\": 2}");
            ObjectMapper mapper = new ObjectMapper();
            Identification identification1 = mapper.treeToValue(identificationJson1, Identification.class);
            identification1.setCompany(mapper.treeToValue(companyJson1, Company.class));
            Identification identification2 = mapper.treeToValue(identificationJson2, Identification.class);
            identification2.setCompany(mapper.treeToValue(companyJson2, Company.class));

            List<Identification> identifications = Arrays.asList(identification1, identification2);

            Collections.sort(identifications);

            assertTrue(identifications.get(0).getId().equalsIgnoreCase("2"));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void orderIdentification3() {
        try {
            JsonNode companyJson1 = Json.parse("{\"id\": 1, \"name\": \"Test Bank 1\", \"slatime\": 60, \"slapercentage\": 0.9, \"currentSLAPercentage\": 0.95}");
            JsonNode companyJson2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank 2\", \"slatime\": 120, \"slapercentage\": 0.8, \"currentSLAPercentage\": 0.95}");
            JsonNode identificationJson1 = Json.parse("{\"id\": 1, \"name\": \"Test Iden 1\", \"waitingTime\": 30, \"companyId\": 1}");
            JsonNode identificationJson2 = Json.parse("{\"id\": 2, \"name\": \"Test Iden 2\", \"waitingTime\": 30, \"companyId\": 2}");
            ObjectMapper mapper = new ObjectMapper();
            Identification identification1 = mapper.treeToValue(identificationJson1, Identification.class);
            identification1.setCompany(mapper.treeToValue(companyJson1, Company.class));
            Identification identification2 = mapper.treeToValue(identificationJson2, Identification.class);
            identification2.setCompany(mapper.treeToValue(companyJson2, Company.class));

            List<Identification> identifications = Arrays.asList(identification1, identification2);

            Collections.sort(identifications);

            assertTrue(identifications.get(0).getId().equalsIgnoreCase("1"));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void orderIdentification4() {
        try {
            JsonNode companyJson1 = Json.parse("{\"id\": 1, \"name\": \"Test Bank 1\", \"slatime\": 60, \"slapercentage\": 0.9, \"currentSLAPercentage\": 0.95}");
            JsonNode companyJson2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank 2\", \"slatime\": 120, \"slapercentage\": 0.8, \"currentSLAPercentage\": 0.8}");
            JsonNode identificationJson1 = Json.parse("{\"id\": 1, \"name\": \"Test Iden 1\", \"waitingTime\": 45, \"companyId\": 1}");
            JsonNode identificationJson2 = Json.parse("{\"id\": 2, \"name\": \"Test Iden 2\", \"waitingTime\": 30, \"companyId\": 2}");
            ObjectMapper mapper = new ObjectMapper();
            Identification identification1 = mapper.treeToValue(identificationJson1, Identification.class);
            identification1.setCompany(mapper.treeToValue(companyJson1, Company.class));
            Identification identification2 = mapper.treeToValue(identificationJson2, Identification.class);
            identification2.setCompany(mapper.treeToValue(companyJson2, Company.class));

            List<Identification> identifications = Arrays.asList(identification1, identification2);

            Collections.sort(identifications);

            assertTrue(identifications.get(0).getId().equalsIgnoreCase("2"));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void orderIdentificationSameCompany() {
        try {
            JsonNode companyJson = Json.parse("{\"id\": 1, \"name\": \"Test Bank 1\", \"slatime\": 60, \"slapercentage\": 0.9, \"currentSLAPercentage\": 0.95}");
            JsonNode idntificationJson1 = Json.parse("{\"id\": 1, \"name\": \"Test Iden 1\", \"waitingTime\": 45, \"companyId\": 1}");
            JsonNode idntificationJson2 = Json.parse("{\"id\": 2, \"name\": \"Test Iden 2\", \"waitingTime\": 30, \"companyId\": 1}");
            ObjectMapper mapper = new ObjectMapper();
            Company company = mapper.treeToValue(companyJson, Company.class);
            Identification identification1 = mapper.treeToValue(idntificationJson1, Identification.class);
            identification1.setCompany(company);
            Identification identification2 = mapper.treeToValue(idntificationJson2, Identification.class);
            identification2.setCompany(company);

            List<Identification> identifications = Arrays.asList(identification1, identification2);

            Collections.sort(identifications);

            assertTrue(identifications.get(0).getId().equalsIgnoreCase("1"));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
