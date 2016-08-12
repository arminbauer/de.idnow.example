package controllers;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import engine.IdentificationSystem;
import play.Play;

/**
 * Created by vld on 9/08/16.
 */
@Singleton
public class CompanyIdResolver implements ObjectIdResolver {

    @Inject
    IdentificationSystem identificationSystem;


    @Override
    public void bindItem(ObjectIdGenerator.IdKey id, Object pojo) {

    }

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey id) {
        if (id.key instanceof String) {
            return identificationSystem.getCompany((String) id.key).orElseThrow(() ->
                    new IllegalStateException(String.format("Can not find company %s, it is not registered with us", id.key)));
        } else {
            throw new IllegalArgumentException("id is not of String type");
        }
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object context) {
        return Play.application().injector().instanceOf(CompanyIdResolver.class);

    }

    @Override
    public boolean canUseFor(ObjectIdResolver resolverType) {
        return resolverType instanceof CompanyIdResolver;
    }
}
