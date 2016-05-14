package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import play.data.validation.ValidationError;

/**
 * Created by nick on 14.05.16.
 */
public class ErrorsTest {

    @Test
    public void create_general_error() throws Exception {
        String msg = RandomStringUtils.randomAlphanumeric(12);
        Assert.assertThat(Errors.error(msg).toString(), Matchers.is("{\"errorMsg\":\"" + msg + "\"}"));
    }

    @Test
    public void create_validation_errors() throws Exception {
        String key = RandomStringUtils.randomAlphanumeric(12);
        String msg1 = RandomStringUtils.randomAlphanumeric(12);
        String msg2 = RandomStringUtils.randomAlphanumeric(12);
        ValidationError validationError = new ValidationError(key, Lists.newArrayList(msg1, msg2), Lists.newArrayList("", ""));


        JsonNode jsonNode = Errors.validationError(Lists.newArrayList(validationError));
        Assert.assertThat(jsonNode.toString(), Matchers.is("{\"errorMsg\":\"Validation failed\",\"errorType\":\"validationFailed\",\"details\":[{\"" + key + "\":[\"" + msg1 + "\",\"" +
                "" + msg2 + "\"]}]}"));
    }
}