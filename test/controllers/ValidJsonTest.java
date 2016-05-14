package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Company;
import org.apache.commons.lang3.RandomUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import play.libs.Json;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * Created by nick on 14.05.16.
 */
public class ValidJsonTest {

    @Test
    public void should_provide_errors() throws Exception {
        Company data = new Company();
        data.setSlaPercentage(RandomUtils.nextFloat(0.0F, 1F));
        data.setCurrentSlaPercentage(RandomUtils.nextFloat(0.0F, 1F));
        data.setSlaTimeSeconds(RandomUtils.nextInt(1, 100));

        JsonNode jsonNode = Json.toJson(data);
        ValidJson<Company> json = new ValidJson<>(jsonNode, Company.class);
        Assert.assertThat(json.hasErrors(), Matchers.is(true));
        Assert.assertThat(json.getErrors().size(), greaterThanOrEqualTo(1));
        Assert.assertThat(json.get(), Matchers.is(data));

    }
}