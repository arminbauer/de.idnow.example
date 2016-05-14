package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.data.Form;
import play.data.validation.ValidationError;
import play.libs.Json;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by nick on 14.05.16.
 */
class ValidJson<T extends Serializable> {

    private final Form<T> form;
    private final T obj;

    ValidJson(JsonNode jsonNode, Class<T> clazz) {
        this.obj = Json.fromJson(jsonNode, clazz);
        this.form = Form.form(clazz).bind(jsonNode);
    }

    public T get() {
        return obj;
    }

    public boolean hasErrors() {
        return form.hasErrors() || form.hasGlobalErrors();
    }

    public List<ValidationError> getErrors() {
        return Stream.concat(
                form.globalErrors().stream(),
                form.errors().values().stream().flatMap(Collection::stream)).collect(Collectors.toList());
    }
}
