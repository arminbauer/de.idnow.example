package models;

import javax.inject.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;
import java.util.Map;


@Singleton
public class State {
    private ConcurrentHashMap<Integer, Company> companies = new ConcurrentHashMap<>();

    private Queue<Identification> identifications = new ConcurrentLinkedQueue<>();

    public Map<Integer, Company> getCompanies() {
        return companies;
    }

    public Queue<Identification> getIdentifications() {
        return identifications;
    }
}
